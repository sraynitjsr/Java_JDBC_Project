import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/my_database_name";
        String username = "my_username";
        String password = "my_password";

        JDBCManager jdbcManager = JDBCManager.getInstance(url, username, password);

        try {
            jdbcManager.connect();

            String sqlQuery = "SELECT * FROM my_table_name";
            ResultSet resultSet = jdbcManager.executeQuery(sqlQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcManager.disconnect();
        }
    }
}
