package graphproject.controller.selection_pane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CentralPane {

    public ChoiceBox<String> choiceBoxDay;
    public ChoiceBox<String> choiceBoxStep;
    public ChangeListener<Number> choiceBoxDayListener;
    public ChangeListener<Number> choiceBoxStepListener;
    public Label textAffectation;
    public Label textCost;
    public Label textSpecialty;
    public CentralPane(Pane parentCenterPane){
        choiceBoxDay = (ChoiceBox<String>) parentCenterPane.lookup("#graph-day-selection");
        choiceBoxStep = (ChoiceBox<String>) parentCenterPane.lookup("#graph-step-selection");
        textAffectation = (Label) parentCenterPane.lookup("#graph-affectation");
        textCost = (Label) parentCenterPane.lookup("#graph-cost");
        textSpecialty = (Label) parentCenterPane.lookup("#graph-specialty");

        this.choiceBoxDayListener = (observableValue, number, t1) -> {};
        this.choiceBoxStepListener = ((observableValue, number, t1) -> {});
    }

    public void setLabel(int affectation, float cost, int specialty){
        textAffectation.setText("Affectation : "+affectation);
        textCost.setText("Cout : "+ cost);
        textSpecialty.setText("Spécialités :"+ specialty);
    }

    public void setChoiceBoxStep() {
        choiceBoxStep.getItems().clear();
        choiceBoxStep.getSelectionModel().selectedIndexProperty().removeListener(choiceBoxStepListener);
        for (int step = 1 ; step < 4 ; step++) {
            choiceBoxStep.getItems().add("Step "+step);
        }
    }
}
