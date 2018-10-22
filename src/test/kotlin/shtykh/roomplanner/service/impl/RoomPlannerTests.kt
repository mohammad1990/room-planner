package shtykh.roomplanner.service.impl

import org.junit.Before
import shtykh.roomplanner.config.RoomConfig
import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.model.RoomPlan
import shtykh.roomplanner.model.RoomsUsage
import shtykh.roomplanner.service.RoomPlanner

import java.util.HashMap

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import shtykh.roomplanner.model.RoomLevel.ECONOMY
import shtykh.roomplanner.model.RoomLevel.PREMIUM

abstract class RoomPlannerTests {

    protected lateinit var roomPlanner: RoomPlanner

    protected val plannerService: RoomPlanner
        get() = RoomPlannerHeapImpl(RoomStateServiceImpl(), RoomConfig())

    @Before
    fun init() {
        roomPlanner = plannerService
    }

    protected fun verifyPlan(availabilities: Map<RoomLevel, Int>, expectedPlan: RoomPlan,
                             request: List<Int>) {
        roomPlanner.setAvailability(availabilities)
        val actualPlan = roomPlanner.plan(request)
        assertNotNull(actualPlan)
        assertEquals(2, actualPlan.roomsUsages.size.toLong())
        assertEquals(expectedPlan.roomsUsages, actualPlan.roomsUsages)
    }

    protected fun roomUsage(roomLevel: RoomLevel, number: Int, sum: Int): RoomsUsage {
        return RoomsUsage(roomLevel, number, sum)
    }

    protected fun roomsAvailable(premium: Int, economy: Int): Map<RoomLevel, Int> {
        val availabilities: MutableMap<RoomLevel, Int>
        availabilities = HashMap(2)
        availabilities[PREMIUM] = premium
        availabilities[ECONOMY] = economy
        return availabilities
    }
}
