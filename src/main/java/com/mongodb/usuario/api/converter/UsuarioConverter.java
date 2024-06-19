package com.mongodb.usuario.api.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.mongodb.usuario.api.request.EnderecoRequestDTO;
import com.mongodb.usuario.api.request.UsuarioRequestDTO;
import com.mongodb.usuario.infrastructure.entity.EnderecoEntity;
import com.mongodb.usuario.infrastructure.entity.UsuarioEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioConverter {



    public UsuarioEntity toUsuarioEntity(UsuarioRequestDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .id(UUID.randomUUID().toString())
                .nome(usuarioDTO.getNome())
                .documento(usuarioDTO.getDocumento())
                .email(usuarioDTO.getEmail())
                .dataAtualizacao(LocalDateTime.now())
                .dataCadastro(LocalDateTime.now())
                .build();

    }


    public EnderecoEntity toEnderecoEntity(EnderecoRequestDTO enderecoDTO, String usuarioId) {
        return EnderecoEntity.builder()
                .rua(enderecoDTO.getRua())
                .bairro(enderecoDTO.getBairro())
                .usuarioId(usuarioId)
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .build();
    }

}
