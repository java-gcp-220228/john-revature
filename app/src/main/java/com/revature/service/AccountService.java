package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() { this.accountDao = new AccountDao();}

    public List<Account> getAllAccountByClientId(int clientId) throws SQLException {
        return this.accountDao.getAllAccountsFromClientId(clientId);
    }

    public Account getAccountById(int id) throws SQLException, AccountNotFoundException {
        try {
            Account account = this.accountDao.getAccountById(id);

            if (account == null) {
                throw new AccountNotFoundException("Account with id: " + id + " was not found");
            }
            return account;
        } catch (NumberFormatException e) {
            throw  new IllegalArgumentException("A value that was not corresponding to a valid integer was provided");
        }
    }
}
