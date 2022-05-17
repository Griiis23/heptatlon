package client.gui;

import client.model.Panier;
import magasin.entite.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanierPanel extends JPanel {

    private Panier panier;

    private JTable table;
    private DefaultTableModel tableModel;

    public PanierPanel(Panier panier) {
        super();

        this.panier = panier;
        PanierController controller = new PanierController(this, panier);

        this.setLayout(new BorderLayout());

        // Table
        String[] columns = new String[] {
                "Ref", "Nom","Prix unitaire", "Quantité", "Prix"
        };
        this.tableModel = new DefaultTableModel(null, columns);
        this.table = new JTable(tableModel);


        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Controls
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(7,1));

        JButton button;
        button = new JButton("Consulter le stock d'un article");
        button.setActionCommand("consulter_article");
        button.addActionListener(controller);
        controls.add(button);
        button = new JButton("Recherche par catégorie");
        button.setActionCommand("recherche_article");
        button.addActionListener(controller);
        controls.add(button);
        button = new JButton("Consulter une facture");
        button.setActionCommand("consulter_facture");
        button.addActionListener(controller);
        controls.add(button);
        button = new JButton("Chiffre d'affaire");
        button.setActionCommand("chiffre_affaire");
        button.addActionListener(controller);
        controls.add(button);

        controls.add(new JSeparator());
        button = new JButton("Ajouter un article au panier");
        button.setActionCommand("ajout_panier");
        button.addActionListener(controller);
        controls.add(button);
        button = new JButton("Payer");
        button.setActionCommand("payer");
        button.addActionListener(controller);
        controls.add(button);

        this.add(scroll, BorderLayout.LINE_START);
        this.add(controls, BorderLayout.CENTER);

        this.updatePanier();
    }

    public void updatePanier() {
        tableModel.setRowCount(0);

        for (Object[] o : this.panier.getPanier()) {
            Article a = (Article) o[0];
            int quantite = (int) o[1];
            tableModel.insertRow(0,new Object[] {
                    a.getRef(),
                    a.getNom(),
                    a.getPrix(),
                    quantite,
                    a.getPrix()*quantite,
            });
        }
    }
}
