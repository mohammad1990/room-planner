package shtykh.roomplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shtykh.roomplanner.model.AvailabilityPlan;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.service.RoomPlanner;
import shtykh.roomplanner.service.RoomStateService;

import java.util.List;
import java.util.Map;

@RestController
public class RoomPlannerController {

    @Autowired
    private RoomPlanner roomPlanner;

    @Autowired
    private RoomStateService stateService;

    @PostMapping("updateRooms")
    public AvailabilityPlan updateRooms(@RequestBody Map<RoomLevel, Integer> roomsAvailability) {
        stateService.setAvailableRooms(roomsAvailability);
        return stateService.getAvailableRooms();
    }

    @GetMapping("getRooms")
    public AvailabilityPlan getRooms() {
        return stateService.getAvailableRooms();
    }

    @PostMapping("plan")
    public RoomPlan getPlan(@RequestBody List<Integer> roomRequest) {
        return roomPlanner.plan(roomRequest);
    }

}
