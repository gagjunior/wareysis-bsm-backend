package br.com.wareysis.bsm.service.usuarios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.entity.usuario.Usuario;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.bsm.enumerations.TiposPerfilUsuarioEnum;
import br.com.wareysis.bsm.repository.usuarios.UsuarioPerfilRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioPerfilService {

    @Inject
    UsuarioPerfilRepository repository;

    public void updatePerfis(Usuario usuario, List<TiposPerfilUsuarioEnum> perfis) {

        if (perfis == null || perfis.isEmpty()) {
            return;
        }

        perfis.forEach(perfil -> {

            UsuarioPerfilId perfilId = new UsuarioPerfilId(usuario.getId(), perfil.name());

            UsuarioPerfil usuarioPerfil = repository.findByIdOptional(perfilId).orElse(null);

            if (usuarioPerfil == null) {

                usuarioPerfil = new UsuarioPerfil();
                usuarioPerfil.setId(perfilId);

                usuarioPerfil.persist();
                usuario.setDhAlteracao(LocalDateTime.now());
            }

        });

        repository.flush();
    }

    public void persistNovosPerfis(Usuario usuario, List<TiposPerfilUsuarioEnum> perfis) {

        for (TiposPerfilUsuarioEnum tipoPerfil : perfis) {

            UsuarioPerfilId perfilId = new UsuarioPerfilId(usuario.getId(), tipoPerfil.name());

            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
            usuarioPerfil.setId(perfilId);

            usuarioPerfil.persist();

        }
    }

    public List<UsuarioPerfil> findPerfis(UUID idUsuario) {

        return repository.find("id.idUsuario", idUsuario).list();
    }

}
