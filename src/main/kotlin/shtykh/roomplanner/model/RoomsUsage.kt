package shtykh.roomplanner.model

import com.fasterxml.jackson.annotation.JsonAutoDetect

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RoomsUsage(var roomLevel: RoomLevel, var roomsNumber: Int = 0, var paymentSum: Int = 0) {

    fun add(payment: Int) {
        roomsNumber++
        paymentSum += payment
    }
}
