package magasin.entite;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ArticleManagerRemote extends Remote {

    public ArrayList<Article> findAll() throws RemoteException;

    public Article findByRef(int ref) throws RemoteException;

    public Article findByNom(String nom) throws RemoteException;

    public ArrayList<Article> findByCategorie(Categorie categorie) throws RemoteException;

    public void add(Article article) throws RemoteException;

    public void updateStock(Article article, int delta) throws RemoteException;

    public void updatePrix(Article article, double prix) throws RemoteException;

}