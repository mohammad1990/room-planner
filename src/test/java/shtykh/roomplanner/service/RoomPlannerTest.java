package shtykh.roomplanner.service;

import org.junit.Before;
import org.junit.Test;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsUsage;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;
import shtykh.roomplanner.service.impl.RoomPlannerImpl;
import shtykh.roomplanner.service.impl.RoomStateServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static shtykh.roomplanner.model.RoomLevel.ECONOMY;
import static shtykh.roomplanner.model.RoomLevel.PREMIUM;

public class RoomPlannerTest {

    private List<Integer> request;
    private RoomPlanner roomPlanner;

    @Before
    public void setUp() throws Exception {
        roomPlanner = new RoomPlannerImpl(new RoomStateServiceImpl());
        request = asList(23,
                         45,
                         155,
                         374,
                         22,
                         99,
                         100,
                         101,
                         115,
                         209);
    }

    @Test
    public void test1() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2){{
            put(PREMIUM, 3);
            put(ECONOMY, 3);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 3, 738),
                                             roomUsage(ECONOMY, 3, 167));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test2() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2){{
            put(PREMIUM, 7);
            put(ECONOMY, 5);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 6, 1054),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test3() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2){{
            put(PREMIUM, 2);
            put(ECONOMY, 7);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 2, 583),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test4() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2){{
            put(PREMIUM, 7);
            put(ECONOMY, 1);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 7, 1153),
                                             roomUsage(ECONOMY, 1, 45));
        verifyPlan(availabilities, expectedPlan);
    }

    private void verifyPlan(final Map<RoomLevel, Integer> availabilities, final RoomPlan expectedPlan) {
        roomPlanner.setAvailability(availabilities);
        RoomPlan actualPlan = roomPlanner.plan(request);
        assertNotNull(actualPlan);
        assertEquals(2, actualPlan.getRoomsUsages().size());
        assertEquals(expectedPlan.getRoomsUsages(), actualPlan.getRoomsUsages());
    }

    private RoomsUsage roomUsage(final RoomLevel roomLevel, final int number, final int sum) {
        return new RoomsUsageImpl(roomLevel, number, sum);
    }

}
