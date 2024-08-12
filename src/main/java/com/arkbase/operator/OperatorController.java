package com.arkbase.operator;

import com.arkbase.assembler.OperatorModelAssembler;
import com.arkbase.skill.NewSkillDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        assembler.toModel(operatorService.createOperator(newOperator));
    return ResponseEntity.created(operatorModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(operatorModel);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PagedModel<OperatorDTO> findAll(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    return new PagedModel<>(operatorService.findAllOperators(page, size));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EntityModel<OperatorDTO> findOperatorById(@PathVariable int id) {
    return assembler.toModel(operatorService.findOperatorById(id));
  }

  @PostMapping(
      value = "/{id}/skills",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EntityModel<OperatorDTO>> addSkillToOperator(
      @PathVariable("id") int operatorId, @Valid @RequestBody NewSkillDTO skillDto) {
    EntityModel<OperatorDTO> operatorModel =
        assembler.toModel(operatorService.addSkillToOperator(operatorId, skillDto));
    return ResponseEntity.created(operatorModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(operatorModel);
  }

  @DeleteMapping(value = "/{id}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EntityModel<OperatorDTO>> removeSkillFromOperator(
      @PathVariable("id") int operatorId, @RequestParam("skillId") int skillId) {
    EntityModel<OperatorDTO> operatorModel =
        assembler.toModel(operatorService.removeSkillFromOperator(operatorId, skillId));
    return ResponseEntity.ok(operatorModel);
  }

  @PatchMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OperatorDetailsDTO> updateOperatorDetails(
      @PathVariable("id") int operatorId,
      @Valid @RequestBody OperatorDetailsUpdate operatorUpdate) {
    return ResponseEntity.ok(operatorService.updateOperatorDetails(operatorId, operatorUpdate));
  }
}
