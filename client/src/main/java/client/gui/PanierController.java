package client.gui;

import client.AppClient;
import client.model.Panier;
import client.rmi.Client;
import magasin.entite.Article;
import magasin.entite.ArticleManagerRemote;
import magasin.entite.Facture;
import magasin.entite.FactureManagerRemote;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PanierController implements ActionListener {

    private PanierPanel vue;
    private Panier model;

    public PanierController(PanierPanel vue, Panier model) {
        this.vue = vue;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "consulter_article":
                consulterArticle();
                break;
            case "consulter_facture":
                consulterFacture();
                break;
            case "recherche_article":
                rechercheArticle();
                break;
            case "chiffre_affaire":
                chiffreAffaire();
                break;
            case "ajout_panier":
                ajoutPanier();
                break;
            case "payer":
                payer();
                break;
        }
    }

    private void consulterArticle() {
        String ref = JOptionPane.showInputDialog(
                this.vue,
                "Réference de l'article :",
                "Stock",
                JOptionPane.QUESTION_MESSAGE
        );
        if(ref == null || ref.length() <= 0) return;

        Client client = new Client();
        try {
            Article a = client.getArticleManagerRemote().findByRef(Integer.parseInt(ref));
            if (a != null) {
                JOptionPane.showMessageDialog(
                        this.vue,
                        "Ref : " + a.getRef() +
                                "\nNom : " + a.getNom() +
                                "\nCategorie : " + a.getCategorie() +
                                "\nStock : " + a.getStock() +
                                "\nPrix : " + a.getPrix(),
                        "Article",
                        JOptionPane.PLAIN_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this.vue,"Référence inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void consulterFacture() {
        String id = JOptionPane.showInputDialog(
                this.vue,
                "Id facture :",
                "Facture",
                JOptionPane.QUESTION_MESSAGE
        );
        if(id == null || id.length() <= 0) return;

        Client client = new Client();
        try {
            Facture f = client.getFactureManagerRemote().findById(id);

            if (f != null) {
                JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this.vue), "Facture");
                d.add(new FacturePanel(f));
                d.pack();
                d.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this.vue,"Facture inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void rechercheArticle() {
        JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this.vue), "Rechercher un article");
        d.add(new RechercheArticlePanel());
        d.pack();
        d.setVisible(true);
    }

    private void chiffreAffaire() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = sdf.parse(JOptionPane.showInputDialog(
                    this.vue,
                    "Date (jj/mm/aaaa) :",
                    "Chiffre d'affaire",
                    JOptionPane.QUESTION_MESSAGE
            ));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this.vue,"Date invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client = new Client();
        try {
            JOptionPane.showMessageDialog(
                    this.vue,
                    "Chiffre d'affaire pour la période : " + client.getFactureManagerRemote().calculChiffreAffaire(date),
                    "Chiffre d'affaire",
                    JOptionPane.PLAIN_MESSAGE
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void ajoutPanier() {
        String ref = JOptionPane.showInputDialog(
                this.vue,
                "Réference de l'article :",
                "Panier",
                JOptionPane.QUESTION_MESSAGE
        );
        if(ref == null || ref.length() <= 0) return;

        Client client = new Client();
        try {
            Article a = client.getArticleManagerRemote().findByRef(Integer.parseInt(ref));
            if (a == null) {
                JOptionPane.showMessageDialog(this.vue,"Référence inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantite = Integer.parseInt(JOptionPane.showInputDialog(
                    this.vue,
                    "Quantité :",
                    "Panier",
                    JOptionPane.QUESTION_MESSAGE
            ));

            if(a.getStock() < quantite) {
                JOptionPane.showMessageDialog(this.vue,"Stock insufisant", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.model.ajouterArticle(a, quantite);
            this.vue.updatePanier();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void payer() {

        Facture facture = new Facture(AppClient.config.getProperty("ID_MAGASIN", "MAG") + "_" +  UUID.randomUUID().toString().substring(0,4));

        Client client = new Client();
        double total = 0;
        try {
            ArticleManagerRemote manager = client.getArticleManagerRemote();
            for (Object[] tab : this.model.getPanier()) {
                Article article = (Article) tab[0];
                Integer quantite = (Integer) tab[1];

                manager.updateStock(article, -quantite);
                total += article.getPrix() * quantite;
                facture.getArticles().add(tab);
            }
            facture.setTotal(total);

            Facture.ModePaiement[] choices = Facture.ModePaiement.values();
            Facture.ModePaiement mode = (Facture.ModePaiement) JOptionPane.showInputDialog(
                    this.vue,
                    "Choisissez un mode de paiement",
                    "Mode de paiement",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[1]
            );
            facture.setModePaiement(mode);

            FactureManagerRemote factureManager = client.getFactureManagerRemote();
            factureManager.add(facture);

            this.model.viderPanier();
            this.vue.updatePanier();

            JDialog d = new JDialog(SwingUtilities.getWindowAncestor(this.vue), "Facture");
            d.add(new FacturePanel(facture));
            d.pack();
            d.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
