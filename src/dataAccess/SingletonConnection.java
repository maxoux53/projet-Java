package dataAccess;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class SingletonConnection {
    private static Connection connection;

    private static final Dotenv dotenv = Dotenv.load();
    private static final String user = dotenv.get("PG_USER");
    private static final String password = dotenv.get("PG_PASSWORD");
    private static final String dbName = dotenv.get("PG_DB");
    private static final String connectionURL = "jdbc:postgresql://localhost:5432/" + dbName;

    public static Connection getInstance() throws DAORetrievalFailedException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionURL, user, password);
                System.out.println("Connection successful!");
            } catch (SQLException e) {
                throw new DAORetrievalFailedException("SQL connection to database failed" + e.getMessage());
            }
        }

        return connection;
    }
}
