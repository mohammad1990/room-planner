package shtykh.roomplanner.service.impl;

import org.springframework.stereotype.Repository;
import shtykh.roomplanner.model.AvailabilityPlan;
import shtykh.roomplanner.model.RoomLevel;
import shtykh.roomplanner.service.RoomStateService;

import java.util.Collections;
import java.util.Map;

@Repository
public class RoomStateServiceImpl implements RoomStateService {

    private final Object lock = new Object();

    private Map<RoomLevel, Integer> availableRooms = Collections.emptyMap();
    private int version = 0;

    @Override
    public AvailabilityPlan getAvailableRooms() {
        synchronized (lock) {
            return new AvailabilityPlanImpl(availableRooms, version);
        }
    }

    @Override
    public void setAvailableRooms(Map<RoomLevel, Integer> availabilities) {
        synchronized (lock) {
            this.availableRooms = availabilities;
            version ++;
        }
    }

    @Override
    public int getVersion() {
        synchronized (lock) {
            return version;
        }
    }
}
