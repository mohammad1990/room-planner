package shtykh.roomplanner.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomsAvailability;

@Getter
@AllArgsConstructor
public class RoomsAvailabilityImpl implements RoomsAvailability {

    private final RoomLevel roomLevel;
    private final int       roomsNumber;
}
