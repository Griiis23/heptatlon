package client.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RechercheArticleController implements ItemListener {

    private RechercheArticlePanel vue;

    public RechercheArticleController(RechercheArticlePanel vue) {
        this.vue = vue;
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            this.vue.updateTable();
        }
    }
}
