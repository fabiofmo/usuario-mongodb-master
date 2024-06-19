package com.mongodb.usuario.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.usuario.api.converter.UsuarioConverter;
import com.mongodb.usuario.api.converter.UsuarioMapper;
import com.mongodb.usuario.api.request.UsuarioRequestDTO;
import com.mongodb.usuario.api.response.UsuarioResponseDTO;
import com.mongodb.usuario.infrastructure.entity.EnderecoEntity;
import com.mongodb.usuario.infrastructure.entity.UsuarioEntity;
import com.mongodb.usuario.infrastructure.exceptions.BusinessException;
import com.mongodb.usuario.infrastructure.repository.UsuarioRepository;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoService enderecoService;


    public UsuarioEntity saveUsuario(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }

    public UsuarioResponseDTO saveUsuarios(UsuarioRequestDTO usuarioRequestDTO) {
    	
        try {
            notNull(usuarioRequestDTO, "Os dados do usuário são obrigatórios");
            UsuarioEntity usuarioEntity = saveUsuario(usuarioConverter.toUsuarioEntity(usuarioRequestDTO));
            
            EnderecoEntity enderecoEntity = enderecoService.saveEndereco(
                    usuarioConverter.toEnderecoEntity(usuarioRequestDTO.getEndereco(), usuarioEntity.getId()));
            
            return usuarioMapper.toUsuarioResponseDTO(usuarioEntity, enderecoEntity);
            
        } catch (Exception e) {
            throw new BusinessException("Erro ao gravar dados de usuário", e);
        }
    }


    public UsuarioResponseDTO findUsuarioByEmail(String email) {
    	
        try {
            UsuarioEntity entity = usuarioRepository.findByEmail(email);
            notNull(entity, "Usuário não encontrado");
            EnderecoEntity enderecoEntity = enderecoService.findByUsuarioId(entity.getId());

            return usuarioMapper.toUsuarioResponseDTO(entity, enderecoEntity);
            
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar dados de usuário", e);
        }
    }

    @Transactional
    public void deleteUsuario(String email) {
    	
        UsuarioEntity entity = usuarioRepository.findByEmail(email);
        usuarioRepository.deleteByEmail(email);
        enderecoService.deleteByUsuarioId(entity.getId());
    }


}
