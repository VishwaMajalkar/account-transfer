package com.money.transfer.rest;

import com.money.transfer.beans.Account;
import com.money.transfer.service.AccountService;
import com.money.transfer.service.AccountServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vishwanath on 15/07/2019.
 * Class to Expose Rest APIs for Account Data
 */
@Path("/account")
@Api(value = "Account Service", description = "REST Endpoints for Account Service")
public class AccountRestServiceImpl implements AccountRestService{

    private static final Logger log = LoggerFactory.getLogger(AccountRestServiceImpl.class);
    private AccountService accountService;

    @GET
    @Path("getAllAccounts")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAllAccounts() throws Exception {
        log.info("Entry - getAllAccounts");
        String accounts = getAccountService().getAllAccounts();
        log.info("Exit - getAllAccounts ");
        return Response.status(Response.Status.OK).entity(accounts).build();
    }


    @PUT
    @Path("saveAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response saveAccount(Account account) throws Exception{
        log.info("Entry - saveAccount");
        String accountData = getAccountService().saveAccount(account);
        log.info("Exit - saveAccount");
        return Response.status(Response.Status.OK).entity(accountData).build();
    }

    @DELETE
    @Path("deleteAccount/{acctNumber}")
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public Response deleteAccount(@PathParam("acctNumber") String acctNumber) throws Exception {
        log.info("Entry - deleteAccount");
        getAccountService().deleteAccount(acctNumber);
        log.info("Exit - deleteAccount");
        return Response.status(Response.Status.OK).entity("SUCCESS").build();
    }

    @POST
    @Path("transferAmount/{source}/{dest}/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public Response transferAmount(@PathParam("source") String sourceAccount,
                                   @PathParam("dest") String destAccount,
                                   @PathParam("amount") String amount) throws Exception {
        log.info("Entry - transferAmount");
        getAccountService().transferAmount(sourceAccount, destAccount, amount);
        log.info("Exit - transferAmount");
        return Response.status(Response.Status.OK).entity("SUCCESS").build();
    }

    @POST
    @Path("withdrawAmount/{acctNumber}/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public Response withdrawAmount(@PathParam("acctNumber") String accountNum,
                                   @PathParam("amount") String amount) throws Exception {
        log.info("Entry - withdrawAmount");
        getAccountService().withdrawAmount(accountNum, amount);
        log.info("Exit - withdrawAmount");
        return Response.status(Response.Status.OK).entity("SUCCESS").build();
    }

    @POST
    @Path("depositAmount/{acctNumber}/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public Response depositAmount(@PathParam("acctNumber") String accountNum,
                                  @PathParam("amount") String amount) throws Exception {
        log.info("Entry - depositAmount");
        getAccountService().depositAmount(accountNum, amount);
        log.info("Exit - depositAmount");
        return Response.status(Response.Status.OK).entity("SUCCESS").build();
    }

    public AccountService getAccountService() {
        if(accountService == null){
            accountService = new AccountServiceImpl();
        }
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}