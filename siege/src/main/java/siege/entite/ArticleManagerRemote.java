package siege.entite;

import magasin.entite.Article;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArticleManagerRemote extends Remote {

    public double findPrix(Article article) throws RemoteException;

}