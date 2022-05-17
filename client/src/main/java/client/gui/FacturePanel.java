package client.gui;

import magasin.entite.Article;
import magasin.entite.Facture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacturePanel extends JPanel {
    private Facture facture;

    private JTable table;
    private DefaultTableModel tableModel;

    public FacturePanel(Facture facture) {
        super();

        this.facture = facture;

        this.setLayout(new BorderLayout());

        // Table
        String[] columns = new String[] {
                "Ref", "Nom","Prix unitaire", "Quantité", "Prix"
        };
        this.tableModel = new DefaultTableModel(null, columns);
        this.table = new JTable(tableModel);

        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(2,2));
        labels.add(new JLabel("ID : " + facture.getIdFact()));
        labels.add(new JLabel("Date : " + facture.getDate().toString()));
        labels.add(new JLabel("Mode de paiement : " + facture.getModePaiement().name()));
        labels.add(new JLabel("Total : " + String.valueOf(facture.getTotal())));

        this.add(labels, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);

        this.updateTable();
    }

    public void updateTable() {
        tableModel.setRowCount(0);

        for (Object[] o : this.facture.getArticles()) {
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
