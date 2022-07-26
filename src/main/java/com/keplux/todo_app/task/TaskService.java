package com.keplux.todo_app.task;

import com.keplux.todo_app.project.ProjectRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ProjectRepository projectRepository;

  public ResponseEntity<List<Task>> getAllTasks() {
    List<Task> tasks = taskRepository.findAll();

    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }

  public ResponseEntity<Task> getTaskById(Long taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NoSuchElementException("No Task with id " + taskId));

    return new ResponseEntity<>(task, HttpStatus.OK);
  }

  public ResponseEntity<List<Task>> getAllProjectTasks(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new NoSuchElementException("No Project with id " + projectId);
    }

    return new ResponseEntity<>(taskRepository.findByProjectId(projectId), HttpStatus.OK);
  }

  public ResponseEntity<Task> createTask(Task newTask) {
    Task task = taskRepository.save(newTask);

    return new ResponseEntity<>(task, HttpStatus.OK);
  }

  public ResponseEntity<Task> updateTask(Long taskId, Task updatedTask) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NoSuchElementException("No Task with id " + taskId));
    task.setTitle(updatedTask.getTitle());
    task.setIsComplete(updatedTask.getIsComplete());
    task.setProject(updatedTask.getProject());

    return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> deleteTaskById(Long taskId) {
    taskRepository.deleteById(taskId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
