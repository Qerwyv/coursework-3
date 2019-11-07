package database;

import java.io.File;
import java.sql.*;

public class Database {

    private static Connection connection;

    public static Connection getConnection () {
        return connection;
    }



    public boolean connectToDatabase () {
        try {
            Class.forName("org.sqlite.JDBC");
            String path = System.getProperty("user.dir") + "\\javaee_3\\src\\database\\gift.db";
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean terminateConnection () {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery (String query) {
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                //etc
//            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateQuery (String query) {
        try {
            connection.createStatement().executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
