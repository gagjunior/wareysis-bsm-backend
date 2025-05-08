package br.com.wareysis.bsm.service.usuarios;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import br.com.wareysis.bsm.core.service.MessageService;
import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioUpdateDto;
import br.com.wareysis.bsm.exceptions.usuarios.UsuarioException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class UsuarioFirebaseService {

    @Inject
    Logger log;

    @Inject
    MessageService messageService;

    public UserRecord createUserInFirebase(UsuarioCreateDto dto) {

        log.info(String.format("BsmApp -> FIREBASE: Salvar usuário -> email: %s, nome: %s", dto.email(), dto.nomeCompleto()));

        if (usuarioAlreadyExistsInFirbase(dto.email())) {
            throw new UsuarioException(messageService.getMessage("usuario.create.alread.exists", dto.email(), dto.nomeCompleto()), Status.CONFLICT);
        }

        try {

            return FirebaseAuth.getInstance().createUser(createRequestFirebase(dto));

        } catch (Exception e) {

            throw new UsuarioException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }

    }

    public void updateUserInFirebase(UsuarioUpdateDto dto) {

        log.info(String.format("BsmApp -> FIREBASE: Alterar usuário com uid %s", dto.id()));

        try {

            UserRecord userRecord = FirebaseAuth.getInstance().getUser(dto.id().toString());

            if (userRecord == null) {
                throw new UsuarioException(messageService.getMessage("usuario.uid.not.found", dto.id()), Status.BAD_REQUEST);
            }

            FirebaseAuth.getInstance().updateUser(updateRequestFirebase(dto));

        } catch (FirebaseAuthException e) {

            throw new UsuarioException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }

    }

    public void deleteUserInFirebase(String uid) {

        try {

            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

            if (userRecord == null) {
                throw new UsuarioException(messageService.getMessage("usuario.uid.not.found", uid), Status.BAD_REQUEST);
            }

            FirebaseAuth.getInstance().deleteUser(uid);
            log.warn(String.format("Usuário com id: %s deletado do Firebase", uid));

        } catch (FirebaseAuthException e) {

            throw new UsuarioException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }

    }

    private UserRecord.UpdateRequest updateRequestFirebase(UsuarioUpdateDto dto) {

        UserRecord.UpdateRequest request = new UpdateRequest(dto.id().toString());

        if (!StringUtils.isBlank(dto.email())) {
            request.setEmail(dto.email());
        }

        if (!StringUtils.isBlank(dto.nomeCompleto())) {
            request.setDisplayName(dto.nomeCompleto());
        }

        if (!StringUtils.isBlank(dto.telefone())) {
            request.setPhoneNumber(dto.telefone());
        }

        if (dto.habilitado() != null) {
            request.setDisabled(dto.habilitado());
        }

        return request;

    }

    private UserRecord.CreateRequest createRequestFirebase(UsuarioCreateDto dto) {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest();

        request.setUid(UUID.randomUUID().toString());
        request.setEmail(dto.email());
        request.setPassword(dto.senha());
        request.setDisplayName(dto.nomeCompleto());

        if (!StringUtils.isBlank(dto.telefone())) {
            request.setPhoneNumber(dto.telefone());
        }

        request.setDisabled(false);
        request.setEmailVerified(false);

        return request;
    }

    private boolean usuarioAlreadyExistsInFirbase(String email) {

        try {

            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return (userRecord != null && userRecord.getEmail().equals(email));

        } catch (FirebaseAuthException e) {

            if (e.getAuthErrorCode().equals(AuthErrorCode.EMAIL_NOT_FOUND) || e.getAuthErrorCode().equals(AuthErrorCode.USER_NOT_FOUND)) {

                return false;

            } else {
                throw new UsuarioException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
            }

        }

    }

}
