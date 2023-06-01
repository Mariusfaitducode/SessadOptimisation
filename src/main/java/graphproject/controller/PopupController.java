package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;

public class PopupController {

    // Graphic attributes of the pop-up
    private Pane popupPane, popupPaneParent;

    private Button start;

    private TextField popSize, generationNbr, crossOverRate, mutationRate;

    public PopupController(Pane popupPaneParent){
        this.popupPaneParent = popupPaneParent;
        this.popupPane = (Pane) popupPaneParent.lookup("#id-popup-pane");

        this.popSize = (TextField) popupPane.lookup("#id-popup-popSize");
        this.generationNbr = (TextField) popupPane.lookup("#id-popup-generationNbr");
        this.crossOverRate = (TextField) popupPane.lookup("#id-popup-crossOverRate");
        this.mutationRate = (TextField) popupPane.lookup("#id-popup-mutationRate");
        this.start = (Button) popupPane.lookup("#id-popup-buttonStart");
    }

    public void setVisible(boolean statut) {
        popupPaneParent.setVisible(statut);
    }

    public void setParameters(int popSize, int generationNbr, double crossOverRate, double MutationRate){
        this.popSize.setText(String.valueOf(popSize));
        this.generationNbr.setText(String.valueOf(generationNbr));
        this.crossOverRate.setText(String.valueOf(crossOverRate));
        this.mutationRate.setText(String.valueOf(MutationRate));
    }

    public Button getStart() {
        return start;
    }

    public int getPopSize() {
        return Integer.parseInt(popSize.getText());
    }

    public int getGenerationNbr() {
        return Integer.parseInt(generationNbr.getText());
    }

    public double getCrossOverRate() {
        return Double.parseDouble(crossOverRate.getText());
    }

    public double getMutationRate() {
        return Double.parseDouble(mutationRate.getText());
    }
}
