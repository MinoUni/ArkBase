package com.arkbase.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operators")
@RequiredArgsConstructor
public class OperatorController {

  private final OperatorService operatorService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OperatorDTO> addOperator(@RequestBody OperatorCreationDTO operatorDto) {
    OperatorDTO operator = operatorService.addOperator(operatorDto);
    return ResponseEntity.ok(operator);
  }
}
