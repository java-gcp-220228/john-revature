package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.dao.JoinDao;
import com.revature.model.Client;

import java.sql.SQLException;
import java.util.List;

public class JoinService {
    private JoinDao joinDao;

    public JoinService() {
        this.joinDao = new JoinDao();
    }

    public List<Client> getClientAccounts() throws SQLException {
        return joinDao.getClientAccounts();
    }
}
