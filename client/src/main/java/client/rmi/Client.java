package client.rmi;

import client.AppClient;
import magasin.entite.ArticleManagerRemote;
import magasin.entite.CategorieManagerRemote;
import magasin.entite.FactureManagerRemote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private <T> T getRemote(String s) {
        try {
            // Récupérer le registre
            Registry reg = LocateRegistry.getRegistry(
                    AppClient.config.getProperty("HOST_MAGASIN", "localhost"),
                    Integer.parseInt(AppClient.config.getProperty("PORT_MAGASIN", "25555"))
            );

            // Recherche dans le registre de l'objet distant
            return (T) reg.lookup(s);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArticleManagerRemote getArticleManagerRemote() {
        return getRemote("Articles");
    }

    public FactureManagerRemote getFactureManagerRemote() {
        return getRemote("Factures");
    }

    public CategorieManagerRemote getCategorieManagerRemote() {
        return getRemote("Categories");
    }
}