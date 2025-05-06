package br.com.wareysis.bsm.service.usuarios;

import java.util.UUID;

import org.jboss.logging.Logger;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import br.com.wareysis.bsm.core.config.messages.MessageService;
import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioFirebaseService {

    @Inject
    Logger log;

    @Inject
    MessageService messageService;

    public UserRecord createUserInFirebase(UsuarioCreateDto dto) {

        log.info(String.format("BsmApp -> FIREBASE: Salvar usuário -> email: %s, nome: %s", dto.email(), dto.nomeCompleto()));

        if (clienteAlreadyExistsInFirbase(dto.email())) {
            throw new RuntimeException(messageService.getMessage("cliente.create.alread.exists", dto.email(), dto.nomeCompleto()));
        }

        try {

            return FirebaseAuth.getInstance().createUser(createRequestFirebase(dto));

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage(), e);
        }

    }

    private UserRecord.CreateRequest createRequestFirebase(UsuarioCreateDto dto) {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest();

        request.setUid(UUID.randomUUID().toString());
        request.setEmail(dto.email());
        request.setPassword(dto.senha());
        request.setDisplayName(dto.nomeCompleto());
        request.setDisabled(false);
        request.setEmailVerified(false);

        return request;
    }

    private boolean clienteAlreadyExistsInFirbase(String email) {

        try {

            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return (userRecord != null && userRecord.getEmail().equals(email));

        } catch (FirebaseAuthException e) {

            if (e.getAuthErrorCode().equals(AuthErrorCode.EMAIL_NOT_FOUND) || e.getAuthErrorCode().equals(AuthErrorCode.USER_NOT_FOUND)) {

                return false;

            } else {
                throw new RuntimeException(e.getMessage(), e);
            }

        }

    }

}
