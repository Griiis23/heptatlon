package siege.entite;

import magasin.entite.Article;
import siege.mysql.MySqlConnector;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleManager implements ArticleManagerRemote {

    private siege.mysql.MySqlConnector mySqlConnector = new MySqlConnector();

    @Override
    public double findPrix(Article article) throws RemoteException {
        try {
            Connection conn = mySqlConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT prix FROM Article WHERE ref_article=?");
            ps.setInt(1,article.getRef());
            ResultSet rs = ps.executeQuery();

            double prix = 0;
            if (rs.next()) {
                prix = rs.getDouble("prix");
            }
            conn.close();

            return prix;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
