package shtykh.roomplanner.service;

import shtykh.roomplanner.model.*;
import shtykh.roomplanner.model.impl.RoomPlanImpl;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static shtykh.roomplanner.model.RoomLevel.*;

public class RoomPlannerImpl implements RoomPlanner {

    private static final Integer MIN_PREMIUM_PAYMENT = 100; // TODO
    private int economySlots                     = 0;
    private int premiumSlots                     = 0;

    @Override
    public RoomPlan plan(RoomRequest roomRequest) {
        Queue<Integer> queue = sortedQueueOf(roomRequest.getDesiredPayments());
        RoomPlanImpl plan = new RoomPlanImpl();
        RoomsUsageImpl premium = new RoomsUsageImpl(PREMIUM, 0, 0);
        // processing all high payers
        while (queue.peek() != null && queue.peek() >= MIN_PREMIUM_PAYMENT) {
            if (premium.getRoomsNumber() < premiumSlots) {
                premium.add(queue.poll());
            } else {
                queue.remove();
            }
        }
        // is any premium rooms left?
        int roomsForUpgrade = max(premiumSlots - premium.getRoomsNumber(), 0);
        if (roomsForUpgrade > 0) {
            int customersToUpgrade = min(queue.size() - economySlots, roomsForUpgrade);
            while (customersToUpgrade > 0) {
                premium.add(queue.poll());
                customersToUpgrade --;
            }
        }
        plan.add(premium);
        RoomsUsageImpl economy = new RoomsUsageImpl(ECONOMY, 0, 0);
        int economyRoomsToFill = min(queue.size(), economySlots);
        while (economyRoomsToFill > 0) {
            economy.add(queue.poll());
            economyRoomsToFill--;
        }
        plan.add(economy);
        return plan;
    }

    private Queue<Integer> sortedQueueOf(List<Integer> desiredPayments) {
        LinkedList<Integer> queue = new LinkedList<>(desiredPayments);
        queue.sort(Comparator.reverseOrder()); // TODO think of complexity
        return queue;
    }

    @Override
    public void setAvailability(List<RoomsAvailability> availabilities) {
        availabilities.forEach(it -> {
            switch (it.getRoomLevel()) {
                case ECONOMY:
                    economySlots = it.getRoomsNumber();
                    break;
                case PREMIUM:
                    premiumSlots = it.getRoomsNumber();
                    break;
                default: throw new RuntimeException(it.getRoomLevel() + " is not supported, sorry");
            }
        });
    }
}
