package siege;

import siege.rmi.Serveur;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServeurSiege {

    public static final Properties config = new Properties();
    private Serveur serveur;

    static {
        try {
            config.load(new FileInputStream("siege.config"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServer() {
        Serveur serveur = new Serveur();
        serveur.start();
    }

    public static void main(String[] args) {
        startServer();
    }
}
