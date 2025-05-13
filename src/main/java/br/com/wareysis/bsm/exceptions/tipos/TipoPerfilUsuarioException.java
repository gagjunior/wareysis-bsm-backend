package br.com.wareysis.bsm.exceptions.tipos;

import br.com.wareysis.bsm.core.exception.AbstractException;

import jakarta.ws.rs.core.Response;

public class TipoPerfilUsuarioException extends AbstractException {

    public TipoPerfilUsuarioException(String message, Response.Status status) {

        super(message, status);
    }
}
