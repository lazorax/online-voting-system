package com.onlineVoting;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DButil {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn != null) return conn;

        try {
            Properties props = new Properties();
            InputStream input = DButil.class.getClassLoader().getResourceAsStream("db_config.properties");
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
