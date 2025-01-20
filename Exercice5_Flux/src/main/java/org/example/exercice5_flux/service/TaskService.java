package org.example.exercice5_flux.service;

import org.example.exercice5_flux.entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    List<Task> tasks = new ArrayList<>();

    public TaskService() {
        Collections.addAll(tasks,
                new Task(1, "laver voiture", false),
                new Task(2, "Manger", true),
                new Task(3, "Couper du bois", false)
        );
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int id) {
        return tasks.stream().filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public ResponseEntity<String> addTask(Task task) {

        if (!tasks.contains(task)) {
            tasks.add(task);
            return ResponseEntity.ok("Task added");
        }
        return ResponseEntity.badRequest().body("Task already exists");
    }

    public ResponseEntity<String> removeTask(int id) {

        if (tasks.contains(getTask(id))) {
            tasks.remove(getTask(id));
            return ResponseEntity.ok("Task removed");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> updateTask(int id, Task task) {
        Task existingTask = getTask(id);
        if (existingTask != null) {
            existingTask.setDescription(task.getDescription());
            existingTask.setCompleted(task.isCompleted());
            return ResponseEntity.ok("Task updated");
        }
        return ResponseEntity.notFound().build();
    }
}
