package com.cymap.ms_personalizacion.Assamblers;

import com.cymap.ms_personalizacion.controller.personalizacionController;
import com.cymap.ms_personalizacion.model.personalizacionModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PersonalizacionModelAssamblers implements RepresentationModelAssembler<personalizacionModel,EntityModel<personalizacionModel>> {
    @Override
    public EntityModel<personalizacionModel> toModel(personalizacionModel entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(personalizacionController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo((methodOn(personalizacionController.class).listar())).withRel("Usuarios")
        );

    }
}
