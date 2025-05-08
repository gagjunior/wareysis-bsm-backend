package br.com.wareysis.bsm.mapper.usuarios;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioResponseDto;
import br.com.wareysis.bsm.entity.usuario.Usuario;
import br.com.wareysis.bsm.mapper.tipos.TipoPerfilUsuarioMapper;

@Mapper(componentModel = "cdi", uses = TipoPerfilUsuarioMapper.class)
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "perfis", source = "perfis")
    UsuarioResponseDto toDto(Usuario usuario, List<TipoPerfilUsuarioDto> perfis);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nomeCompleto", target = "nomeCompleto")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "telefone", target = "telefone")
    @Mapping(source = "emailVerificado", target = "emailVerificado")
    @Mapping(source = "habilitado", target = "habilitado")
    @Mapping(source = "alterarSenha", target = "alterarSenha")
    @Mapping(source = "dhCriacao", target = "dhCriacao")
    @Mapping(source = "dhAlteracao", target = "dhAlteracao")
    Usuario toEntity(UsuarioResponseDto usuarioResponseDto);
}
