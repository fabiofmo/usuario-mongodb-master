package com.mongodb.usuario.api.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mongodb.usuario.api.response.UsuarioResponseDTO;
import com.mongodb.usuario.infrastructure.entity.EnderecoEntity;
import com.mongodb.usuario.infrastructure.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", source = "usuario.id")
    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "documento", source = "usuario.documento")
    @Mapping(target = "endereco", source = "enderecoEntity")
    UsuarioResponseDTO toUsuarioResponseDTO(UsuarioEntity usuario, EnderecoEntity enderecoEntity);

}
