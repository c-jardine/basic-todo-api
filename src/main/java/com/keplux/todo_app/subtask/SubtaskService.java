package com.keplux.todo_app.subtask;

import com.keplux.todo_app.task.TaskRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService {
  @Autowired
  private SubtaskRepository subtaskRepository;

  @Autowired
  private TaskRepository taskRepository;

  public ResponseEntity<List<Subtask>> getAllSubtasks() {
    List<Subtask> subtasks = subtaskRepository.findAll();

    return new ResponseEntity<>(subtasks, HttpStatus.OK);
  }

  public ResponseEntity<Subtask> getSubtasksById(Long subtaskId) {
    Subtask subtask = subtaskRepository.findById(subtaskId)
        .orElseThrow(() -> new NoSuchElementException("No Subtask with id " + subtaskId));

    return new ResponseEntity<>(subtask, HttpStatus.OK);
  }

  public ResponseEntity<List<Subtask>> getAllTaskSubtasks(Long taskId) {
    if (!taskRepository.existsById(taskId)) {
      throw new NoSuchElementException("No Task with id " + taskId);
    }

    return new ResponseEntity<>(subtaskRepository.findByTaskId(taskId), HttpStatus.OK);
  }

  public ResponseEntity<Subtask> createSubtask(Subtask newSubtask) {
    Subtask subtask = subtaskRepository.save(newSubtask);

    return new ResponseEntity<>(subtask, HttpStatus.OK);
  }

  public ResponseEntity<Subtask> updateSubtask(Long subtaskId, Subtask updatedSubtask) {
    Subtask subtask = subtaskRepository.findById(subtaskId)
        .orElseThrow(() -> new NoSuchElementException("No Subtask with id " + subtaskId));
    subtask.setTitle(updatedSubtask.getTitle());
    subtask.setIsComplete(updatedSubtask.getIsComplete());
    subtask.setTask(updatedSubtask.getTask());

    return new ResponseEntity<>(subtaskRepository.save(subtask), HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> deleteSubtaskById(Long subtaskId) {
    subtaskRepository.deleteById(subtaskId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
