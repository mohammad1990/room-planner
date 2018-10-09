package shtykh.roomplanner.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoomsUsage {

    private final RoomLevel roomLevel;
    private int roomsNumber = 0;
    private int paymentSum  = 0;

    public void add(int payment) {
        roomsNumber++;
        paymentSum += payment;
    }
}
