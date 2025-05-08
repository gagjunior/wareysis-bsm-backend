package br.com.wareysis.bsm.core.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class AbstractException extends RuntimeException {

    private final Response.Status status;

    public AbstractException(String message, Response.Status status) {

        super(message);
        this.status = status;
    }

    public Status getStatus() {

        return status;
    }
}
