package com.cymap.ms_imagenes.Assamblers;
import com.cymap.ms_imagenes.controller.imagenesController;
import com.cymap.ms_imagenes.model.imagenesModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class ImagenesModelAssamblers implements RepresentationModelAssembler<imagenesModel,EntityModel<imagenesModel>> {
    @Override
    public EntityModel<imagenesModel> toModel(imagenesModel entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(imagenesController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo((methodOn(imagenesController.class).listar())).withRel("Usuarios")
        );

    }
}


