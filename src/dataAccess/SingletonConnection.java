package dataAccess;

import Utils.Env;
import exceptions.DAORetrievalFailedException;
import view.ViewCore;

import java.sql.*;

public class SingletonConnection {
    private static Connection connection;
    private static final String user;
    private static final String password;
    private static final String dbName;
    private static final String connectionURL;

    static {
        user = Env.getDotenv().get("PG_USER");
        password = Env.getDotenv().get("PG_PASSWORD");
        dbName = Env.getDotenv().get("PG_DB");
        connectionURL = "jdbc:postgresql://localhost:5432/" + dbName;
    }

    public static Connection getInstance() throws DAORetrievalFailedException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionURL, user, password);
                System.out.println("Connection successful!");
            } catch (SQLException e) {
                throw new DAORetrievalFailedException("La connexion à la base de donnée a échouée : ", e.getMessage());
            }
        }

        return connection;
    }
}
