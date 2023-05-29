package graphproject.controller;

import graphproject.model.Node;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.SessadGestion;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SessadGestionController {
    SessadGestion sessadGestion;

    ToolsController toolsController;

    public SessadGestionController(SessadGestion sessadGestion, SelectionPaneController selectionPaneController, HBox toolsBar) {
        this.toolsController = new ToolsController(toolsBar, selectionPaneController);
        this.sessadGestion = sessadGestion;
    }

    public void setNodeColor(Circle circle, Node node) {
        if (node.isCentre()){
            switch (node.getIdCentre()){
                case 1:
                    circle.setFill(Color.BLUE);
                    break;
                case 2:
                    circle.setFill(Color.ORANGE);
                    break;
                case 3:
                    circle.setFill(Color.GREEN);
                    break;
            }
        }
        else{
            Mission mission = (Mission) node.getListPlace().get(0);

            if (mission.getEmployee() != null){

                switch (mission.getEmployee().getCentre().getId()){
                    case 1:
                        circle.setFill(Color.rgb(0,128,255));
                        break;
                    case 2:
                        circle.setFill(Color.rgb(255,178,102));
                        break;
                    case 3:
                        circle.setFill(Color.rgb(128,255,0));
                        break;
                }
            }
        }
    }
}
