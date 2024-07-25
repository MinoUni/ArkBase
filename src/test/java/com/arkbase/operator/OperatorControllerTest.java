package com.arkbase.operator;

import static com.arkbase.utils.OperatorUtils.buildOperatorCreationDto;
import static com.arkbase.utils.OperatorUtils.buildOperatorDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.assembler.OperatorModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(OperatorModelAssembler.class)
@WebMvcTest(OperatorController.class)
class OperatorControllerTest {

  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private OperatorService operatorService;

  @Test
  void shouldAddOperator() throws Exception {
    OperatorCreationDTO opCreation = buildOperatorCreationDto();
    OperatorDTO operator = buildOperatorDto();
    when(operatorService.addOperator(eq(opCreation))).thenReturn(operator);

    mvc.perform(
            post("/operators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opCreation)))
        .andExpectAll(
            status().isCreated(),
            header().exists(HttpHeaders.LOCATION),
            header()
                .string(
                    HttpHeaders.LOCATION,
                    Matchers.containsString("/operators/" + operator.getId())));

    verify(operatorService).addOperator(eq(opCreation));
  }

  @Test
  void shouldFailValidationWhenAddingOperator() throws Exception {
    OperatorCreationDTO opCreation = buildOperatorCreationDto();
    opCreation.setCodeName(null);
    opCreation.setRarity("1 star");
    mvc.perform(
            post("/operators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(opCreation)))
        .andExpectAll(
            status().isBadRequest(),
            header().doesNotExist(HttpHeaders.LOCATION),
            jsonPath("$.status", is(HttpStatus.BAD_REQUEST.name())),
            jsonPath("$.timestamp").exists(),
            jsonPath("$.message", is("Validation failed")),
            jsonPath("$.subErrors").exists(),
            jsonPath("$.subErrors.[*].field", containsInAnyOrder("rarity", "codeName")));

    verify(operatorService, never()).addOperator(eq(opCreation));
  }
}
