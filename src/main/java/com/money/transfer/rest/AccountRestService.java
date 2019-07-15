package com.money.transfer.rest;

import com.money.transfer.beans.Account;
import javax.ws.rs.core.Response;

public interface AccountRestService {

    Response getAllAccounts() throws Exception;
    Response saveAccount(Account account) throws Exception;
    Response deleteAccount(String acctNumber) throws Exception;
    Response transferAmount(String sourceAccount, String destAccount, String amount) throws Exception;
    Response withdrawAmount(String accountNum, String amount) throws Exception;
    Response depositAmount(String accountNum, String amount) throws Exception;
}
