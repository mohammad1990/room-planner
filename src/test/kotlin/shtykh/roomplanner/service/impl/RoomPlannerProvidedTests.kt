package shtykh.roomplanner.service.impl

import org.junit.Before
import org.junit.Test
import shtykh.roomplanner.model.RoomLevel.ECONOMY
import shtykh.roomplanner.model.RoomLevel.PREMIUM
import shtykh.roomplanner.model.RoomPlan
import java.util.Arrays.asList

class RoomPlannerProvidedTests : RoomPlannerTests() {

    private lateinit var request: List<Int>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        request = asList(23,
            45,
            155,
            374,
            22,
            99,
            100,
            101,
            115,
            209)
    }

    @Test
    @Throws(Exception::class)
    fun test1() {
        val availabilities = roomsAvailable(3, 3)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 3, 738),
            roomUsage(ECONOMY, 3, 167)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun test2() {
        val availabilities = roomsAvailable(7, 5)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 6, 1054),
            roomUsage(ECONOMY, 4, 189)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun test3() {
        val availabilities = roomsAvailable(2, 7)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 2, 583),
            roomUsage(ECONOMY, 4, 189)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun test4() {
        val availabilities = roomsAvailable(7, 1)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 7, 1153),
            roomUsage(ECONOMY, 1, 45)))
        verifyPlan(availabilities, expectedPlan, request)
    }
}
