package br.com.wareysis.bsm.mapper.usuarios;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.wareysis.bsm.dto.usuarios.UsuarioPerfilDto;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;

@Mapper(componentModel = "cdi")
public interface UsuarioPerfilMapper {

    UsuarioPerfilMapper INSTANCE = Mappers.getMapper(UsuarioPerfilMapper.class);

    UsuarioPerfil toEntity(UsuarioPerfilDto usuarioPerfilDto);

    @Mapping(source = "id.idUsuario", target = "idUsuario")
    @Mapping(source = "id.idPerfil", target = "idPerfil")
    UsuarioPerfilDto toDto(UsuarioPerfil entity);
}
