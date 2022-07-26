package com.keplux.todo_app.owner;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
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
   *
   * @throws Exception
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
   * @throws Exception
   */
  @Test
  public void getOwnerById_success() throws Exception {
    Owner owner = owner2;

    Mockito.when(ownerService.getOwnerById(2L))
        .thenReturn(new ResponseEntity<>(owner, HttpStatus.OK));

    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/owners/2").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("firstName", is("Jane")))
        .andExpect(jsonPath("lastName", is("Doe"))).andExpect(jsonPath("email", is("jane@doe.com")))
        .andExpect(jsonPath("password", is("test456")));
  }

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

  @Test
  public void updateOwner_success() throws Exception {
    Mockito.when(ownerService.createOwner(owner1))
        .thenReturn(new ResponseEntity<>(owner1, HttpStatus.OK));

    Owner updatedOwner = Owner.builder().id(owner1.getId()).firstName("Steve").lastName("Jobs")
        .email("test@email.com").password("Apple!123").build();

    Mockito.when(ownerService.updateOwner(owner1.getId(), updatedOwner))
        .thenReturn(new ResponseEntity<>(updatedOwner, HttpStatus.OK));

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(
            "/api/v1/owners/{ownerId}", owner1.getId())
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(updatedOwner));

    mockMvc.perform(mockRequest).andExpect(status().isOk());
//        .andExpect(jsonPath("firstName", is("Steve")));
  }
}
