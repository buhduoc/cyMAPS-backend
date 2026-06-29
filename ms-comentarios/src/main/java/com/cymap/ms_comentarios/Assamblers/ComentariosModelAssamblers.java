package com.cymap.ms_comentarios.Assamblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.cymap.ms_comentarios.controller.comentariosController;
import com.cymap.ms_comentarios.model.comentariosModel;

@Component
public class ComentariosModelAssamblers implements RepresentationModelAssembler<comentariosModel, EntityModel<comentariosModel>> {

    @Override
    public EntityModel<comentariosModel> toModel(comentariosModel comentario) {

        return EntityModel.of(comentario,
                linkTo(methodOn(comentariosController.class)
                        .buscarPorId(comentario.getId()))
                        .withSelfRel(),

                linkTo(methodOn(comentariosController.class)
                        .listar())
                        .withRel("comentarios"),

                linkTo(methodOn(comentariosController.class)
                        .buscarPorRutaId(comentario.getRutaId()))
                        .withRel("comentarios-ruta"));
    }

    @Override
    public CollectionModel<EntityModel<comentariosModel>> toCollectionModel(
            Iterable<? extends comentariosModel> entities) {

        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(comentariosController.class)
                        .listar())
                        .withSelfRel());
    }
}