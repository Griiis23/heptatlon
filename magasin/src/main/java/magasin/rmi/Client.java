package magasin.rmi;

import magasin.ServeurMagasin;
import siege.entite.ArticleManagerRemote;
import siege.entite.FactureManagerRemote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private <T> T getRemote(String s) {
        try {
            // Récupérer le registre
            Registry reg = LocateRegistry.getRegistry(
                    ServeurMagasin.config.getProperty("HOST_SIEGE","localhost"),
                    Integer.parseInt(ServeurMagasin.config.getProperty("PORT_SIEGE","25556"))
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

}
