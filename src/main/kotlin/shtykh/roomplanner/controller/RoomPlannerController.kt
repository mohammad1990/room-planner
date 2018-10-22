package shtykh.roomplanner.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.model.RoomPlan
import shtykh.roomplanner.service.RoomPlanner
import shtykh.roomplanner.service.RoomStateService

@RestController
class RoomPlannerController(private val roomPlanner: RoomPlanner, private val stateService: RoomStateService) {


    @GetMapping("getRooms")
    fun getRooms() = stateService.availableRooms

    @PostMapping("plan")
    fun getPlan(@RequestBody roomRequest: List<Int>): RoomPlan {
        return roomPlanner.plan(roomRequest)
    }

    @PostMapping("updateRooms")
    fun updateRooms(@RequestBody roomsAvailability: Map<RoomLevel, Int>) {
        stateService.availableRooms = roomsAvailability
    }

}
