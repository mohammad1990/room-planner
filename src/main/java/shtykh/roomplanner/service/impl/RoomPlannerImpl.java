package shtykh.roomplanner.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsAvailability;
import shtykh.roomplanner.model.RoomsUsage;
import shtykh.roomplanner.model.impl.RoomPlanImpl;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;
import shtykh.roomplanner.service.RoomPlanner;
import shtykh.roomplanner.service.RoomStateService;

import java.util.*;

import static java.lang.Math.max;
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
        Queue<Integer> queue = sortedQueueOf(roomRequest);
        RoomPlanImpl plan = new RoomPlanImpl();
        Map<RoomLevel, Integer> availableRooms = roomStateService.getAvailableRooms();
        RoomsUsage premium = fillPremium(queue, availableRooms);
        plan.add(premium);
        RoomsUsage economy = fillEconomy(queue, availableRooms);
        plan.add(economy);
        return plan;
    }

    @Override
    public void setAvailability(Map<RoomLevel, Integer> availabilities) {
        roomStateService.setAvailableRooms(availabilities);
    }

    private RoomsUsage fillPremium(Queue<Integer> queue, Map<RoomLevel, Integer> availableRooms) {
        RoomsUsageImpl premium = new RoomsUsageImpl(PREMIUM, 0, 0);
        // processing all high payers
        int premiumSlots = availableRooms.get(PREMIUM);
        while (queue.peek() != null && queue.peek() >= minPremiumPayment) {
            if (premium.getRoomsNumber() < premiumSlots) {
                premium.add(queue.poll());
            } else {
                Integer remove = queue.remove();
                log.fine(remove + " is not premium enough for " + premiumSlots + " rooms");
            }
        }
        // is any premium rooms left?
        int roomsForUpgrade = max(premiumSlots - premium.getRoomsNumber(), 0);
        if (roomsForUpgrade > 0) {
            int economySlots = availableRooms.get(ECONOMY);
            int customersToUpgrade = min(queue.size() - economySlots, roomsForUpgrade);
            while (customersToUpgrade > 0) {
                premium.add(queue.poll());
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

    private Queue<Integer> sortedQueueOf(List<Integer> desiredPayments) {
        desiredPayments.sort(Comparator.reverseOrder());
        return new LinkedList<>(desiredPayments);
    }
}
