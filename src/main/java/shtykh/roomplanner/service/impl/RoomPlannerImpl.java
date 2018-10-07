package shtykh.roomplanner.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsUsage;
import shtykh.roomplanner.model.impl.RoomPlanImpl;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;
import shtykh.roomplanner.service.RoomPlanner;
import shtykh.roomplanner.service.RoomStateService;

import java.util.*;

import static java.lang.Math.min;
import static shtykh.roomplanner.model.RoomLevel.ECONOMY;
import static shtykh.roomplanner.model.RoomLevel.PREMIUM;

@Log
@Service
public class RoomPlannerImpl implements RoomPlanner {

    @Value("${room.premium.min-price}")
    private Integer minPremiumPayment = 100;

    private RoomStateService roomStateService;

    public RoomPlannerImpl(@Autowired RoomStateService roomStateService) {
        this.roomStateService = roomStateService;
    }

    @Override
    public RoomPlan plan(List<Integer> roomRequest) {
        Map<RoomLevel, Integer> availableRooms = roomStateService.getAvailableRooms();
        PriorityQueue<Integer> premiumPayments = new PriorityQueue<>(roomRequest.size(), Comparator.reverseOrder());
        PriorityQueue<Integer> economyPayments = new PriorityQueue<>(roomRequest.size(), Comparator.reverseOrder());
        initPaymentQueues(roomRequest, premiumPayments, economyPayments);
        RoomsUsage premium = fillPremium(premiumPayments, economyPayments, availableRooms);
        RoomsUsage economy = fillEconomy(economyPayments, availableRooms);
        return new RoomPlanImpl() {{
            add(premium);
            add(economy);
        }};
    }

    @Override
    public void setAvailability(Map<RoomLevel, Integer> availabilities) {
        roomStateService.setAvailableRooms(availabilities);
    }

    private RoomsUsage fillPremium(Queue<Integer> premiumPayments, Queue<Integer> economyPayments,
                                   Map<RoomLevel, Integer> availableRooms) {
        RoomsUsageImpl premium = new RoomsUsageImpl(PREMIUM, 0, 0);
        int premiumSlots = availableRooms.get(PREMIUM);
        // processing $premiumSlots top paying high payers
        while (premiumSlots > 0 && !premiumPayments.isEmpty()) {
            premium.add(premiumPayments.poll());
            premiumSlots--;
        }
        // is any premium rooms left?
        if (premiumSlots > 0) {
            int economySlots = availableRooms.get(ECONOMY);
            int customersToUpgrade = min(economyPayments.size() - economySlots, premiumSlots);
            while (customersToUpgrade > 0) {
                premium.add(economyPayments.poll());
                customersToUpgrade--;
            }
        }
        return premium;
    }

    private RoomsUsage fillEconomy(Queue<Integer> queue, Map<RoomLevel, Integer> availableRooms) {
        RoomsUsageImpl economy = new RoomsUsageImpl(ECONOMY, 0, 0);
        int economySlots = availableRooms.get(ECONOMY);
        int economyRoomsToFill = min(queue.size(), economySlots);
        while (economyRoomsToFill > 0) {
            economy.add(queue.poll());
            economyRoomsToFill--;
        }
        return economy;
    }

    private void initPaymentQueues(List<Integer> roomRequest, Queue<Integer> premiumPayments,
                                   Queue<Integer> economyPayments) {
        roomRequest.forEach(price -> {
            if (price >= minPremiumPayment) {
                premiumPayments.offer(price);
            } else {
                economyPayments.offer(price);
            }
        });
    }
}
