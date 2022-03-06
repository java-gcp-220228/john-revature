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

        String url = "jdbc:postgresql://localhost:5433/postgres";
        String username = "postgres";
        String password = "1718";

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
