package br.com.wareysis.bsm.service.tipos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import br.com.wareysis.bsm.core.service.MessageService;
import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.entity.tipos.TipoPerfilUsuario;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.exceptions.tipos.TipoPerfilUsuarioException;
import br.com.wareysis.bsm.mapper.tipos.TipoPerfilUsuarioMapper;
import br.com.wareysis.bsm.repository.tipos.TipoPerfilUsuarioRepository;
import br.com.wareysis.bsm.service.usuarios.UsuarioPerfilService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class TipoPerfilUsuarioService {

    @Inject
    TipoPerfilUsuarioRepository repository;

    @Inject
    UsuarioPerfilService usuarioPerfilService;

    @Inject
    TipoPerfilUsuarioMapper tipoPerfilUsuarioMapper;

    @Inject
    MessageService messageService;

    public List<TipoPerfilUsuario> findAll() {

        return repository.listAll();
    }

    public TipoPerfilUsuario findById(String id) {

        if (StringUtils.isBlank(id)) {
            throw new TipoPerfilUsuarioException(messageService.getMessage("tipo.perfil.usuario.id.null"), Status.BAD_REQUEST);
        }

        return repository.findByIdOptional(id)
                .orElseThrow(() -> new TipoPerfilUsuarioException(messageService.getMessage("tipo.perfil.usuario.not.found", id), Status.BAD_REQUEST));
    }

    public List<TipoPerfilUsuario> findByNome(String nome) {

        if (StringUtils.isBlank(nome)) {
            throw new TipoPerfilUsuarioException(messageService.getMessage("tipo.perfil.usuario.nome.null"), Status.BAD_REQUEST);
        }

        return repository.findByNome(nome);
    }

    public List<TipoPerfilUsuarioDto> findTipoPerfisDto(UUID idUsuario) {

        List<TipoPerfilUsuarioDto> tiposPerfilList = new ArrayList<>();
        List<UsuarioPerfil> perfisUsuario = usuarioPerfilService.findPerfis(idUsuario);

        for (UsuarioPerfil perfil : perfisUsuario) {
            TipoPerfilUsuario perfilUsuario = repository.findById(perfil.getId().idPerfil().name());
            tiposPerfilList.add(tipoPerfilUsuarioMapper.toDto(perfilUsuario));
        }

        return tiposPerfilList;

    }

}
