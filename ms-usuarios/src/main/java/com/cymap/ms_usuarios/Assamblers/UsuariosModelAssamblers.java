package com.cymap.ms_usuarios.Assamblers;

import com.cymap.ms_usuarios.controller.usuariosController;
import com.cymap.ms_usuarios.model.usuariosModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuariosModelAssamblers implements  RepresentationModelAssembler<usuariosModel,EntityModel<usuariosModel>> {
    @Override
    public EntityModel<usuariosModel> toModel(usuariosModel entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(usuariosController.class).obtenerPorId(entity.getId())).withSelfRel(),
                linkTo((methodOn(usuariosController.class).listar())).withRel("Usuarios")
        );

    }
}
