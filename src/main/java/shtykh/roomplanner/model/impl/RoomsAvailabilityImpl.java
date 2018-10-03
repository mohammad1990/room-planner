package shtykh.roomplanner.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shtykh.roomplanner.model.RoomClass;
import shtykh.roomplanner.model.RoomClassAvailability;

@Getter
@AllArgsConstructor
public class RoomClassAvailabilityImpl implements RoomClassAvailability {
    int       roomsNumber;
    RoomClass roomClass;
}
