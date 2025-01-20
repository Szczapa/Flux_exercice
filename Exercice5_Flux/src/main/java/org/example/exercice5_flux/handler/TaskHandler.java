package org.example.exercice5_flux.handler;

import org.example.exercice5_flux.entity.Task;
import org.example.exercice5_flux.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TaskHandler {

    private final TaskService taskService;

    public TaskHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    public Mono<ServerResponse> getTasks(ServerRequest request) {
        Flux<Task> tasks = Flux.fromIterable(taskService.getTasks());
        return ServerResponse.ok().body(tasks, Task.class);
    }

    public Mono<ServerResponse> getTask(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Task task = taskService.getTask(id);

        if (task != null) {
            return ServerResponse.ok().body(Mono.just(task), Task.class);
        }
        return ServerResponse.notFound().build();
    }

    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(Task.class)
                .map(task -> taskService.addTask(task))
                .flatMap(response -> ServerResponse.ok().bodyValue(response.getBody()));
    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(Task.class)
                .map(task -> taskService.updateTask(id, task))
                .flatMap(response -> ServerResponse.ok().bodyValue(response.getBody()));
    }

    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        ResponseEntity<String> response = taskService.removeTask(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ServerResponse.ok().bodyValue(response.getBody());
        }
        return ServerResponse.notFound().build();
    }
}
