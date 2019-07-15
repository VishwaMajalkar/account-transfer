package com.money.transfer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.money.transfer.beans.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by Vishwanath on 15/07/2019.
 * Service Class to process Account Data
 */
public class AccountServiceImpl implements AccountService{

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static ConcurrentMap<String, Account> accountMap = new ConcurrentHashMap<String, Account>();

    //This is just for testing to initialize accountMap with test data
    static {
        Account account = new Account();
        account.setAcctNumber("1448830");
        account.setSortCode("223344");
        account.setAcctUser("Vishwa M");
        account.setAcctBalance(12000D);
        account.setCreatedBy("System");
        account.setCreatedDt(new Date());
        accountMap.putIfAbsent(account.getAcctNumber(), account);
    }

    @Override
    public String getAllAccounts() throws Exception{
        log.info("calling getAllAccounts");
        List<Account> accountList = accountMap.keySet().stream().filter(key -> !key.isEmpty())
                .map(key -> accountMap.get(key)).collect(Collectors.toList());
        return writeData(accountList);
    }

    @Override
    public String saveAccount(Account account) throws Exception{
        log.info("calling saveAccount");
        // If we get a non empty value that means the account already exists.
        if (null != accountMap.putIfAbsent(account.getAcctNumber(), account)) {
            //update account data
            accountMap.replace(account.getAcctNumber(), account);
        }
        return writeData(Lists.newArrayList(account));
    }

    @Override
    public void deleteAccount(String accountNumber) throws Exception{
        log.info("calling deleteAccount");
        //Delete given account
        accountMap.remove(accountNumber);
    }

    @Override
    public void transferAmount(String sourceAcctNum, String destAcctNum, String amount) throws Exception{
        log.info("calling transferAmount");
        if(!sourceAcctNum.isEmpty() && !destAcctNum.isEmpty()){
            //Transfer given account to destination account
            Account sourceAccount = accountMap.get(sourceAcctNum);
            Account destAccount = accountMap.get(destAcctNum);
            if (sourceAccount != null && destAccount != null) {

                //If both account sort codes are same means both accounts are in same branch
                if(!sourceAccount.getSortCode().isEmpty() && !destAccount.getSortCode().isEmpty()){
                    if(sourceAccount.getSortCode().equals(destAccount.getSortCode())){
                        log.info("Your fund will be transferred immediately");
                    }else{
                        log.info("Your fund will be transferred in 24 to 48 Hours");
                    }
                }

                //Credit amount to destination account and update
                depositAmount(destAccount, Double.parseDouble(amount));
                accountMap.replace(destAccount.getAcctNumber(), destAccount);

                //Deduct amount from source account and update
                withdrawAmount(sourceAccount, Double.parseDouble(amount));
                accountMap.replace(sourceAccount.getAcctNumber(), sourceAccount);
            }
        }
    }

    @Override
    public void withdrawAmount(String accountNum, String amount) throws Exception{
        log.info("calling withdrawAmount");
        if(!accountNum.isEmpty() && !amount.isEmpty()){
            //Withdraw given account from given account
            Account account = accountMap.get(accountNum);
            if (account != null) {
                //Deduct amount from given account and update
                withdrawAmount(account, Double.parseDouble(amount));
                accountMap.replace(account.getAcctNumber(), account);
            }
        }
    }

    @Override
    public void depositAmount(String accountNum, String amount) throws Exception{
        log.info("calling depositAmount");
        if(!accountNum.isEmpty() && !amount.isEmpty()){
            //Withdraw given account from given account
            Account account = accountMap.get(accountNum);
            if (account != null) {
                //Credit amount to given account and update
                depositAmount(account, Double.parseDouble(amount));
                accountMap.replace(account.getAcctNumber(), account);
            }
        }
    }

    private void withdrawAmount(Account account, double amount) throws Exception{
        //Validate if Sufficient funds available or not
        if(account.getAcctBalance() < amount){
            throw new Exception("No Sufficient Funds Available in Account");
        }
        double newBalance = account.getAcctBalance() - amount;
        account.setAcctBalance(newBalance);
    }

    private void depositAmount(Account account, double amount) throws Exception{
        double newBalance = account.getAcctBalance() + amount;
        account.setAcctBalance(newBalance);
    }


    public ConcurrentMap<String, Account> getAccountMap() {
        return accountMap;
    }

    private String writeData(List<Account> accounts) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(accounts);
    }
}
