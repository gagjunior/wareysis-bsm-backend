package br.com.wareysis.bsm.core.exception;

import java.time.LocalDateTime;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class AbstractExceptionHandler<T extends AbstractException> implements ExceptionMapper<T> {

    @Override
    public Response toResponse(T exception) {

        return Response.status(exception.getStatus())
                .entity(new ErrorResponse(
                        exception.getStatus().getStatusCode(),
                        exception.getStatus(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
