package shtykh.roomplanner.service.impl;

import org.junit.Before;
import org.junit.Test;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static shtykh.roomplanner.model.RoomLevel.ECONOMY;
import static shtykh.roomplanner.model.RoomLevel.PREMIUM;

public class RoomPlannerProvidedTests extends RoomPlannerTests {

    private List<Integer> request;

    @Before
    public void setUp() throws Exception {
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
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2) {{
            put(PREMIUM, 3);
            put(ECONOMY, 3);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 3, 738),
                                             roomUsage(ECONOMY, 3, 167));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void test2() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2) {{
            put(PREMIUM, 7);
            put(ECONOMY, 5);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 6, 1054),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void test3() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2) {{
            put(PREMIUM, 2);
            put(ECONOMY, 7);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 2, 583),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan, request);
    }

    @Test
    public void test4() throws Exception {
        Map<RoomLevel, Integer> availabilities = new HashMap<RoomLevel, Integer>(2) {{
            put(PREMIUM, 7);
            put(ECONOMY, 1);
        }};
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 7, 1153),
                                             roomUsage(ECONOMY, 1, 45));
        verifyPlan(availabilities, expectedPlan, request);
    }
}
