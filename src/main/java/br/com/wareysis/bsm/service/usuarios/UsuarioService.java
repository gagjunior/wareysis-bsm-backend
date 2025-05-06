package br.com.wareysis.bsm.service.usuarios;

import java.util.UUID;

import org.jboss.logging.Logger;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.bsm.core.config.messages.MessageService;
import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.entity.usuario.Usuario;

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

        log.info(String.format("BsmApp -> APP: Salvar usuário -> email: %s, nome: %s", dto.email(), dto.nomeCompleto()));
        Usuario usuario = new Usuario();

        usuario.id = UUID.fromString(userRecord.getUid());
        usuario.email = userRecord.getEmail();
        usuario.nomeCompleto = userRecord.getDisplayName();
        usuario.cpf = dto.cpf();
        usuario.telefone = dto.telefone();
        usuario.emailVerificado = false;
        usuario.habilitado = true;
        usuario.alterarSenha = false;

        usuario.persist();

        return usuario;

    }

}
