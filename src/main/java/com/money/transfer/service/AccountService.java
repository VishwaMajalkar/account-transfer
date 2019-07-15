package com.money.transfer.service;

import com.money.transfer.beans.Account;

public interface AccountService {

    String getAllAccounts() throws Exception;
    String saveAccount(Account account) throws Exception;
    void deleteAccount(String accountNumber) throws Exception;
    void transferAmount(String sourceAcctNum, String destAcctNum, String amount) throws Exception;
    void withdrawAmount(String accountNum, String amount) throws Exception;
    void depositAmount(String accountNum, String amount) throws Exception;
}
