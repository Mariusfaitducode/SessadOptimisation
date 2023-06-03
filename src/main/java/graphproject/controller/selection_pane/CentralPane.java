package graphproject.controller.selection_pane;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CentralPane {

    public ChoiceBox<String> choiceBoxDay;
    public ChoiceBox<String> choiceBoxStep;
    public ChangeListener<Number> choiceBoxDayListener;
    public ChangeListener<Number> choiceBoxStepListener;
    public Label textAffectation;

    public int[] affectation = new int[4];

    public Label textCost;

    public float[] cost = new float[4];

    public Label textSpecialty;

    public int[] specialty = new int[4];

    public CentralPane(Pane parentCenterPane){
        choiceBoxDay = (ChoiceBox<String>) parentCenterPane.lookup("#graph-day-selection");
        choiceBoxStep = (ChoiceBox<String>) parentCenterPane.lookup("#graph-step-selection");
        textAffectation = (Label) parentCenterPane.lookup("#graph-affectation");
        textCost = (Label) parentCenterPane.lookup("#graph-cost");
        textSpecialty = (Label) parentCenterPane.lookup("#graph-specialty");

        this.choiceBoxDayListener = (observableValue, number, t1) -> {};
        this.choiceBoxStepListener = ((observableValue, number, t1) -> {});
    }

    public void setValueLabel(int affectation, float cost, int specialty, int step){
        this.affectation[step] = affectation;
        this.cost[step] = cost;
        this.specialty[step] = specialty;
    }

    public void setLabel(int step){
        textAffectation.setText("Affectation : "+affectation[step]);
        textCost.setText("Cout : "+ cost[step]);
        textSpecialty.setText("Spécialités :"+ specialty[step]);
    }

    public void setChoiceBoxStep() {
        choiceBoxStep.getItems().clear();
        choiceBoxStep.getSelectionModel().selectedIndexProperty().removeListener(choiceBoxStepListener);
        for (int step = 1 ; step < 5 ; step++) {
            choiceBoxStep.getItems().add("Step "+step);
        }
    }
}
