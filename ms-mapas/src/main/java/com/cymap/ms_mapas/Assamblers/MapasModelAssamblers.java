package com.cymap.ms_mapas.Assamblers;
import com.cymap.ms_mapas.controller.mapasController;
import com.cymap.ms_mapas.model.mapasModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class MapasModelAssamblers implements RepresentationModelAssembler<mapasModel,EntityModel<mapasModel>> {
    @Override
    public EntityModel<mapasModel> toModel(mapasModel entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(mapasController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo((methodOn(mapasController.class).listar())).withRel("Usuarios")
        );

    }
}
