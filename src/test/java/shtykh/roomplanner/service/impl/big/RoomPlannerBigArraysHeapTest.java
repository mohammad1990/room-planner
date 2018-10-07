package shtykh.roomplanner.service.impl.big;

import shtykh.roomplanner.service.RoomPlanner;
import shtykh.roomplanner.service.impl.RoomPlannerHeapImpl;
import shtykh.roomplanner.service.impl.RoomStateServiceImpl;

public class RoomPlannerBigArraysHeapTest extends AbstractRoomPlannerBigArraysTest {

    private RoomPlanner roomPlannerHeap = new RoomPlannerHeapImpl(new RoomStateServiceImpl());

    public RoomPlanner getPlannerService() {
        return roomPlannerHeap;
    }
}
