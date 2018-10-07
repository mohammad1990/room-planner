package shtykh.roomplanner.service.impl;

import org.junit.Before;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsUsage;
import shtykh.roomplanner.model.impl.RoomsUsageImpl;
import shtykh.roomplanner.service.RoomPlanner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class RoomPlannerTests {

    protected RoomPlanner roomPlanner;

    @Before
    public void init() {
        roomPlanner = getPlannerService();
    }

    protected RoomPlanner getPlannerService() {
        return new RoomPlannerHeapImpl(new RoomStateServiceImpl());
    }

    protected void verifyPlan(final Map<RoomLevel, Integer> availabilities, final RoomPlan expectedPlan,
                              List<Integer> request) {
        roomPlanner.setAvailability(availabilities);
        RoomPlan actualPlan = roomPlanner.plan(request);
        assertNotNull(actualPlan);
        assertEquals(2, actualPlan.getRoomsUsages().size());
        assertEquals(expectedPlan.getRoomsUsages(), actualPlan.getRoomsUsages());
    }

    protected RoomsUsage roomUsage(final RoomLevel roomLevel, final int number, final int sum) {
        return new RoomsUsageImpl(roomLevel, number, sum);
    }
}
