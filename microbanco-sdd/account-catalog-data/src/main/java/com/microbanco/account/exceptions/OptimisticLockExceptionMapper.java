package com.microbanco.account.exceptions;

import com.microbanco.account.dto.ErrorResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OptimisticLockExceptionMapper implements ExceptionMapper<OptimisticLockException> {

    @Override
    public Response toResponse(OptimisticLockException exception) {
        return Response.status(Response.Status.CONFLICT)
            .entity(new ErrorResponse(409, "Conflict", "Concurrent modification detected. Please retry the operation."))
            .build();
    }
}
