package com.microbanco.account.exceptions;

import com.microbanco.account.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException exception) {
        return Response.status(Response.Status.CONFLICT)
            .entity(new ErrorResponse(409, "Conflict", exception.getMessage()))
            .build();
    }
}
