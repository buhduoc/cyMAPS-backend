package com.cymap.ms_rutas.Assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cymap.ms_rutas.controller.rutasController;
import com.cymap.ms_rutas.model.rutasModel;

@Component
public class RutasModelAssamblers implements RepresentationModelAssembler<rutasModel, EntityModel<rutasModel>> {

    @Override
    public EntityModel<rutasModel> toModel(rutasModel ruta) {

        return EntityModel.of(ruta,
                linkTo(methodOn(rutasController.class)
                        .obtenerPorId(ruta.getId()))
                        .withSelfRel(),

                linkTo(methodOn(rutasController.class)
                        .listar())
                        .withRel("rutas"));
    }

    @Override
    public CollectionModel<EntityModel<rutasModel>> toCollectionModel(
            Iterable<? extends rutasModel> entities) {

        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(rutasController.class).listar()).withSelfRel());
    }
}