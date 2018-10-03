package shtykh.roomplanner.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shtykh.roomplanner.model.RoomClass;
import shtykh.roomplanner.model.RoomsAvailability;

@Getter
@AllArgsConstructor
public class RoomsAvailabilityImpl implements RoomsAvailability {
    int       roomsNumber;
    RoomClass roomClass;
}
