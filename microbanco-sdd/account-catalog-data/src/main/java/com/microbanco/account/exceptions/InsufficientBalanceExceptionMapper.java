package com.microbanco.account.exceptions;

import com.microbanco.account.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientBalanceExceptionMapper implements ExceptionMapper<InsufficientBalanceException> {

    @Override
    public Response toResponse(InsufficientBalanceException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse(400, "Bad Request", exception.getMessage()))
            .build();
    }
}
