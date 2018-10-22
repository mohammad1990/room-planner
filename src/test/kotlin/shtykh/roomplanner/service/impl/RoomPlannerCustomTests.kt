package shtykh.roomplanner.service.impl

import org.junit.Test
import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.model.RoomPlan
import shtykh.roomplanner.service.RoomPlanner

import java.util.*

import java.util.Arrays.asList
import shtykh.roomplanner.model.RoomLevel.ECONOMY
import shtykh.roomplanner.model.RoomLevel.PREMIUM

class RoomPlannerCustomTests : RoomPlannerTests() {

    @Test
    @Throws(Exception::class)
    fun emptyPayments() {
        val request = emptyList<Int>()
        val availabilities = roomsAvailable(7, 1)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 0, 0),
            roomUsage(ECONOMY, 0, 0)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun emptyAvailable() {
        val request = Arrays.asList(100, 101, 102)
        val availabilities = emptyMap<RoomLevel, Int>()
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 0, 0),
            roomUsage(ECONOMY, 0, 0)))
        verifyPlan(availabilities, expectedPlan, request)
    }
}
