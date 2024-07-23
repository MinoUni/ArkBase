package com.arkbase.operator;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arkbase.enums.Rarity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

  private OperatorCreationDTO buildOperatorCreationDto() {
    return OperatorCreationDTO.builder()
        .codeName("Ray")
        .archetype(Operator.Archetype.SNIPER.getArchetype())
        .subclass(Operator.Subclass.HUNTER.getSubclass())
        .trait(Operator.Trait.HUNTER.name())
        .rarity(Rarity.SIX_STAR.name())
        .position(Operator.Position.RANGED.name())
        .attackType(Operator.AttackType.PHYSICAL_DAMAGE.name())
        .attributes(
            OperatorAttributesDTO.builder()
                .hp(2000)
                .atk(1130)
                .def(200)
                .res(10)
                .block(1)
                .aspd("Fast")
                .deploymentCost(20)
                .redeploymentTime("Long")
                .build())
        .build();
  }

  private OperatorDTO buildOperatorDto() {
    return OperatorDTO.builder()
        .id(1)
        .codeName("Ray")
        .archetype(Operator.Archetype.SNIPER)
        .subclass(Operator.Subclass.HUNTER)
        .trait(Operator.Trait.HUNTER)
        .rarity(Rarity.SIX_STAR)
        .position(Operator.Position.RANGED)
        .attackType(Operator.AttackType.PHYSICAL_DAMAGE)
        .attributes(
            OperatorAttributesDTO.builder()
                .hp(2000)
                .atk(1130)
                .def(200)
                .res(10)
                .block(1)
                .aspd("Fast")
                .deploymentCost(20)
                .redeploymentTime("Long")
                .build())
        .build();
  }
}
