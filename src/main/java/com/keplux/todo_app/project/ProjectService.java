package com.keplux.todo_app.project;

import com.keplux.todo_app.owner.OwnerRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  public ResponseEntity<List<Project>> getAllProjects() {
    List<Project> projects = projectRepository.findAll();

    return new ResponseEntity<>(projects, HttpStatus.OK);
  }

  public ResponseEntity<Project> getProjectById(Long projectId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NoSuchElementException("No Project with id " + projectId));

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  public ResponseEntity<List<Project>> getAllOwnerProjects(Long ownerId) {
    if (!ownerRepository.existsById(ownerId)) {
      throw new NoSuchElementException("No Owner with id " + ownerId);
    }

    return new ResponseEntity<>(projectRepository.findByOwnerId(ownerId), HttpStatus.OK);
  }

  public ResponseEntity<Project> createProject(Project newProject) {
    Project project = projectRepository.save(newProject);

    return new ResponseEntity<>(project, HttpStatus.OK);
  }

  public ResponseEntity<Project> updateProject(Long projectId, Project updatedProject) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new NoSuchElementException("No Project with id " + projectId));
    project.setTitle(updatedProject.getTitle());
    project.setDescription(updatedProject.getDescription());

    return new ResponseEntity<>(projectRepository.save(project), HttpStatus.OK);
  }

  public ResponseEntity<HttpStatus> deleteProjectById(Long projectId) {
    projectRepository.deleteById(projectId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
