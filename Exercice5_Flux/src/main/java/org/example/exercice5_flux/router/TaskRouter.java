package org.example.exercice5_flux.router;

import org.example.exercice5_flux.handler.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskRouter {

    @Bean
    public RouterFunction<ServerResponse> taskRoutes(TaskHandler taskHandler) {
        return route(GET("/api/tasks"), taskHandler::getTasks)
                .andRoute(GET("/api/tasks/{id}"), taskHandler::getTask)
                .andRoute(POST("/api/tasks"), taskHandler::createTask)
                .andRoute(PUT("/api/tasks/{id}"), taskHandler::updateTask)
                .andRoute(DELETE("/api/tasks/{id}"), taskHandler::deleteTask);
    }
}
