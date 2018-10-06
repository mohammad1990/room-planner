package shtykh.roomplanner.service;

import shtykh.roomplanner.model.RoomsAvailability;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomRequest;

import java.util.List;

public interface RoomPlanner {

    RoomPlan plan(RoomRequest roomRequest);

    void setAvailability(List<RoomsAvailability> availabilities);
}
