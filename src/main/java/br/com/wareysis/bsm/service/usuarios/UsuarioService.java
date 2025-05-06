package br.com.wareysis.bsm.service.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.bsm.core.config.messages.MessageService;
import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioUpdateDto;
import br.com.wareysis.bsm.entity.usuario.Usuario;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfil;
import br.com.wareysis.bsm.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.bsm.enumerations.TiposPerfilUsuarioEnum;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    Logger log;

    @Inject
    MessageService messageService;

    @Inject
    UsuarioFirebaseService usuarioFirebaseService;

    @Transactional
    public Usuario create(UsuarioCreateDto dto) {

        UserRecord userRecord = usuarioFirebaseService.createUserInFirebase(dto);

        log.info(String.format("BsmApp -> APPLICATION: Salvar usuário -> email: %s, nome: %s", dto.email(), dto.nomeCompleto()));

        Usuario usuario = persistNovoUsuario(dto, userRecord);
        persistNovosPerfis(usuario, dto.perfis());

        return usuario;

    }

    @Transactional
    public Usuario update(UsuarioUpdateDto dto) {

        usuarioFirebaseService.updateUserInFirebase(dto);

        log.info(String.format("BsmApp -> APPLICATION: Alterar usuário com uid %s", dto.id()));

        Usuario usuario = (Usuario) Usuario.findByIdOptional(dto.id()).orElseThrow();

        updateUsuario(dto, usuario);
        updatePerfis(usuario, dto.perfis());

        Usuario.flush();

        return usuario;

    }

    public List<Usuario> findAll() {

        return Usuario.listAll();
    }

    public Usuario findById(UUID id) {

        return (Usuario) Usuario
                .findByIdOptional(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + id));
    }

    public Usuario findByEmail(String email) {

        return (Usuario) Usuario
                .find("email = :email", Map.of("email", email))
                .singleResultOptional()
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com email: " + email));
    }

    private void updateUsuario(UsuarioUpdateDto dto, Usuario usuario) {

        if (!StringUtils.isBlank(dto.email())) {
            usuario.setEmail(dto.email());
        }

        if (!StringUtils.isBlank(dto.nomeCompleto())) {
            usuario.setNomeCompleto(dto.nomeCompleto());
        }

        if (!StringUtils.isBlank(dto.cpf())) {
            usuario.setCpf(dto.cpf());
        }

        if (!StringUtils.isBlank(dto.telefone())) {
            usuario.setTelefone(dto.telefone());
        }

        if (dto.habilitado() != null) {
            usuario.setHabilitado(dto.habilitado());
        }

        if (dto.alterarSenha() != null) {
            usuario.setAlterarSenha(dto.alterarSenha());
        }
    }

    private void updatePerfis(Usuario usuario, List<TiposPerfilUsuarioEnum> perfis) {

        if (perfis == null || perfis.isEmpty()) {
            return;
        }

        perfis.forEach(perfil -> {

            UsuarioPerfilId perfilId = new UsuarioPerfilId(usuario.getId(), perfil.name());

            UsuarioPerfil usuarioPerfil = (UsuarioPerfil) UsuarioPerfil.findByIdOptional(perfilId).orElse(null);

            if (usuarioPerfil == null) {

                usuarioPerfil = new UsuarioPerfil();
                usuarioPerfil.setId(perfilId);

                usuarioPerfil.persist();
                usuario.setDhAlteracao(LocalDateTime.now());
            }

        });

        UsuarioPerfil.flush();
    }

    private Usuario persistNovoUsuario(UsuarioCreateDto dto, UserRecord userRecord) {

        Usuario usuario = new Usuario();

        usuario.setId(UUID.fromString(userRecord.getUid()));
        usuario.setEmail(userRecord.getEmail());
        usuario.setNomeCompleto(userRecord.getDisplayName());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());
        usuario.setEmailVerificado(false);
        usuario.setHabilitado(true);
        usuario.setAlterarSenha(false);

        usuario.persist();

        return usuario;

    }

    private void persistNovosPerfis(Usuario usuario, List<TiposPerfilUsuarioEnum> perfis) {

        List<UsuarioPerfil> novosPerfis = new ArrayList<>();

        for (TiposPerfilUsuarioEnum tipoPerfil : perfis) {

            UsuarioPerfilId perfilId = new UsuarioPerfilId(usuario.getId(), tipoPerfil.name());

            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
            usuarioPerfil.setId(perfilId);

            usuarioPerfil.persist();

            novosPerfis.add(usuarioPerfil);
        }
    }

}
