package com.microbanco.account.exceptions;

import com.microbanco.account.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .reduce((a, b) -> a + "; " + b)
            .orElse("Validation failed");
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ErrorResponse(400, "Validation Error", message))
            .build();
    }
}
