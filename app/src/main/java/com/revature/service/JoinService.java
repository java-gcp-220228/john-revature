package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.model.Client;

import java.sql.SQLException;
import java.util.List;

public class JoinService {
    private ClientDao clientDao;
    private AccountDao accountDao;

    public JoinService() {
        this.clientDao = new ClientDao();
        this.accountDao = new AccountDao();
    }

    public List<Client> getClientAccounts() throws SQLException {
        List<Client> clients = clientDao.getAllClients();
        for (Client c: clients) {
            c.setAccounts(accountDao.getAllAccountsFromClientId(c.getId()));
        }
        return clients;
    }
}
