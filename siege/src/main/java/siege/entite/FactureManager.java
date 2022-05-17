package siege.entite;

import magasin.entite.Article;
import magasin.entite.Facture;
import siege.mysql.MySqlConnector;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FactureManager implements FactureManagerRemote {

    private siege.mysql.MySqlConnector mySqlConnector = new MySqlConnector();


    public Facture findById(String idFacture) throws RemoteException {
        return null;
    }

    public void add(Facture facture) throws RemoteException
    {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Facture VALUES (?, ?, ?, ?)");

            ps.setString(1,facture.getIdFact());
            ps.setDouble(2, facture.getTotal());
            ps.setString(3, facture.getModePaiement().toString());
            ps.setDate(4, new java.sql.Date(facture.getDate().getTime()));

            ps.execute();

            for (Object[] o : facture.getArticles()) {
                ps = conn.prepareStatement("INSERT INTO ArtFac VALUES (?,?,?)");
                Article article = (Article) o[0];
                int quantite = (int) o[1];
                ps.setInt(1, article.getRef());
                ps.setString(2, facture.getIdFact());
                ps.setInt(3,quantite);

            }
            ps.execute();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
