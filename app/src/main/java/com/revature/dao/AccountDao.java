package com.revature.dao;

import com.revature.model.Account;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
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

    public Account createNewAccountForClientId(Account newAccount) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "INSERT INTO accounts (typeOfAccount, balance, client_id) " +
                    "VALUES (?::account_type, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, newAccount.getType());
            pstmt.setDouble(2, 0);
            pstmt.setInt(3, newAccount.getClientId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new Account(id, newAccount.getType(), newAccount.getClientId());
        }
    }

    public Account updateAccount(Account account) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "UPDATE accounts " +
                    "SET typeOfAccount = ?::account_type, " +
                    "balance = ?, " +
                    "client_id = ?" +
                    "WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, account.getType());
            pstmt.setDouble(2,account.getBalance());
            pstmt.setInt(3, account.getClientId());
            pstmt.setInt(4, account.getId());

            pstmt.executeUpdate();
        }
        return account;
    }

    public boolean deleteAccountById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "DELETE FROM accounts WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, id);

            int numberOfRecordsDeleted = pstmt.executeUpdate();

            if (numberOfRecordsDeleted == 1) {
                return true;
            }
        }
        return false;
    }
}
