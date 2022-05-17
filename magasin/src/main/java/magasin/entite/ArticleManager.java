package magasin.entite;

import magasin.mysql.MySqlConnector;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticleManager implements ArticleManagerRemote {

    private MySqlConnector mySqlConnector = new MySqlConnector();

    public ArrayList<Article> findAll() throws RemoteException
    {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Article");
            ResultSet rs = ps.executeQuery();

            CategorieManager categorieManager = new CategorieManager();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("ref_article"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        categorieManager.findById(rs.getInt("id_categorie"))
                );
                articles.add(article);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }


    public Article findByRef(int ref) throws RemoteException
    {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Article WHERE ref_article=?");
            ps.setInt(1,ref);
            ResultSet rs = ps.executeQuery();

            CategorieManager categorieManager = new CategorieManager();

            Article article = null;
            if (rs.next()) {
                article = new Article(
                        rs.getInt("ref_article"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        categorieManager.findById(rs.getInt("id_categorie"))
                );
            }
            conn.close();

            return article;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Article findByNom(String nom) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Article WHERE nom=?");
            ps.setString(1,nom);
            ResultSet rs = ps.executeQuery();

            CategorieManager categorieManager = new CategorieManager();

            Article article = null;
            if (rs.next()) {
                article = new Article(
                        rs.getInt("ref_article"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        categorieManager.findById(rs.getInt("id_categorie"))
                );
            }
            conn.close();

            return article;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<Article> findByCategorie(Categorie categorie) throws RemoteException {
        ArrayList<Article> articles = new ArrayList<>();
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Article WHERE id_categorie = ?");
            ps.setInt(1,categorie.getId());
            ResultSet rs = ps.executeQuery();

            CategorieManager categorieManager = new CategorieManager();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("ref_article"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("stock"),
                        categorieManager.findById(rs.getInt("id_categorie"))
                );
                articles.add(article);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return articles;
    }


    public void add(Article article) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Article VALUES (null, ?, ?, ?, ?)");

            ps.setString(1, article.getNom());
            ps.setDouble(2, article.getPrix());
            ps.setInt(3, article.getStock());
            ps.setInt(4, article.getCategorie().getId());

            ps.execute();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStock(Article article, int delta) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE Article SET stock = stock + ? WHERE ref_article = ?");

            ps.setInt(1, delta);
            ps.setInt(2, article.getRef());

            ps.execute();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePrix(Article article, double prix) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE Article SET prix = ? WHERE ref_article = ?");

            ps.setDouble(1, prix);
            ps.setInt(2, article.getRef());

            ps.execute();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}