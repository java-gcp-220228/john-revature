package com.revature.utility;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {
    private ConnectionUtility() {

    }
    public static Connection getConnection() throws SQLException {

        DriverManager.registerDriver(new Driver());

        String url = System.getenv("POSTGRES_URL");
        String username = System.getenv("POSTGRES_USER");
        String password = System.getenv("POSTGRES_PASSWORD");

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
