package org.example.utility;

import java.sql.*;

public class JdbcUtils {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;
    private static PreparedStatement statement;

    public static Connection getConnection(){
        if(connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        statement = getConnection().prepareStatement(sql);
        return statement;
    }

    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        return statement;
    }

    public static PreparedStatement getPreparedStatementMovable(String sql) throws SQLException {
        statement = getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return statement;
    }

    public static void closeConnection() {
        if(connection != null){
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatement() {
        if(statement != null){
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
