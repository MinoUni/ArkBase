package com.arkbase.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.arkbase.operator.OperatorController;
import com.arkbase.operator.OperatorDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class OperatorModelAssembler
    implements RepresentationModelAssembler<OperatorDTO, EntityModel<OperatorDTO>> {

  /** {@inheritDoc} */
  @Override
  public @NonNull EntityModel<OperatorDTO> toModel(@NonNull OperatorDTO operator) {
    return EntityModel.of(
        operator,
        linkTo(methodOn(OperatorController.class).findOperatorById(operator.id())).withSelfRel());
  }
}
