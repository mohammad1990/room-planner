package shtykh.roomplanner.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoomPlannerRouter {

    @Bean
    public RouterFunction<ServerResponse> routeGet(RoomPlannerHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/getRooms")
                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getRooms);
    }

    @Bean
    public RouterFunction<ServerResponse> getPlan(RoomPlannerHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/plan")
                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getPlan);
    }

    @Bean
    public RouterFunction<ServerResponse> updateRooms(RoomPlannerHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/updateRooms")
                                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::updateRooms);
    }
}
