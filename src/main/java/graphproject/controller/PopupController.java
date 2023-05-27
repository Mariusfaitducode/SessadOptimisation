package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PopupController {

    // Graphic attributes of the pop-up
    private Pane popupPane;

    private RadioButton rbutton1, rbutton2, rbutton3;

    private TextField nameGraph, nodesNumber;


    // App Attribute
    private App app;

    PopupController(Pane popupPane, RadioButton rbutton1, RadioButton rbutton2, RadioButton rbutton3, TextField nameGraph, TextField nodesNumber, App app){
        this.popupPane = popupPane;
        this.rbutton1 = rbutton1;
        this.rbutton2 = rbutton2;
        this.rbutton3 = rbutton3;
        this.nameGraph = nameGraph;
        this.nodesNumber = nodesNumber;
        this.app = app;
    }

    public void setVisible(boolean statut) {
        popupPane.setVisible(statut);
    }

    public Graph generateGraph(Pane centerPane){

        // Create new Graph
        app.createNewGraph(nameGraph.getText());

        // Update Graph depending on Radio Button:
        // Empty / Random / import existing one
        if (rbutton1.isSelected()) {

            // Empty

        } else if (rbutton2.isSelected()) {

            app.getLastGraph().setRandomNodesAndLinks(Integer.parseInt(nodesNumber.getText()), centerPane);

        } else if (rbutton3.isSelected()) {

            // TODO
        }

        // Hide Creating Graph Pop-up
        popupPane.setVisible(false);

        return app.getLastGraph();
    }
}
