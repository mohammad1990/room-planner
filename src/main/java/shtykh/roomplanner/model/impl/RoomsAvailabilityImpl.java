package shtykh.roomplanner.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomsAvailability;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsAvailabilityImpl implements RoomsAvailability {

    private RoomLevel roomLevel;
    private int       roomsNumber;
}
