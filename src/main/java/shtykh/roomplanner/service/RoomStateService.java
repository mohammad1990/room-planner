package shtykh.roomplanner.service;

import org.springframework.core.ParameterizedTypeReference;
import shtykh.roomplanner.model.RoomLevel;

import java.util.Map;

public interface RoomStateService {

    ParameterizedTypeReference<Map<RoomLevel, Integer>> UPDATE_ROOMS_REQUEST_TYPE
            = new ParameterizedTypeReference<Map<RoomLevel, Integer>>() {};

    Map<RoomLevel, Integer> getAvailableRooms();

    boolean setAvailableRooms(Map<RoomLevel, Integer> availabilities);
}
