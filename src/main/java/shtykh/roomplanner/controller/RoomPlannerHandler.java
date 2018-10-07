package shtykh.roomplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import shtykh.roomplanner.model.RoomPlan;
import shtykh.roomplanner.service.RoomPlanner;
import shtykh.roomplanner.service.RoomStateService;

import static shtykh.roomplanner.service.RoomPlanner.PLAN_REQUEST_TYPE;
import static shtykh.roomplanner.service.RoomStateService.UPDATE_ROOMS_REQUEST_TYPE;

@Component
public class RoomPlannerHandler {

    @Autowired
    private RoomPlanner roomPlanner;

    @Autowired
    private RoomStateService stateService;

    public Mono<ServerResponse> getRooms(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromObject(stateService.getAvailableRooms()));
    }

    public Mono<ServerResponse> updateRooms(ServerRequest request) {
        Mono<Boolean> publisher = request.bodyToMono(UPDATE_ROOMS_REQUEST_TYPE)
                                         .map(stateService::setAvailableRooms);
        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(publisher, Boolean.class));
    }

    public Mono<ServerResponse> getPlan(ServerRequest request) {
        Mono<RoomPlan> publisher = request.bodyToMono(PLAN_REQUEST_TYPE)
                                          .map(roomPlanner::plan);
        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(BodyInserters.fromPublisher(publisher, RoomPlan.class));
    }

}
