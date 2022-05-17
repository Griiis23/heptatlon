package magasin.entite;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CategorieManagerRemote extends Remote {

    public ArrayList<Categorie> findAll() throws RemoteException;

    public Categorie findById(int id) throws RemoteException;


    }
