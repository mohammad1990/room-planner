package shtykh.roomplanner.service.impl.big;

import org.junit.Test;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.service.impl.RoomPlannerTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static shtykh.roomplanner.model.RoomLevel.ECONOMY;
import static shtykh.roomplanner.model.RoomLevel.PREMIUM;

public abstract class AbstractRoomPlannerBigArraysTest extends RoomPlannerTests {

    private static final int PAYMENTS_ARRAY_SIZE = 50000000;

    @Test
    public void planFull() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 100, 99);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(3, 3);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
                                                    roomUsage(ECONOMY, 3, 297)));
        verifyPlan(availabilities, expectedPlan, request);

    }

    @Test
    public void planOnlyEconomyPayers() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 99);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(3, 3);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 3, 297),
                                                    roomUsage(ECONOMY, 3, 297)));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void planOnlyPremiumPayers() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 100);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(3, 3);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
                                                    roomUsage(ECONOMY, 0, 0)));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void planAllPremiumOneEconomy() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 100);
        request.add(1);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(3, 3);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
                                                    roomUsage(ECONOMY, 1, 1)));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void planOnlyPremiumRooms() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 100, 99);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(100, 0);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 100, 100 * 100),
                                                    roomUsage(ECONOMY, 0, 0)));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void planOnlyEconomyRooms() throws Exception {
        List<Integer> request = generate(PAYMENTS_ARRAY_SIZE, 100, 99);
        Map<RoomLevel, Integer> availabilities = roomsAvailable(0, 100);
        RoomPlan expectedPlan = new RoomPlan(asList(roomUsage(PREMIUM, 0, 0),
                                                    roomUsage(ECONOMY, 100, 99 * 100)));
        verifyPlan(availabilities, expectedPlan, request);
    }

    private List<Integer> generate(int repetition, int... progression) {
        List<Integer> list = new ArrayList<>(repetition);
        for (int i = 0; i < repetition / progression.length; i++) {
            for (int number : progression) {
                list.add(number);
            }
        }
        return list;
    }

}
