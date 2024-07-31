package com.arkbase.operator;

import static com.arkbase.utils.TestUtils.buildNewOperatorDto;
import static com.arkbase.utils.TestUtils.buildOperatorDto;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.assembler.OperatorModelAssembler;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
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
                    HttpHeaders.LOCATION, Matchers.containsString("/operators/" + operator.id())));

    verify(operatorService).addOperator(eq(opCreation));
  }

  @Test
  void shouldFailValidationWhenAddingOperator() throws Exception {
    NewOperatorDTO newOperator =
        NewOperatorDTO.builder()
            .codeName(null)
            .archetype(Archetype.SNIPER)
            .subclass(Subclass.ARTILLERYMAN)
            .rarity(null)
            .trait(Trait.ARTILLERYMAN)
            .position(Position.RANGED)
            .attackType(AttackType.PHYSICAL_DAMAGE)
            .build();

    mvc.perform(
            post("/operators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOperator)))
        .andExpectAll(
            status().isBadRequest(),
            header().doesNotExist(HttpHeaders.LOCATION),
            jsonPath("$.status", is(HttpStatus.BAD_REQUEST.name())),
            jsonPath("$.timestamp").exists(),
            jsonPath("$.message", is("Validation failed")),
            jsonPath("$.subErrors").exists(),
            jsonPath("$.subErrors.[*].field", containsInAnyOrder("rarity", "codeName")));

    verify(operatorService, never()).addOperator(eq(newOperator));
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
  void shouldFindAllOperatorsPaged() throws Exception {
    final int page = 1;
    final int size = 3;
    List<OperatorDTO> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(TestUtils.buildOperatorDto());
    }

    when(operatorService.findAll(eq(page), eq(size))).thenReturn(new PageImpl<>(list));

    mvc.perform(
            get("/operators")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
        .andExpectAll(
            status().isOk(),
                jsonPath("$.content").isArray(),
                jsonPath("$.content", hasSize(size)));

    verify(operatorService).findAll(eq(page), eq(size));
  }
}
