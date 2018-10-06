package shtykh.roomplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.impl.RoomsAvailabilityImpl;
import shtykh.roomplanner.service.RoomPlanner;

import java.util.List;

@RestController
public class RoomPlannerController {

    @Autowired
    private RoomPlanner service;

    @PostMapping("updateRooms")
    public void updateRooms(@RequestBody List<RoomsAvailabilityImpl> roomsAvailability) {
        service.setAvailability(roomsAvailability);
    }

    @PostMapping("plan")
    public RoomPlan getPlan(@RequestBody List<Integer> roomRequest) {
        return service.plan(roomRequest);
    }

}
