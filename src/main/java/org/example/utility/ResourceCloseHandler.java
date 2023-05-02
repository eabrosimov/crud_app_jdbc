package org.example.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ResourceCloseHandler {

    public static void closeResources(Connection connection, Statement statement){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
