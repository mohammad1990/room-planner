package shtykh.roomplanner.service

import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.model.RoomPlan

interface RoomPlanner {

    fun plan(roomRequest: List<Int>): RoomPlan

    fun setAvailability(availabilities: Map<RoomLevel, Int>)
}
