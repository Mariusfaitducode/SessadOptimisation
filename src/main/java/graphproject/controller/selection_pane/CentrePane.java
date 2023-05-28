package graphproject.controller.selection_pane;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CentrePane {

    public Centre selectedCentre;
    public Label textId;

    public Label textName;
    public Label textPosX;
    public Label textPosY;

    public ChoiceBox<String> choiceBoxEmployee;

    public ChangeListener<Number> choiceBoxEmployeeListener;

    public CentrePane(Pane centreRightPane){
        //Initialisation du champ de texte id
        this.textId = (Label) centreRightPane.lookup("#centre-id");

        //Initialisation du champ de texte name
        this.textName = (Label) centreRightPane.lookup("#centre-name");

        //Initialisation du champ de texte position X
        this.textPosX = (Label) centreRightPane.lookup("#centre-posX");

        //Initialisation du champ de texte position X
        this.textPosY = (Label) centreRightPane.lookup("#centre-posY");

        this.choiceBoxEmployee = (ChoiceBox<String>) centreRightPane.lookup("#centre-employee");

        this.choiceBoxEmployeeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
    }

    public void setSelectedCentre(Centre selectedCentre){
        this.selectedCentre = selectedCentre;

        this.textId.setText("Id : " + selectedCentre.getId());
        //this.textName.setText(selectedCentre.getName());
        this.textPosX.setText("X : " + (int)selectedCentre.getX());
        this.textPosY.setText("Y : " + (int)selectedCentre.getY());

        choiceBoxEmployee.getSelectionModel().selectedIndexProperty().removeListener(choiceBoxEmployeeListener);

        for (Employee employee : selectedCentre.getListEmployee()){

            this.choiceBoxEmployee.getItems().add("Employé : "+employee.getId());
        }

        /*choiceBoxEmployeeListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < selectedCentre.getListEmployee().size()) {

                Node selectedNode = listNodePath.get(new_val.intValue());
                //selectionPaneController.setNodePane(selectedNode);
            }
        };*/
    }
}
