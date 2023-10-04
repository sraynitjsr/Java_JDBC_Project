import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class JDBCManager {
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

