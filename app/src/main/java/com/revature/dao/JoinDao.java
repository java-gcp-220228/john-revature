package com.revature.dao;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoinDao {
    public List<Client> getClientAccounts() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "SELECT c.*, json_agg(a) as bankAccounts " +
            "FROM clients AS c " +
            "FULL JOIN accounts AS a on a.client_id=c.id " +
            "GROUP BY c.id";

            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                JSONArray json = new JSONArray(rs.getString("bankAccounts"));
                List<Account> accounts = new ArrayList<>();
                for (int i = 0; i < json.length(); i++) {
                    if(json.isNull(i)) continue;
                    JSONObject obj = json.getJSONObject(i);
                    int accountId = obj.getInt("id");
                    String type = obj.getString("typeofaccount");
                    double balance = obj.getDouble("balance");
                    int clientId = obj.getInt("client_id");
                    Account account = new Account(accountId, type, balance, clientId);
                    accounts.add(account);
                }
                Client client = new Client(id, firstName, lastName, age, accounts);
                clients.add(client);
            }
        }
        return clients;
    }
}
