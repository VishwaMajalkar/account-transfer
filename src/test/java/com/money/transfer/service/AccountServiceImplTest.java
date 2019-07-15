package com.money.transfer.service;

import com.money.transfer.beans.Account;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImplTest.class);

    @InjectMocks
    AccountServiceImpl accountService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Account account = null;

    @Before
    public void init()throws Exception{
        account = new Account();
        account.setAcctNumber("1448830");
        account.setSortCode("223344");
        account.setAcctUser("Vishwa M");
        account.setAcctBalance(1200D);
        account.setCreatedBy("System");
    }

    @Test
    public void testASaveAccount() throws Exception{
        String status = accountService.saveAccount(account);
        assertEquals("[{\"acctNumber\":\"1448830\",\"sortCode\":\"223344\",\"acctUser\":\"Vishwa M\",\"acctBalance\":1200.0,\"createdBy\":\"System\"}]", status);
        assertEquals(1, accountService.getAccountMap().keySet().size());
    }

    @Test
    public void testBGetAllAccounts() throws Exception{
        String status = accountService.getAllAccounts();
        assertEquals("[{\"acctNumber\":\"1448830\",\"sortCode\":\"223344\",\"acctUser\":\"Vishwa M\",\"acctBalance\":1200.0,\"createdBy\":\"System\"}]", status);
        assertEquals(1, accountService.getAccountMap().keySet().size());
    }

    @Test
    public void testCDeleteAccount() throws Exception{
        accountService.deleteAccount("1448830");
        assertEquals(0, accountService.getAccountMap().keySet().size());
    }

    @Test
    public void testDTransferAmount() throws Exception{
        accountService.saveAccount(account);

        Account account2 = new Account();
        account2.setAcctNumber("1448831");
        account2.setSortCode("223344");
        account2.setAcctUser("Vishwa M");
        account2.setAcctBalance(1300D);
        account2.setCreatedBy("System");
        accountService.saveAccount(account2);
        assertEquals(2, accountService.getAccountMap().keySet().size());
        accountService.transferAmount("1448830","1448831","200");
        assertEquals(1000D, accountService.getAccountMap().get("1448830").getAcctBalance(), 200D);
        assertEquals(1500D, accountService.getAccountMap().get("1448831").getAcctBalance(), 200D);
    }

    @Test
    public void testEWithdrawAmount() throws Exception{
        Account account2 = new Account();
        account2.setAcctNumber("1448831");
        account2.setSortCode("223344");
        account2.setAcctUser("Vishwa M");
        account2.setAcctBalance(1300D);
        account2.setCreatedBy("System");
        accountService.saveAccount(account2);
        accountService.withdrawAmount("1448831","200");
        assertEquals(1100D, accountService.getAccountMap().get("1448831").getAcctBalance(),200D);
    }

    @Test(expected = Exception.class)
    public void testFWithdrawAmountWithException() throws Exception{
        Account account2 = new Account();
        account2.setAcctNumber("1448833");
        account2.setSortCode("223344");
        account2.setAcctUser("Vishwa M");
        account2.setAcctBalance(100D);
        account2.setCreatedBy("System");
        accountService.saveAccount(account2);
        accountService.withdrawAmount("1448833","200");
        assertEquals(1100D, accountService.getAccountMap().get("1448833").getAcctBalance(),200D);
    }

    @Test
    public void testGDepositAmount() throws Exception{
        Account account2 = new Account();
        account2.setAcctNumber("1448831");
        account2.setSortCode("223344");
        account2.setAcctUser("Vishwa M");
        account2.setAcctBalance(1300D);
        account2.setCreatedBy("System");
        accountService.saveAccount(account2);
        accountService.depositAmount("1448831","200");
        assertEquals(1500D, accountService.getAccountMap().get("1448831").getAcctBalance(), 200D);
    }
}
