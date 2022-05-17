package magasin.entite;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface FactureManagerRemote extends Remote {

    public ArrayList<Facture> findAll() throws RemoteException;
    public Facture findById(String idFacture) throws RemoteException;

    public void add(Facture facture) throws RemoteException;

    public double calculChiffreAffaire(Date date) throws RemoteException;
}
