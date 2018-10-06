package shtykh.roomplanner.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsAvailability;
import shtykh.roomplanner.model.RoomsUsage;
import shtykh.roomplanner.model.impl.RoomPlanImpl;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static shtykh.roomplanner.model.RoomLevel.ECONOMY;
import static shtykh.roomplanner.model.RoomLevel.PREMIUM;

@Log
@Service
public class RoomPlannerImpl implements RoomPlanner {

    private final Object lock = new Object();

    @Value("${room.premium.min-price}")
    private Integer minPremiumPayment = 100;
    private int     economySlots      = 0;
    private int     premiumSlots      = 0;

    @Override
    public RoomPlan plan(List<Integer> roomRequest) {
        Queue<Integer> queue = sortedQueueOf(roomRequest);
        RoomPlanImpl plan = new RoomPlanImpl();
        synchronized (lock) {
            RoomsUsage premium = fillPremium(queue);
            plan.add(premium);
            RoomsUsage economy = fillEconomy(queue);
            plan.add(economy);
        }
        return plan;
    }

    private RoomsUsage fillPremium(Queue<Integer> queue) {
        RoomsUsageImpl premium = new RoomsUsageImpl(PREMIUM, 0, 0);
        // processing all high payers
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
            int customersToUpgrade = min(queue.size() - economySlots, roomsForUpgrade);
            while (customersToUpgrade > 0) {
                premium.add(queue.poll());
                customersToUpgrade--;
            }
        }
        return premium;
    }

    private RoomsUsage fillEconomy(Queue<Integer> queue) {
        RoomsUsageImpl economy = new RoomsUsageImpl(ECONOMY, 0, 0);
        int economyRoomsToFill = min(queue.size(), economySlots);
        while (economyRoomsToFill > 0) {
            economy.add(queue.poll());
            economyRoomsToFill--;
        }
        return economy;
    }

    private Queue<Integer> sortedQueueOf(List<Integer> desiredPayments) {
        desiredPayments.sort(Comparator.reverseOrder());
        LinkedList<Integer> queue = new LinkedList<>(desiredPayments);
        return queue;
    }

    @Override
    public void setAvailability(List<? extends RoomsAvailability> availabilities) {
        synchronized (lock) {
            availabilities.forEach(it -> {
                switch (it.getRoomLevel()) {
                    case ECONOMY:
                        economySlots = it.getRoomsNumber();
                        break;
                    case PREMIUM:
                        premiumSlots = it.getRoomsNumber();
                        break;
                    default:
                        String wrongRoomClassMsg = it.getRoomLevel() + " is not supported, sorry";
                        log.info(wrongRoomClassMsg);
                        throw new RuntimeException(wrongRoomClassMsg);
                }
            });
        }
    }
}
