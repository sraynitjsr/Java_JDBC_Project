import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCManager {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    private JDBCManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private static class SingletonHelper {
        private static final JDBCManager INSTANCE = new JDBCManager(
                "jdbc:mysql://localhost:3306/my_database_name",
                "my_username",
                "my_password"
        );
    }

    public static JDBCManager getInstance(String url, String username, String password) {
        return SingletonHelper.INSTANCE;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
    }

    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        return preparedStatement.executeQuery();
    }

    // CREATE
    public int insertRecord(String name) throws SQLException {
        String insertQuery = "INSERT INTO my_table_name (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            // Get the auto-generated ID (if needed)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating record failed, no ID obtained.");
            }
        }
    }

    // UPDATE
    public void updateRecord(int id, String newName) throws SQLException {
        String updateQuery = "UPDATE my_table_name SET name=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    // DELETE
    public void deleteRecord(int id) throws SQLException {
        String deleteQuery = "DELETE FROM my_table_name WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
