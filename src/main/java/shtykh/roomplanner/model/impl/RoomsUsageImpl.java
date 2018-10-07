package shtykh.roomplanner.model.impl;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.model.RoomsUsage;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RoomsUsageImpl implements RoomsUsage {

    private final RoomLevel roomLevel;
    private int roomsNumber = 0;
    private int paymentSum  = 0;

    public void add(Integer payment) {
        roomsNumber++;
        paymentSum += payment;
    }
}
