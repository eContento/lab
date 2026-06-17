package com.microbanco.account.boundary;

import com.microbanco.account.dto.AccountRequest;
import com.microbanco.account.dto.AccountResponse;
import com.microbanco.account.dto.BalanceResponse;
import com.microbanco.account.dto.PagedResponse;
import com.microbanco.account.dto.TransferResponse;
import com.microbanco.account.entities.Account;
import com.microbanco.account.services.AccountService;
import com.microbanco.account.services.TransferService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    AccountService accountService;

    @Inject
    TransferService transferService;

    @POST
    public Response openAccount(@Valid AccountRequest request) {
        Account account = accountService.openAccount(
            request.getOwnerName(),
            request.getCurrency(),
            request.getInitialBalance() != null ? request.getInitialBalance() : java.math.BigDecimal.ZERO
        );
        return Response.created(URI.create("/accounts/" + account.iban))
            .entity(AccountResponse.fromEntity(account))
            .build();
    }

    @GET
    @Path("/{iban}")
    public Response getAccount(@PathParam("iban") String iban) {
        Account account = accountService.getAccount(iban);
        return Response.ok(AccountResponse.fromEntity(account)).build();
    }

    @GET
    public Response listAccounts(@QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("size") @DefaultValue("20") int size) {
        var accounts = accountService.listAccounts(page, size);
        var total = accountService.countAccounts();
        var items = accounts.stream().map(AccountResponse::fromEntity).toList();
        return Response.ok(new PagedResponse<>(items, total, page, size)).build();
    }

    @DELETE
    @Path("/{iban}")
    public Response closeAccount(@PathParam("iban") String iban) {
        accountService.closeAccount(iban);
        return Response.noContent().build();
    }

    @GET
    @Path("/{iban}/balance")
    public Response getBalance(@PathParam("iban") String iban) {
        Account account = accountService.getAccount(iban);
        return Response.ok(new BalanceResponse(account.iban, account.balance, account.currency)).build();
    }

    @GET
    @Path("/{iban}/transfers")
    public Response getTransferHistory(@PathParam("iban") String iban,
                                        @QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("20") int size) {
        var transfers = transferService.getTransferHistory(iban, page, size);
        var total = transferService.countTransfers(iban);
        var items = transfers.stream().map(TransferResponse::fromEntity).toList();
        return Response.ok(new PagedResponse<>(items, total, page, size)).build();
    }
}
