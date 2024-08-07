package com.arkbase.operator;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.enums.Rarity;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OperatorIntTest {

  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper jsonMapper;

  @Test
  @Order(1)
  void shouldAddOperator() throws Exception {
    String newOperator = TestUtils.readWholeFile("add-operator-request.json");
    mvc.perform(post("/operators").contentType(MediaType.APPLICATION_JSON).content(newOperator))
        .andExpectAll(
            status().isCreated(),
            header().exists(HttpHeaders.LOCATION),
            header().string(HttpHeaders.LOCATION, Matchers.containsString("/operators/1")),
            jsonPath("$.id", is(1)),
            jsonPath("$.codeName", is("Ray")),
            jsonPath("$.archetype", is(Archetype.SNIPER.getArchetype())),
            jsonPath("$.subclass", is(Subclass.HUNTER.getSubclass())),
            jsonPath("$.rarity", is(Rarity.SIX_STAR.getRarity())),
            jsonPath("$.trait", is(Trait.HUNTER.getTrait())),
            jsonPath("$.skills").isArray(),
            jsonPath("$.skills", hasSize(2)),
            jsonPath("$._links").exists());
  }

  @Test
  @Order(2)
  void shouldUpdateOperatorDetails() throws Exception {
    int id = 1;
    String req = TestUtils.readWholeFile("update-operator-details-request.json");
    String resp = TestUtils.readWholeFile("update-operator-details-response.json");

    mvc.perform(
            patch(String.format("/operators/%d", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
        .andExpectAll(
            status().isOk(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(resp));
  }
}
