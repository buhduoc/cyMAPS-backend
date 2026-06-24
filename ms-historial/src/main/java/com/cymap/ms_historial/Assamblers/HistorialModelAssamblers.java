package com.cymap.ms_historial.Assamblers;
import com.cymap.ms_historial.controller.historialController;
import com.cymap.ms_historial.model.historialModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class HistorialModelAssamblers implements RepresentationModelAssembler <historialModel,EntityModel<historialModel>> {
    @Override
    public EntityModel<historialModel> toModel(historialModel entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(historialController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo((methodOn(historialController.class).listar())).withRel("Usuarios")
        );

    }
}

