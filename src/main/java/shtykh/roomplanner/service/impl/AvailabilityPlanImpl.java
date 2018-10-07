package shtykh.roomplanner.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shtykh.roomplanner.model.AvailabilityPlan;
import shtykh.roomplanner.model.RoomLevel;

import java.util.Map;

@Getter
@AllArgsConstructor
public class AvailabilityPlanImpl implements AvailabilityPlan {
    private Map<RoomLevel, Integer> value;
    private int version;
}
