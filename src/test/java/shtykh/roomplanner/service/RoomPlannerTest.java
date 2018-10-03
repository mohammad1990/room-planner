package shtykh.roomplanner.service;

import org.junit.Before;
import org.junit.Test;
import shtykh.roomplanner.model.*;
import shtykh.roomplanner.model.impl.RoomsAvailabilityImpl;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static shtykh.roomplanner.model.RoomClass.ECONOMY;
import static shtykh.roomplanner.model.RoomClass.PREMIUM;

public class RoomPlannerTest {

    private RoomRequest request;
    private RoomPlanner roomPlanner; // TODO implement

    @Before
    public void setUp() throws Exception {
        request = () -> asList(23,
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
        List<RoomsAvailability> availabilities = asList(rooms(3, PREMIUM),
                                                        rooms(3, ECONOMY));
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 3, 738),
                                             roomUsage(ECONOMY, 3, 167));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test2() throws Exception {
        List<RoomsAvailability> availabilities = asList(rooms(7, PREMIUM),
                                                        rooms(5, ECONOMY));
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 6, 1054),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test3() throws Exception {
        List<RoomsAvailability> availabilities = asList(rooms(2, PREMIUM),
                                                        rooms(7, ECONOMY));
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 2, 583),
                                             roomUsage(ECONOMY, 4, 189));
        verifyPlan(availabilities, expectedPlan);
    }

    @Test
    public void test4() throws Exception {
        List<RoomsAvailability> availabilities = asList(rooms(7, PREMIUM),
                                                        rooms(1, ECONOMY));
        RoomPlan expectedPlan = () -> asList(roomUsage(PREMIUM, 7, 1153),
                                             roomUsage(ECONOMY, 1, 45));
        verifyPlan(availabilities, expectedPlan);
    }

    private void verifyPlan(final List<RoomsAvailability> availabilities, final RoomPlan expectedPlan) {
        roomPlanner.setAvailability(availabilities);
        RoomPlan actualPlan = roomPlanner.plan(request);
        assertNotNull(actualPlan);
        assertEquals(2, actualPlan.getRoomUsages().size());
        assertEquals(expectedPlan, actualPlan);
    }

    private RoomsUsage roomUsage(final RoomClass roomClass, final int number, final int sum) {
        return new RoomsUsage() {

            @Override
            public int getPaymentSum() {
                return sum;
            }

            @Override
            public int getRoomsNumber() {
                return number;
            }

            @Override
            public RoomClass getRoomClass() {
                return roomClass;
            }
        };
    }

    private RoomsAvailabilityImpl rooms(int roomsNumber, RoomClass economy) {
        return new RoomsAvailabilityImpl(roomsNumber, economy);
    }

}
