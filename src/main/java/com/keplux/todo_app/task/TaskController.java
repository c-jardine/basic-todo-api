package com.keplux.todo_app.task;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping("/tasks")
  public ResponseEntity<List<Task>> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
    return taskService.getTaskById(taskId);
  }

  @GetMapping("/projects/{projectId}/tasks")
  public ResponseEntity<List<Task>> getAllProjectTasks(@PathVariable Long projectId) {
    return taskService.getAllProjectTasks(projectId);
  }

  @PostMapping("/tasks")
  public ResponseEntity<Task> createTask(@RequestBody Task newTask) {
    return taskService.createTask(newTask);
  }

  @PutMapping("/tasks/{taskId}")
  public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
    return taskService.updateTask(taskId, updatedTask);
  }

  @DeleteMapping("/tasks")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long taskId) {
    return taskService.deleteTaskById(taskId);
  }
}
