package br.com.wareysis.bsm.service.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.mapper.tipos.TipoPerfilUsuarioMapper;
import br.com.wareysis.bsm.repository.usuarios.TipoPerfilUsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TipoPerfilUsuarioService {

    @Inject
    TipoPerfilUsuarioRepository repository;

    @Inject
    UsuarioPerfilService usuarioPerfilService;

    @Inject
    TipoPerfilUsuarioMapper tipoPerfilUsuarioMapper;

    public List<TipoPerfilUsuarioDto> findTipoPerfisDto(UUID idUsuario) {

        List<TipoPerfilUsuarioDto> tiposPerfilList = new ArrayList<>();
        List<UsuarioPerfil> perfisUsuario = usuarioPerfilService.findPerfis(idUsuario);

        for (UsuarioPerfil perfil : perfisUsuario) {
            TipoPerfilUsuario perfilUsuario = repository.findById(perfil.getId().idPerfil());
            tiposPerfilList.add(tipoPerfilUsuarioMapper.toDto(perfilUsuario));
        }

        return tiposPerfilList;

    }

}
