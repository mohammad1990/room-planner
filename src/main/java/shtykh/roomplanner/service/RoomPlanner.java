package shtykh.roomplanner.service;

import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsAvailability;

import java.util.List;

public interface RoomPlanner {

    RoomPlan plan(List<Integer> roomRequest);

    void setAvailability(List<? extends RoomsAvailability> availabilities);
}
