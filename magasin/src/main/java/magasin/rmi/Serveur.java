package magasin.rmi;


import magasin.ServeurMagasin;
import magasin.entite.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur {

    public Serveur() {}

    public void start() {
        try {
            Registry reg = LocateRegistry.createRegistry(
                    Integer.parseInt(ServeurMagasin.config.getProperty("PORT_MAGASIN", "25555"))
            );

            ArticleManager articles = new ArticleManager();
            ArticleManagerRemote articleStub = (ArticleManagerRemote) UnicastRemoteObject.exportObject(articles, 0);
            reg.bind("Articles", articleStub);

            FactureManager factures = new FactureManager();
            FactureManagerRemote facturesStub = (FactureManagerRemote) UnicastRemoteObject.exportObject(factures, 0);
            reg.bind("Factures", facturesStub);

            CategorieManager categories = new CategorieManager();
            CategorieManagerRemote categorieStub = (CategorieManagerRemote) UnicastRemoteObject.exportObject(categories, 0);
            reg.bind("Categories", categorieStub);

            System.out.println("SERVEUR RMI MAGASIN OK");
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
