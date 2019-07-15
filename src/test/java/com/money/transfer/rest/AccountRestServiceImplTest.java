package com.money.transfer.rest;

import com.money.transfer.beans.Account;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AccountRestServiceImplTest extends JerseyTest {

    private static final Logger log = LoggerFactory.getLogger(AccountRestServiceImplTest.class);

    private Account account = null;

    @Before
    public void init()throws Exception{
        account = new Account();
        account.setAcctNumber("1448830");
        account.setSortCode("223344");
        account.setAcctUser("Vishwa M");
        account.setAcctBalance(12000D);
        account.setCreatedBy("System");
        account.setCreatedDt(new Date());
    }

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountRestServiceImpl.class);
    }

    @Test
    public void testGetAllAccounts() {
        Response response = target("/account/getAllAccounts").request().get();
        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void testSaveAccount() {
        Response response = target("/account/saveAccount").request().put(Entity.entity(account, MediaType.APPLICATION_JSON));
        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void testDeleteAccount() {
        Response response = target("/account/deleteAccount/1448830").request().delete();
        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void testTransferAmount() {
        Response response = target("/account/transferAmount/1448830/1448831/300").request().post(Entity.entity(null, MediaType.TEXT_PLAIN));
        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void testWithdrawAmount() {
        Response response = target("/account/withdrawAmount/1448831/300").request().post(Entity.entity(null, MediaType.TEXT_PLAIN));
        assertEquals("should return status 200", 200, response.getStatus());
    }

    @Test
    public void testDepositAmount() {
        Response response = target("/account/depositAmount/1448831/300").request().post(Entity.entity(null, MediaType.TEXT_PLAIN));
        assertEquals("should return status 200", 200, response.getStatus());
    }
}
