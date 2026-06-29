package com.cymap.ms_geolocalizacion.Assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cymap.ms_geolocalizacion.controller.geolocalizacionController;
import com.cymap.ms_geolocalizacion.model.geolocalizacionModel;

@Component
public class GeolocalizacionModelAssamblers implements RepresentationModelAssembler<geolocalizacionModel, EntityModel<geolocalizacionModel>> {

    @Override
    public EntityModel<geolocalizacionModel> toModel(geolocalizacionModel geo) {

        return EntityModel.of(geo,
                linkTo(methodOn(geolocalizacionController.class)
                        .buscarPorId(geo.getId()))
                        .withSelfRel(),

                linkTo(methodOn(geolocalizacionController.class)
                        .listar())
                        .withRel("geolocalizacion"),

                linkTo(methodOn(geolocalizacionController.class)
                        .buscarUltimaUbicacion(geo.getUsuarioId()))
                        .withRel("geolocalizacion-usuario"));
    }

    @Override
    public CollectionModel<EntityModel<geolocalizacionModel>> toCollectionModel(
            Iterable<? extends geolocalizacionModel> entities) {

        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(geolocalizacionController.class).listar()).withSelfRel());
    }
}