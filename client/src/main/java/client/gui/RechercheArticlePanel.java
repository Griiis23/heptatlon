package client.gui;

import magasin.entite.Article;
import client.rmi.Client;
import magasin.entite.Categorie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class RechercheArticlePanel extends JPanel {

    private RechercheArticleController controller;

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> combobox;

    public RechercheArticlePanel() {
        super();

        this.controller = new RechercheArticleController(this);
        this.setLayout(new BorderLayout());

        // Table
        String[] columns = new String[] {
                "Ref", "Categorie", "Nom", "Stock", "Prix unitaire"
        };
        this.tableModel = new DefaultTableModel(null, columns);
        this.table = new JTable(tableModel);

        JScrollPane scroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Controls
        Categorie categories[] = {};
        Client client = new Client();
        try {
            categories = client.getCategorieManagerRemote().findAll().toArray(categories);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.combobox = new JComboBox(categories);
        this.combobox.addItemListener(this.controller);

        this.add(combobox, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);

        this.updateTable();
    }

    public void updateTable() {
        tableModel.setRowCount(0);

        Client client = new Client();
        try {
            List<Article> articles = client.getArticleManagerRemote().findByCategorie(
                    (Categorie) combobox.getSelectedItem()
            );
            for (Article a : articles) {
                tableModel.insertRow(0,new Object[] {
                        a.getRef(),
                        a.getCategorie().toString(),
                        a.getNom(),
                        a.getStock(),
                        a.getPrix(),
                });
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
