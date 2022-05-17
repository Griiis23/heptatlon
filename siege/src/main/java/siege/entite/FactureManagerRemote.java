package siege.entite;

import magasin.entite.Facture;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FactureManagerRemote extends Remote {
    public Facture findById(String idFacture) throws RemoteException;

    public void add(Facture facture) throws RemoteException;
}
