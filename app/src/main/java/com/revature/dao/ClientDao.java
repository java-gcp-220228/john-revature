package com.revature.dao;

import com.revature.model.Client;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    public Client getClientById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM clients WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                int clientId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                return new Client(clientId, firstName, lastName, age);
            }
        }
        return null;
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Connection con = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM clients";

            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                Client client = new Client(id, firstName, lastName, age);
                clients.add(client);
            }
        }
        return clients;
    }
}
