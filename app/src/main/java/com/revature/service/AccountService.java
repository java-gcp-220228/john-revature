package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.AccountNotFoundException;
import com.revature.model.Account;
import io.javalin.http.UnauthorizedResponse;

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

    public boolean deleteAccount(String id, String clientId) throws SQLException, AccountNotFoundException {
        try {
            int intId = Integer.parseInt(id);
            int client_id = Integer.parseInt(clientId);
            Account account = doesAccountExist(intId);
            if (account.getClientId() != client_id) throw new UnauthorizedResponse("Client id does not match");
            return this.accountDao.deleteAccountById(intId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A value that was not corresponding to a valid integer was provided");
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
