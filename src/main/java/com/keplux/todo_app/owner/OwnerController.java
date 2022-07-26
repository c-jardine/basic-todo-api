package com.keplux.todo_app.owner;

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
public class OwnerController {

  @Autowired
  private OwnerService ownerService;

  @GetMapping("/owners")
  public ResponseEntity<List<Owner>> getAllOwners() {
    return ownerService.getAllOwners();
  }

  @GetMapping("/owners/{ownerId}")
  public ResponseEntity<Owner> getOwnerById(@PathVariable Long ownerId) {
    return ownerService.getOwnerById(ownerId);
  }

  @PostMapping("/owners")
  public ResponseEntity<Owner> createOwner(@RequestBody Owner newOwner) {
    return ownerService.createOwner(newOwner);
  }

  @PutMapping("/owners/{ownerId}")
  public ResponseEntity<Owner> updateOwner(@PathVariable Long ownerId, @RequestBody Owner updatedOwner) {
    return ownerService.updateOwner(ownerId, updatedOwner);
  }

  @DeleteMapping("/owners/{ownerId}")
  public ResponseEntity<HttpStatus> deleteOwner(@PathVariable Long ownerId) {
    return ownerService.deleteOwnerById(ownerId);
  }
}
