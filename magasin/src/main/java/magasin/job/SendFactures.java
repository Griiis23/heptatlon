package magasin.job;

import magasin.entite.Article;
import magasin.entite.ArticleManager;
import magasin.entite.Facture;
import magasin.entite.FactureManager;
import magasin.rmi.Client;
import siege.entite.FactureManagerRemote;

import java.rmi.RemoteException;

public class SendFactures implements Runnable {
    @Override
    public void run() {
        System.out.println("ENVOIE DES FACTURES");

        Client client = new Client();
        FactureManager manager = new FactureManager();

        try {
            FactureManagerRemote managerSiege = client.getFactureManagerRemote();

            for (Facture f : manager.findAll()) {
                managerSiege.add(f);
            }

            System.out.println("FACTURES ENVOYEES");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
