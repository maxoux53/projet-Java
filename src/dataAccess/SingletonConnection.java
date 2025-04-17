package dataAccess;

import utils.Env;
import java.sql.*;

public class SingletonConnection {
    private static Connection connection;

    private static final String user = Env.getDotenv().get("PG_USER");
    private static final String password = Env.getDotenv().get("PG_PASSWORD");
    private static final String dbName = Env.getDotenv().get("PG_DB");
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
