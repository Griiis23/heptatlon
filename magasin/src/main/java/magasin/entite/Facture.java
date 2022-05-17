package magasin.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Facture implements Serializable {

    public static enum ModePaiement {
        CB, ESPECE, CHEQUE, BTC
    }

    private String id_fact;
    private double total;
    private Date date;
    private ModePaiement modePaiement;
    private ArrayList<Object[]> articles = new ArrayList<Object[]>();

    public Facture(String id_fact) {
        this.id_fact = id_fact;
        this.date = new Date();
    }

    public Facture(String id_fact, double total, Date date, ModePaiement modePaiement) {
        this.id_fact = id_fact;
        this.total = total;
        this.date = date;
        this.modePaiement = modePaiement;
    }

    public String getIdFact() {
        return id_fact;
    }

    public double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setRef(String ref) {
        this.id_fact = ref;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public ArrayList<Object[]> getArticles() {
        return articles;
    }
}
