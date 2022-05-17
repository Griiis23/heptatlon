package magasin.job;

import magasin.entite.Article;
import magasin.entite.ArticleManager;
import magasin.rmi.Client;
import siege.entite.ArticleManagerRemote;

import java.rmi.RemoteException;

public class UpdatePrix implements Runnable{
    @Override
    public void run() {
        System.out.println("MISE A JOUR DES PRIX");

        Client client = new Client();
        ArticleManager manager = new ArticleManager();

        try {
            ArticleManagerRemote managerSiege = client.getArticleManagerRemote();

            for (Article a : manager.findAll()) {
                double prix = managerSiege.findPrix(a);
                manager.updatePrix(a, prix);
            }

            System.out.println("PRIX MIS A JOUR");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
