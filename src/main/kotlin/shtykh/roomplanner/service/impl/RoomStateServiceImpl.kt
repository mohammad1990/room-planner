package shtykh.roomplanner.service.impl

import org.springframework.stereotype.Repository
import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.service.RoomStateService

import java.util.HashMap

import java.util.Collections.unmodifiableMap

@Repository
class RoomStateServiceImpl : RoomStateService {
    override var availableRooms: Map<RoomLevel, Int>
        get() {
            synchronized(lock) {
                return availableRoomsInternal
            }
        }
        set(value) {
            synchronized(lock) {
                this.availableRoomsInternal = unmodifiableMap(HashMap(value))
            }
        }

    private val lock = Any()
    private var availableRoomsInternal = emptyMap<RoomLevel, Int>()
}
