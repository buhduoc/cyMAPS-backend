package com.cymap.ms_comunidad.Assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cymap.ms_comunidad.controller.comunidadController;
import com.cymap.ms_comunidad.model.comunidadModel;

@Component
public class ComunidadModelAssamblers implements RepresentationModelAssembler<comunidadModel, EntityModel<comunidadModel>> {

    @Override
    public EntityModel<comunidadModel> toModel(comunidadModel comunidad) {

        return EntityModel.of(comunidad,
                linkTo(methodOn(comunidadController.class)
                        .buscarPorId(comunidad.getId()))
                        .withSelfRel(),

                linkTo(methodOn(comunidadController.class)
                        .listar())
                        .withRel("comunidad"),

                linkTo(methodOn(comunidadController.class)
                        .buscarPorCreadorId(comunidad.getUsuarioId()))
                        .withRel("comunidad-usuario"));
    }

    @Override
    public CollectionModel<EntityModel<comunidadModel>> toCollectionModel(
            Iterable<? extends comunidadModel> entities) {

        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(comunidadController.class).listar()).withSelfRel());
    }
}