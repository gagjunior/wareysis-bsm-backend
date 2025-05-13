package br.com.wareysis.bsm.mapper.usuarios;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioResponseDto;
import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;
import br.com.wareysis.bsm.entity.usuario.Usuario;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {

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
    @Mapping(source = "perfis", target = "perfis", qualifiedByName = "mapUsuarioPerfisToDto")
    UsuarioResponseDto usuarioToUsuarioResponseDto(Usuario usuario);

    @Named("mapUsuarioPerfisToDto")
    default List<TipoPerfilUsuarioDto> mapUsuarioPerfisToDto(List<UsuarioPerfil> usuarioPerfis) {

        return usuarioPerfis.stream()
                .map(usuarioPerfil -> {
                    TipoPerfilUsuario perfil = usuarioPerfil.getPerfil();
                    return new TipoPerfilUsuarioDto(perfil.getId(), perfil.getNome());
                })
                .toList();
    }
}
