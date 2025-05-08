package br.com.wareysis.bsm.exceptions.usuarios;

import br.com.wareysis.bsm.core.exception.AbstractException;

import jakarta.ws.rs.core.Response;

public class UsuarioException extends AbstractException {

    public UsuarioException(String message, Response.Status status) {

        super(message, status);
    }
}
