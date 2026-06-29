package com.cymap.ms_disponibilidad.Assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cymap.ms_disponibilidad.controller.disponibilidadController;
import com.cymap.ms_disponibilidad.model.disponibilidadModel;

@Component
public class DisponibilidadModelAssamblers implements RepresentationModelAssembler<disponibilidadModel, EntityModel<disponibilidadModel>> {

    @Override
    public EntityModel<disponibilidadModel> toModel(disponibilidadModel disponibilidad) {

        return EntityModel.of(disponibilidad,
                linkTo(methodOn(disponibilidadController.class)
                        .buscarPorId(disponibilidad.getId()))
                        .withSelfRel(),

                linkTo(methodOn(disponibilidadController.class)
                        .listar())
                        .withRel("disponibilidad"),

                linkTo(methodOn(disponibilidadController.class)
                        .buscarPorRutaId(disponibilidad.getRutaId()))
                        .withRel("disponibilidad-ruta"));
    }

    @Override
    public CollectionModel<EntityModel<disponibilidadModel>> toCollectionModel(
            Iterable<? extends disponibilidadModel> entities) {

        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(disponibilidadController.class).listar()).withSelfRel());
    }
}