package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() { this.accountDao = new AccountDao();}

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getAllAccountByClientId(int clientId) throws SQLException {
        return this.accountDao.getAllAccountsFromClientId(clientId);
    }

    public Account getAccountById(int id) throws SQLException, AccountNotFoundException {
        try {
            return doesAccountExist(id);
        } catch (NumberFormatException e) {
            throw  new IllegalArgumentException("A value that was not corresponding to a valid integer was provided");
        }
    }

    public Account createAccount(Account newAccount) throws SQLException {
        return this.accountDao.createNewAccountForClientId(newAccount);
    }

    public Account updateAccount(Account account) throws SQLException, AccountNotFoundException {
        try {
            doesAccountExist(account.getId());
            return this.accountDao.updateAccount(account);
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException("Update attempt on account that does not exist :" + e.getMessage());
        }
    }

    private Account doesAccountExist(int id) throws SQLException, AccountNotFoundException {
        Account account = this.accountDao.getAccountById(id);

        if (account == null) {
            throw new AccountNotFoundException("Account with id: " + id + " was not found");
        }
        return account;
    }

}
