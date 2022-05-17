package siege.rmi;

import siege.ServeurSiege;
import siege.entite.ArticleManager;
import siege.entite.ArticleManagerRemote;
import siege.entite.FactureManager;
import siege.entite.FactureManagerRemote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur {


    public Serveur() {}

    public void start() {
        try {
            Registry reg = LocateRegistry.createRegistry(
                    Integer.parseInt(ServeurSiege.config.getProperty("PORT_SIEGE", "25556"))
            );

            ArticleManager articles = new ArticleManager();
            ArticleManagerRemote articleStub = (ArticleManagerRemote) UnicastRemoteObject.exportObject(articles, 0);
            reg.bind("Articles", articleStub);

            FactureManager factures = new FactureManager();
            FactureManagerRemote facturesStub = (FactureManagerRemote) UnicastRemoteObject.exportObject(factures, 0);
            reg.bind("Factures", facturesStub);

            System.out.println("SERVEUR RMI SIEGE OK");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
