package br.com.wareysis.bsm.service.usuarios;

import java.util.List;
import java.util.UUID;

import br.com.wareysis.bsm.dto.usuarios.UsuarioPerfilDeleteDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioPerfilDto;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.bsm.repository.usuarios.UsuarioPerfilRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioPerfilService {

    @Inject
    UsuarioPerfilRepository repository;

    public List<UsuarioPerfilDto> findDtoListByPerfilId(String idPerfil) {

        return repository.findDtoListByPerfilId(idPerfil);
    }

    public List<UsuarioPerfil> findBbyIdUsuario(UUID idUsuario) {

        return repository.find("id.idUsuario", idUsuario).list();
    }

    public List<UsuarioPerfil> findPerfis(UUID idUsuario) {

        return repository.find("id.idUsuario", idUsuario).list();
    }

    @Transactional
    public void deleteByIdUsuario(UsuarioPerfilDeleteDto dto) {

        dto.perfis().forEach(perfil -> repository.deleteById(new UsuarioPerfilId(dto.idUsuario(), perfil)));

        repository.flush();

    }

}
