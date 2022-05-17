package siege.mysql;

import siege.ServeurSiege;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {

    private static final String host = ServeurSiege.config.getProperty("HOST_BDD_SIEGE", "localhost");
    private static final String port = ServeurSiege.config.getProperty("PORT_BDD_SIEGE", "3306");
    private static final String name = ServeurSiege.config.getProperty("NOM_BDD_SIEGE", "heptatlonSiege");

    private static final String URL = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", host, port, name);

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    URL,
                    ServeurSiege.config.getProperty("USER_BDD_SIEGE", "bebert"),
                    ServeurSiege.config.getProperty("PWD_BDD_SIEGE", "bebert")
            );

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
