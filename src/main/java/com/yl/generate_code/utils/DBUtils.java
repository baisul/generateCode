package com.yl.generate_code.utils;

import com.yl.generate_code.model.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static Connection connection;
    public static Connection getConnection() {
        return connection;
    }

    public static Connection init(Db db) {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection( db.getUrl(),db.getUsername(), db.getPassword());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return connection;
    }
}
