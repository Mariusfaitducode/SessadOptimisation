package graphproject.controller.selection_pane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CentralPane {

    public ChoiceBox<String> choiceBoxDay;
    public ChangeListener<Number> choiceBoxDayListener;

    public Label textAffectation;
    public Label textCost;
    public Label textSpecialty;
    public CentralPane(Pane parentCenterPane){
        choiceBoxDay = (ChoiceBox<String>) parentCenterPane.lookup("#graph-day-selection");
        textAffectation = (Label) parentCenterPane.lookup("#graph-affectation");
        textCost = (Label) parentCenterPane.lookup("#graph-cost");
        textSpecialty = (Label) parentCenterPane.lookup("#graph-specialty");

        this.choiceBoxDayListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
    }

    public void setLabel(int affectation, float cost, int specialty){
        textAffectation.setText("Affectation : "+affectation);
        textCost.setText("Cout : "+ cost);
        textSpecialty.setText("Spécialités :"+ specialty);
    }
}
