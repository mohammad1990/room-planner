package shtykh.roomplanner.service;

import shtykh.roomplanner.model.AvailabilityPlan;
import shtykh.roomplanner.model.RoomLevel;

import java.util.Map;

public interface RoomStateService {

    AvailabilityPlan getAvailableRooms();

    void setAvailableRooms(Map<RoomLevel, Integer> availabilities);

    int getVersion();
}
