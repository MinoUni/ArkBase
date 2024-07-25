package com.arkbase.operator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.utils.OperatorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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
  void shouldAddOperator() throws Exception {
    OperatorCreationDTO newOperator = OperatorUtils.buildOperatorCreationDto();
    mvc.perform(
            post("/operators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newOperator)))
        .andExpectAll(
            status().isCreated(),
            header().exists(HttpHeaders.LOCATION),
            header().string(HttpHeaders.LOCATION, Matchers.containsString("/operators/1")),
            jsonPath("$.id", is(1)),
            jsonPath("$.codeName", is("Ray")),
            jsonPath("$.archetype", is("Sniper")),
            jsonPath("$.trait", containsString("Attacks require ammo")),
            jsonPath("$._links").exists());
  }
}
