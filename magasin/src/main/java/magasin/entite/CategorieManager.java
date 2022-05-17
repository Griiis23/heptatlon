package magasin.entite;

import magasin.mysql.MySqlConnector;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategorieManager implements CategorieManagerRemote {

    private MySqlConnector mySqlConnector = new MySqlConnector();

    public ArrayList<Categorie> findAll() throws RemoteException {
        ArrayList<Categorie> categories = new ArrayList<>();
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Categorie");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categorie categorie = new Categorie(
                        rs.getInt("id_categorie"),
                        rs.getString("nom")
                );
                categories.add(categorie);
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return categories;
    }

    public Categorie findById(int id) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Categorie WHERE id_categorie = ? ");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Categorie categorie = null;
            if (rs.next()) {
                categorie = new Categorie(
                        rs.getInt("id_categorie"),
                        rs.getString("nom")
                );
            }
            conn.close();

            return categorie;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}