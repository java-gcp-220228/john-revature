package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public List<Account> getAllAccountsFromClientId(int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM accounts WHERE client_id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, clientId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("typeOfAccount");
                double balance = rs.getDouble("balance");

                Account account = new Account(id, type, balance, clientId);
                accounts.add(account);
            }
        }
        return accounts;
    }

    public Account getAccountById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM accounts WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                String type = rs.getString("typeOfAccount");
                double balance = rs.getDouble("balance");
                int clientId = rs.getInt("client_id");

                return new Account(id, type, balance, clientId);
            }
        }
        return null;
    }
}
