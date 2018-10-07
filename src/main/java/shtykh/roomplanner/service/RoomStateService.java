package shtykh.roomplanner.service;

import shtykh.roomplanner.model.RoomLevel;

import java.util.Map;

public interface RoomStateService {

    Map<RoomLevel, Integer> getAvailableRooms();

    void setAvailableRooms(Map<RoomLevel, Integer> availabilities);
}
