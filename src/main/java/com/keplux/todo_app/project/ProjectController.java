package com.keplux.todo_app.project;

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
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getAllProjects() {
    return projectService.getAllProjects();
  }

  @GetMapping("/projects/{projectId}")
  public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
    return projectService.getProjectById(projectId);
  }

  @GetMapping("/owners/{ownerId}/projects")
  public ResponseEntity<List<Project>> getAllOwnerProjects(@PathVariable Long ownerId) {
    return projectService.getAllOwnerProjects(ownerId);
  }

  @PostMapping("/projects")
  public ResponseEntity<Project> createProject(@RequestBody Project newProject) {
    return projectService.createProject(newProject);
  }

  @PutMapping("/projects/{projectId}")
  public ResponseEntity<Project> updateProject(@PathVariable Long projectId,
      @RequestBody Project updatedProject) {
    return projectService.updateProject(projectId, updatedProject);
  }

  @DeleteMapping("/projects/{projectId}")
  public ResponseEntity<HttpStatus> deleteProject(@PathVariable Long projectId) {
    return projectService.deleteProjectById(projectId);
  }
}
