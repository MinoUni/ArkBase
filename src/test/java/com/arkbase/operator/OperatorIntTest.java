package com.arkbase.operator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.utils.TestUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OperatorIntTest {

  @Autowired private MockMvc mockMvc;

  @Test
  @Order(1)
  void shouldCreateOperator() throws Exception {
    String req = TestUtils.readWholeFile("add-operator-request.json");
    String resp = TestUtils.readWholeFile("add-operator-response.json");

    mockMvc
        .perform(post("/operators").contentType(MediaType.APPLICATION_JSON).content(req))
        .andExpectAll(
            status().isCreated(),
            header().exists(HttpHeaders.LOCATION),
            header().string(HttpHeaders.LOCATION, Matchers.containsString("/operators/1")),
            content().json(resp));
  }

  @Test
  @Order(2)
  void shouldFindOperatorById() throws Exception {
    int operatorId = 1;
    String resp = TestUtils.readWholeFile("find-operator-by-id-response.json");

    mockMvc
        .perform(get(String.format("/operators/%d", operatorId)))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(resp));
  }

  @Test
  @Order(3)
  void shouldAddSkillToOperator() throws Exception {
    int operatorId = 1;
    String req = TestUtils.readWholeFile("add-skill-to-operator-request.json");
    String resp = TestUtils.readWholeFile("add-skill-to-operator-response.json");

    mockMvc
        .perform(
            post(String.format("/operators/%d/skills", operatorId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
        .andExpectAll(
            status().isCreated(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(resp));
  }

  @Test
  @Order(4)
  void shouldRemoveSkillFromOperator() throws Exception {
    int operatorId = 1, skillId = 2;
    String resp = TestUtils.readWholeFile("remove-skill-from-operator-response.json");

    mockMvc
        .perform(
            delete(String.format("/operators/%d/skills", operatorId))
                .param("skillId", String.valueOf(skillId)))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(resp));
  }

  @Test
  @Order(5)
  void shouldUpdateOperatorDetails() throws Exception {
    int id = 1;
    String req = TestUtils.readWholeFile("update-operator-details-request.json");
    String resp = TestUtils.readWholeFile("update-operator-details-response.json");

    mockMvc
        .perform(
            patch(String.format("/operators/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(resp));
  }
}
