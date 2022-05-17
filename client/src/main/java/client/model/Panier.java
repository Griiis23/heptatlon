package client.model;

import magasin.entite.Article;

import java.util.ArrayList;
import java.util.List;

public class Panier {

    private List<Object[]> panier = new ArrayList<>();

    public void ajouterArticle(Article article, int quantite) {
        Object[] paire = {article, quantite};
        this.panier.add(paire);
    }

    public void viderPanier() {
        this.panier = new ArrayList<>();
    }

    public List<Object[]> getPanier() {
        return panier;
    }

}
