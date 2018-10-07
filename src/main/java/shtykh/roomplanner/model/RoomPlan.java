package shtykh.roomplanner.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public interface RoomPlan extends Versioned<List<RoomsUsage>> {
    @Override
    default int getVersion() {
        throw new NotImplementedException();
    }
}
