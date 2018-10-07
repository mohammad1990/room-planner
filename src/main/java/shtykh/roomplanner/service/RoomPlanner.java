package shtykh.roomplanner.service;

import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;

import java.util.List;
import java.util.Map;

public interface RoomPlanner {

    RoomPlan plan(List<Integer> roomRequest);

    void setAvailability(Map<RoomLevel, Integer> availabilities);
}
