package com.microbanco.account.exceptions;

import com.microbanco.account.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {

    @Override
    public Response toResponse(AccountNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorResponse(404, "Not Found", exception.getMessage()))
            .build();
    }
}
