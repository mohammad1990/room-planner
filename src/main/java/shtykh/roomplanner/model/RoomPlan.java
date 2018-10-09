package shtykh.roomplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoomPlan {

    private final List<RoomsUsage> roomsUsages;
}
