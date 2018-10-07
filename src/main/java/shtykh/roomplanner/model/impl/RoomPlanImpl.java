package shtykh.roomplanner.model.impl;

import lombok.Getter;
import lombok.Setter;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.model.RoomsUsage;

import java.util.ArrayList;
import java.util.List;

public class RoomPlanImpl implements RoomPlan {

    @Getter
    private List<RoomsUsage> value = new ArrayList<>();

    @Getter
    @Setter
    private int version;

    public boolean add(RoomsUsage roomsUsage) {
        return value.add(roomsUsage);
    }
}
