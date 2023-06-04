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

    private TextField gen1_popSize, gen1_generationNbr, gen1_crossOverRate, gen1_mutationRate;
    private TextField gen2_popSize, gen2_generationNbr, gen2_crossOverRate, gen2_mutationRate;

    public PopupController(Pane popupPaneParent){
        this.popupPaneParent = popupPaneParent;
        this.popupPane = (Pane) popupPaneParent.lookup("#id-popup-pane");

        this.gen1_popSize = (TextField) popupPane.lookup("#id-popup-gen1-popSize");
        this.gen1_generationNbr = (TextField) popupPane.lookup("#id-popup-gen1-generationNbr");
        this.gen1_crossOverRate = (TextField) popupPane.lookup("#id-popup-gen1-crossOverRate");
        this.gen1_mutationRate = (TextField) popupPane.lookup("#id-popup-gen1-mutationRate");

        this.gen2_popSize = (TextField) popupPane.lookup("#id-popup-gen2-popSize");
        this.gen2_generationNbr = (TextField) popupPane.lookup("#id-popup-gen2-generationNbr");
        this.gen2_crossOverRate = (TextField) popupPane.lookup("#id-popup-gen2-crossOverRate");
        this.gen2_mutationRate = (TextField) popupPane.lookup("#id-popup-gen2-mutationRate");

        this.start = (Button) popupPane.lookup("#id-popup-buttonStart");
    }

    public void setVisible(boolean statut) {
        popupPaneParent.setVisible(statut);
    }

    public void setParameters(int gen1_popSize, int gen1_generationNbr, double gen1_crossOverRate, double gen1_MutationRate, int gen2_popSize, int gen2_generationNbr, double gen2_crossOverRate, double gen2_MutationRate){
        this.gen1_popSize.setText(String.valueOf(gen1_popSize));
        this.gen1_generationNbr.setText(String.valueOf(gen1_generationNbr));
        this.gen1_crossOverRate.setText(String.valueOf(gen1_crossOverRate));
        this.gen1_mutationRate.setText(String.valueOf(gen1_MutationRate));

        this.gen2_popSize.setText(String.valueOf(gen2_popSize));
        this.gen2_generationNbr.setText(String.valueOf(gen2_generationNbr));
        this.gen2_crossOverRate.setText(String.valueOf(gen2_crossOverRate));
        this.gen2_mutationRate.setText(String.valueOf(gen2_MutationRate));
    }

    public Button getStart() {
        return start;
    }
    public int getGen1PopSize() {
        return Integer.parseInt(gen1_popSize.getText());
    }
    public int getGen1GenerationNbr() {return Integer.parseInt(gen1_generationNbr.getText());}
    public double getGen1CrossOverRate() {
        return Double.parseDouble(gen1_crossOverRate.getText());
    }
    public double getGen1MutationRate() {
        return Double.parseDouble(gen1_mutationRate.getText());
    }
    public int getGen2PopSize() {
        return Integer.parseInt(gen2_popSize.getText());
    }
    public int getGen2GenerationNbr() {
        return Integer.parseInt(gen2_generationNbr.getText());
    }
    public double getGen2CrossOverRate() {
        return Double.parseDouble(gen2_crossOverRate.getText());
    }
    public double getGen2MutationRate() {
        return Double.parseDouble(gen2_mutationRate.getText());
    }
}
