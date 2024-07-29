package com.arkbase.operator;

import com.arkbase.assembler.OperatorModelAssembler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operators")
@RequiredArgsConstructor
public class OperatorController {

  private final OperatorService operatorService;
  private final OperatorModelAssembler assembler;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EntityModel<OperatorDTO>> addOperator(
      @Valid @RequestBody NewOperatorDTO newOperator) {
    EntityModel<OperatorDTO> operatorModel =
        assembler.toModel(operatorService.addOperator(newOperator));
    return ResponseEntity.created(operatorModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(operatorModel);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EntityModel<OperatorDTO> findOperatorById(@PathVariable Integer id) {
    return assembler.toModel(operatorService.findById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public EntityModel<OperatorDTO> findOperatorByCodeName(
      @RequestParam("codeName") String codeName) {
    return assembler.toModel(operatorService.findByCodeName(codeName));
  }
}
