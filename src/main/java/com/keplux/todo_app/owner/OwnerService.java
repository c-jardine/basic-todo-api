package com.keplux.todo_app.owner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

  @Autowired
  private OwnerRepository ownerRepository;

  public ResponseEntity<List<Owner>> getAllOwners() {
    List<Owner> owners = ownerRepository.findAll();

    return new ResponseEntity<>(owners, HttpStatus.OK);
  }

  public ResponseEntity<Owner> getOwnerById(Long ownerId) {
    Owner owner = ownerRepository.findById(ownerId)
        .orElseThrow(() -> new NoSuchElementException("No Owner with id " + ownerId));

    return new ResponseEntity<>(owner, HttpStatus.OK);
  }

  public ResponseEntity<Owner> createOwner(Owner newOwner) {
    Owner owner = ownerRepository.save(newOwner);

    return new ResponseEntity<>(owner, HttpStatus.OK);
  }

  public ResponseEntity<Owner> updateOwner(Long ownerId, Owner updatedOwner) {
    Optional<Owner> owner = ownerRepository.findById(ownerId);
    if (owner.isPresent()) {
      Owner o = owner.get();
      o.setFirstName(updatedOwner.getFirstName());
      o.setLastName(updatedOwner.getLastName());
      o.setEmail(updatedOwner.getEmail());
      o.setPassword(updatedOwner.getPassword());
      return new ResponseEntity<>(ownerRepository.save(o), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<HttpStatus> deleteOwnerById(Long ownerId) {
    if (!ownerRepository.existsById(ownerId)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ownerRepository.deleteById(ownerId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
