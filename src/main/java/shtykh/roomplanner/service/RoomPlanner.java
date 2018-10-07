package shtykh.roomplanner.service;

import org.springframework.core.ParameterizedTypeReference;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomPlan;

import java.util.List;
import java.util.Map;

public interface RoomPlanner {

    ParameterizedTypeReference<List<Integer>> PLAN_REQUEST_TYPE
            = new ParameterizedTypeReference<List<Integer>>() {};

    RoomPlan plan(List<Integer> roomRequest);

    void setAvailability(Map<RoomLevel, Integer> availabilities);
}
