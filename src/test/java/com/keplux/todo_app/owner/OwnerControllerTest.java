package com.keplux.todo_app.owner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper mapper;

  @MockBean
  OwnerService ownerService;

  Owner owner1 = new Owner(1L, "Chris", "Jardine", "test@email.com", "test123");
  Owner owner2 = new Owner(2L, "Jane", "Doe", "jane@doe.com", "test456");
  Owner owner3 = new Owner(3L, "John", "Doe", "john@doe.com", "test789");

  /**
   * Get all owners.
   */
  @Test
  public void getAllOwners_success() throws Exception {
    List<Owner> owners = new ArrayList<>(Arrays.asList(owner1, owner2, owner3));

    Mockito.when(ownerService.getAllOwners())
        .thenReturn(new ResponseEntity<>(owners, HttpStatus.OK));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/owners").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[2].firstName", is("John")))
        .andExpect(jsonPath("$[2].lastName", is("Doe")))
        .andExpect(jsonPath("$[2].email", is("john@doe.com")))
        .andExpect(jsonPath("$[2].password", is("test789")));
  }

  /**
   * Get owner by id.
   */
  @Test
  public void getOwnerById_success() throws Exception {
    Mockito.when(ownerService.getOwnerById(any(Long.class)))
        .thenReturn(new ResponseEntity<>(owner2, HttpStatus.OK));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/owners/{ownerId}", 2L)
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Jane"))).andExpect(jsonPath("lastName", is("Doe")))
        .andExpect(jsonPath("email", is("jane@doe.com")))
        .andExpect(jsonPath("password", is("test456")));
  }

  /**
   * Create an owner.
   */
  @Test
  public void createOwner_success() throws Exception {
    Owner owner = Owner.builder().id(5L).firstName("Steve").lastName("Jobs").email("test@email.com")
        .password("Apple!123").build();

    Mockito.when(ownerService.createOwner(any(Owner.class)))
        .thenReturn(new ResponseEntity<>(owner, HttpStatus.OK));

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/owners")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(owner));

    mockMvc.perform(mockRequest).andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Steve"))).andExpect(jsonPath("lastName", is("Jobs")))
        .andExpect(jsonPath("email", is("test@email.com")))
        .andExpect(jsonPath("password", is("Apple!123")));
  }

  /**
   * Update owner.
   */
  @Test
  public void updateOwner_success() throws Exception {
    // Create initial owner
    Mockito.when(ownerService.createOwner(any(Owner.class)))
        .thenReturn(new ResponseEntity<>(owner1, HttpStatus.OK));

    MockHttpServletRequestBuilder mockCreateRequest = MockMvcRequestBuilders.post("/api/v1/owners")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(owner1));

    mockMvc.perform(mockCreateRequest).andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Chris")))
        .andExpect(jsonPath("lastName", is("Jardine")));

    // Update owner
    Owner updatedOwner = Owner.builder().firstName("Elton").lastName("John")
        .email("rocketman@earth.com").password("EJ123").build();

    Mockito.when(ownerService.updateOwner(any(Long.class), any(Owner.class)))
        .thenReturn(new ResponseEntity<>(updatedOwner, HttpStatus.OK));

    MockHttpServletRequestBuilder mockUpdateRequest = put("/api/v1/owners/{ownerId}",
        1L).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(updatedOwner));

    mockMvc.perform(mockUpdateRequest).andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Elton"))).andExpect(jsonPath("lastName", is("John")))
        .andExpect(jsonPath("email", is("rocketman@earth.com")))
        .andExpect(jsonPath("password", is("EJ123")));

  }

  @Test
  public void updateOwner_idNotFound() throws Exception {
    Owner updatedOwner = Owner.builder().id(32L).firstName("Test").lastName("User")
        .email("test@user.com").password("testy123").build();

    Mockito.when(ownerService.updateOwner(any(Long.class), any(Owner.class)))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

    MockHttpServletRequestBuilder mockRequest = put("/api/v1/owners/{ownerId}", 32L).contentType(
            MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(updatedOwner));

    mockMvc.perform(mockRequest).andExpect(status().isNotFound());
  }

  @Test
  public void deleteOwner_success() throws Exception {
    Mockito.when(ownerService.createOwner(any(Owner.class)))
        .thenReturn(new ResponseEntity<>(owner1, HttpStatus.OK));

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/owners")
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(owner1));

    mockMvc.perform(mockRequest).andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Chris"))).andExpect(jsonPath("lastName", is("Jardine")))
        .andExpect(jsonPath("email", is("test@email.com")))
        .andExpect(jsonPath("password", is("test123")));

    Mockito.when(ownerService.deleteOwnerById(any(Long.class)))
        .thenThrow(new ResponseStatusException(HttpStatus.NO_CONTENT));

    MockHttpServletRequestBuilder mockDeleteRequest = delete("/api/v1/owners/{ownerId}", 1L);
    mockMvc.perform(mockDeleteRequest).andExpect(status().isNotFound());

  }
}
