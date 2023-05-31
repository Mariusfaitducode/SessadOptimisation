package graphproject.controller;

import graphproject.model.Graph;
import graphproject.model.Node;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.SessadGestion;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SessadGestionController {
    SessadGestion sessadGestion;


    public SessadGestionController(Graph graph) {

        //this.sessadGestion = graph.getSessadGestion();

    }

    public SessadGestion getSessadGestion() {
        return sessadGestion;
    }

//    public void setNodeColor(Circle circle, Node node) {
//        if (node.isCentre()){
//            switch (node.getIdCentre()){
//                case 1:
//                    circle.setFill(Color.BLUE);
//                    break;
//                case 2:
//                    circle.setFill(Color.ORANGE);
//                    break;
//                case 3:
//                    circle.setFill(Color.GREEN);
//                    break;
//            }
//        }
//        else{
//            Mission mission = (Mission) node.getListPlace().get(0);
//
//            if (mission.getEmployee() != null){
//
//                switch (mission.getEmployee().getCentre().getId()){
//                    case 1:
//                        circle.setFill(Color.rgb(0,128,255));
//                        break;
//                    case 2:
//                        circle.setFill(Color.rgb(255,178,102));
//                        break;
//                    case 3:
//                        circle.setFill(Color.rgb(128,255,0));
//                        break;
//                }
//            }
//        }
//    }
    //TODO : set color to link

//    public void setLink() {
//        for (Employee employee : sessadGestion.getListEmployee()){
//
//            Color color;
//
//            if (employee.getCentre().getId() == 1){
//                float nuance = (float)employee.getId() * 1 / sessadGestion.getListEmployee().size();
//
//                color = new Color(0,0, nuance, 1);
//            }
//            else{
//                float nuance = (float)employee.getId() * 1 / sessadGestion.getListEmployee().size();
//                color = new Color(0, nuance, 0, 1);
//            }
//
//            for (int day = 1; day < 6; day++ ){
//
//                Node firstNode = employee.getCentre().getNode();
//
//                int[] index = new int[1];
//
//                for (Mission mission : employee.getListMission(day, index)){
//
//                    graph.addLink(firstNode, mission.getNode(), color);
//                    firstNode = mission.getNode();
//                }
//                graph.addLink(firstNode, employee.getCentre().getNode());
//            }
//
//
//        }
//    }
}
