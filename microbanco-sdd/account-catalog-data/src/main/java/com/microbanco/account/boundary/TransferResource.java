package com.microbanco.account.boundary;

import com.microbanco.account.dto.TransferRequest;
import com.microbanco.account.dto.TransferResponse;
import com.microbanco.account.entities.Transfer;
import com.microbanco.account.services.TransferService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/transfers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource {

    @Inject
    TransferService transferService;

    @POST
    public Response executeTransfer(@Valid TransferRequest request) {
        Transfer transfer = transferService.executeTransfer(
            request.getSourceAccountIban(),
            request.getTargetAccountIban(),
            request.getAmount(),
            request.getDescription()
        );
        return Response.created(URI.create("/transfers/" + transfer.id))
            .entity(TransferResponse.fromEntity(transfer))
            .build();
    }
}
