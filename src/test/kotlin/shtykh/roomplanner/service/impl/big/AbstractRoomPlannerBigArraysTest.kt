package shtykh.roomplanner.service.impl.big

import org.junit.Test
import shtykh.roomplanner.model.RoomLevel.ECONOMY
import shtykh.roomplanner.model.RoomLevel.PREMIUM
import shtykh.roomplanner.model.RoomPlan
import shtykh.roomplanner.service.impl.RoomPlannerTests
import java.util.*
import java.util.Arrays.asList

abstract class AbstractRoomPlannerBigArraysTest : RoomPlannerTests() {

    @Test
    @Throws(Exception::class)
    fun planFull() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 100, 99)
        val availabilities = roomsAvailable(3, 3)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
            roomUsage(ECONOMY, 3, 297)))
        verifyPlan(availabilities, expectedPlan, request)

    }

    @Test
    @Throws(Exception::class)
    fun planOnlyEconomyPayers() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 99)
        val availabilities = roomsAvailable(3, 3)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 3, 297),
            roomUsage(ECONOMY, 3, 297)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun planOnlyPremiumPayers() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 100)
        val availabilities = roomsAvailable(3, 3)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
            roomUsage(ECONOMY, 0, 0)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun planAllPremiumOneEconomy() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 100)
        request.add(1)
        val availabilities = roomsAvailable(3, 3)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 3, 300),
            roomUsage(ECONOMY, 1, 1)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun planOnlyPremiumRooms() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 100, 99)
        val availabilities = roomsAvailable(100, 0)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 100, 100 * 100),
            roomUsage(ECONOMY, 0, 0)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    @Test
    @Throws(Exception::class)
    fun planOnlyEconomyRooms() {
        val request = generate(PAYMENTS_ARRAY_SIZE, 100, 99)
        val availabilities = roomsAvailable(0, 100)
        val expectedPlan = RoomPlan(asList(roomUsage(PREMIUM, 0, 0),
            roomUsage(ECONOMY, 100, 99 * 100)))
        verifyPlan(availabilities, expectedPlan, request)
    }

    private fun generate(repetition: Int, vararg progression: Int): MutableList<Int> {
        val list = ArrayList<Int>(repetition)
        for (i in 0 until repetition / progression.size) {
            for (number in progression) {
                list.add(number)
            }
        }
        return list
    }

    companion object {
        private const val PAYMENTS_ARRAY_SIZE = 50000000
    }

}
