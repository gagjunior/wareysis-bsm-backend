package br.com.wareysis.bsm.core.exception;

import java.time.LocalDateTime;

import jakarta.ws.rs.core.Response;

public record ErrorResponse(

        Integer statusCode,
        Response.Status statusName,
        String message,
        LocalDateTime timestamp

) {

}
