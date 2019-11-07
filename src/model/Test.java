import java.sql.*;

class Test {
 public static void main(String[] args) { // object testing
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection (
            	"jdbc:sqlite:E:\\Million\\Java EE\\javaee_3\\src\\database\\gift.db");
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}