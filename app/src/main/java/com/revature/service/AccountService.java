package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() { this.accountDao = new AccountDao();}

    public List<Account> getAllAccountByClientId(int clientId) throws SQLException {
        return this.accountDao.getAllAccountsFromClientId(clientId);
    }
}
