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

            // CREATE
            int newId = jdbcManager.insertRecord("John Doe");

            // READ
            String readQuery = "SELECT * FROM my_table_name";
            ResultSet resultSet = jdbcManager.executeQuery(readQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // UPDATE
            int updatedId = 1; // assuming the record with ID 1 exists
            jdbcManager.updateRecord(updatedId, "Updated Name");

            // DELETE
            int deletedId = 2; // assuming the record with ID 2 exists
            jdbcManager.deleteRecord(deletedId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcManager.disconnect();
        }
    }
}
