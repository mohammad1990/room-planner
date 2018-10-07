package shtykh.roomplanner.model.impl;

import lombok.Getter;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsUsage;

import java.util.ArrayList;
import java.util.List;

public class RoomPlanImpl implements RoomPlan {

    @Getter
    private final List<RoomsUsage> roomsUsages = new ArrayList<>();

    protected boolean add(RoomsUsage roomsUsage) {
        return roomsUsages.add(roomsUsage);
    }
}
