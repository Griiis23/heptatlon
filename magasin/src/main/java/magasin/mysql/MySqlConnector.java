package magasin.mysql;

import magasin.ServeurMagasin;
import siege.ServeurSiege;

import java.sql.*;

public class MySqlConnector {

    private static final String host = ServeurMagasin.config.getProperty("HOST_BDD_MAGASIN", "localhost");
    private static final String port = ServeurMagasin.config.getProperty("PORT_BDD_MAGASIN", "3306");
    private static final String name = ServeurMagasin.config.getProperty("NOM_BDD_MAGASIN", "heptatlon");

    private static final String URL = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", host, port, name);

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    URL,
                    ServeurMagasin.config.getProperty("USER_BDD_MAGASIN", "bebert"),
                    ServeurMagasin.config.getProperty("PWD_BDD_MAGASIN", "bebert")
            );
            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
