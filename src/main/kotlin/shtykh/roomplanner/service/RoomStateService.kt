package shtykh.roomplanner.service

import shtykh.roomplanner.model.RoomLevel

interface RoomStateService {
    var availableRooms: Map<RoomLevel, Int>
}
