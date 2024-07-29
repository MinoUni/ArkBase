package com.arkbase.operator;

import static com.arkbase.utils.TestUtils.buildNewOperatorDto;
import static com.arkbase.utils.TestUtils.buildOperatorDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arkbase.assembler.OperatorModelAssembler;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.utils.TestUtils;
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
    NewOperatorDTO opCreation = buildNewOperatorDto();
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
    NewOperatorDTO opCreation = buildNewOperatorDto();
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

  @Test
  void shouldFindOperatorById() throws Exception {
    final int id = 1;
    OperatorDTO operatorDto = TestUtils.buildOperatorDto();

    when(operatorService.findById(eq(id))).thenReturn(operatorDto);

    mvc.perform(get("/operators/" + id))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.id").exists(),
            jsonPath("$.codeName").exists(),
            jsonPath("$.attributes").exists(),
            jsonPath("$.skills").isArray());

    verify(operatorService).findById(eq(id));
  }

  @Test
  void shouldHandleOperatorNotFoundExceptionWhenFindOperatorById() throws Exception {
    final int id = 1;

    when(operatorService.findById(eq(id))).thenThrow(new OperatorNotFoundException(id));

    mvc.perform(get("/operators/" + id))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())),
            jsonPath("$.timestamp").exists(),
            jsonPath("$.message", is(String.format("Operator with id {%d} not found.", id))),
            jsonPath("$.subErrors").isArray(),
            jsonPath("$.subErrors").isEmpty());

    verify(operatorService).findById(eq(id));
  }

  @Test
  void shouldFindOperatorByCodeName() throws Exception {
    final String codeName = "Ray";
    OperatorDTO operatorDto = TestUtils.buildOperatorDto();

    when(operatorService.findByCodeName(eq(codeName))).thenReturn(operatorDto);

    mvc.perform(get("/operators").param("codeName", codeName))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.id", is(operatorDto.getId())),
            jsonPath("$.codeName", is(codeName)),
            jsonPath("$.attributes").exists(),
            jsonPath("$.skills").isArray());

    verify(operatorService).findByCodeName(eq(codeName));
  }

  @Test
  void shouldHandleOperatorNotFoundExceptionWhenFindOperatorByCodeName() throws Exception {
    final String codeName = "Ray";

    when(operatorService.findByCodeName(eq(codeName)))
        .thenThrow(new OperatorNotFoundException(codeName));

    mvc.perform(get("/operators").param("codeName", codeName))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.status", is(HttpStatus.NOT_FOUND.name())),
            jsonPath("$.timestamp").exists(),
            jsonPath(
                "$.message", is(String.format("Operator with codeName {%s} not found.", codeName))),
            jsonPath("$.subErrors").isArray(),
            jsonPath("$.subErrors").isEmpty());

    verify(operatorService).findByCodeName(eq(codeName));
  }
}
