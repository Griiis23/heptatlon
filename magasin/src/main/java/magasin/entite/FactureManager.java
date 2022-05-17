package magasin.entite;

import magasin.mysql.MySqlConnector;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FactureManager implements FactureManagerRemote {

    private MySqlConnector mySqlConnector = new MySqlConnector();

    public ArrayList<Facture> findAll() throws RemoteException {
        ArrayList<Facture> factures = new ArrayList<>();
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Facture");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Facture facture = new Facture(
                        rs.getString("id_facture"),
                        rs.getDouble("total"),
                        rs.getDate("date"),
                        Facture.ModePaiement.valueOf(rs.getString("mode_payment"))
                );

                PreparedStatement ps_art = conn.prepareStatement("SELECT * FROM ArtFac WHERE id_facture = ?");
                ps_art.setString(1, rs.getString("id_facture"));
                ResultSet rs_art = ps_art.executeQuery();

                ArticleManager articleManager = new ArticleManager();
                while (rs_art.next()) {
                    facture.getArticles().add(new Object[]{
                            articleManager.findByRef(rs_art.getInt("ref_article")),
                            rs_art.getInt("quantite")
                    });
                }
                factures.add(facture);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factures;
    }

    public Facture findById(String idFacture) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Facture WHERE id_facture=?");
            ps.setString(1, idFacture);
            ResultSet rs = ps.executeQuery();

            Facture facture = null;
            if (rs.next()) {
                facture = new Facture(
                        rs.getString("id_facture"),
                        rs.getDouble("total"),
                        rs.getDate("date"),
                        Facture.ModePaiement.valueOf(rs.getString("mode_payment"))
                );

                PreparedStatement ps_art = conn.prepareStatement("SELECT * FROM ArtFac WHERE id_facture = ?");
                ps_art.setString(1,rs.getString("id_facture"));
                ResultSet rs_art = ps_art.executeQuery();

                ArticleManager articleManager = new ArticleManager();
                while(rs_art.next())
                {
                    facture.getArticles().add(new Object[] {
                            articleManager.findByRef(rs_art.getInt("ref_article")),
                            rs_art.getInt("quantite")
                    });
                }
            }

            conn.close();

            return facture;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    @Override
    public double calculChiffreAffaire(Date date) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT SUM(total) FROM Facture WHERE date = ?");

            ps.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = ps.executeQuery();

            double ca = 0;
            if (rs.next()) {
                ca = rs.getDouble(1);
            }
            conn.close();

            return ca;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
