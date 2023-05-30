package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import graphproject.model.Node;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.SessadGestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController implements Initializable {

    //Tout les attributs de la scène qu'on va modifier

    //id des éléments
    @FXML
    private Pane centerPane, parentCenterPane;
    @FXML
    private Pane  missionRightPane, centreRightPane, itineraryRightPane;

    @FXML
    private HBox toolsBar;

    @FXML
    private Label graphTitle, zoomText;

    //Elements de la barre de menu
    @FXML
    private MenuItem noRecentInstance;

    @FXML
    private Menu openGraphsMenu;

    //id des objets de la popup
    @FXML
    private Pane popupPane;
    @FXML
    private RadioButton rbutton1, rbutton2, rbutton3;
    @FXML
    private TextField nameGraph, nodesNumber;

    // attribut app qui stocke toutes les données de l'application

    private App app;

    //Tout ce qui contient les actions

    private GraphController graphController;

//    private PopupController popupController;



    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        popupPane.setVisible(false);
        missionRightPane.setVisible(true);
        itineraryRightPane.setVisible(false);
        //searchPathRightPane.setVisible(false);

        graphController = new GraphController(centerPane, missionRightPane, itineraryRightPane, graphTitle, centreRightPane, toolsBar, parentCenterPane, zoomText);

        app = new App(graphController, parentCenterPane, toolsBar);
        createInstances();
    }

    public void closeInstance() {
        graphController.closeGraph();
    }

    public void saveInstance() {
        //TODO : Save the result of an experimentation of optimisation
        System.out.println("The system to save results of the optimisation is not made yet.\n");
    }

    public void createInstances(){

        for(int idInstance = 1; idInstance < 7; idInstance++) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(mapInstance.get(idInstance));

            // Ajoute des listermers aux menu instances
            int finalIdInstance = idInstance;
            menuItem.setOnAction(actionEvent -> app.createNewInstance(finalIdInstance));

            //on initialise les sous-menu dans le menu
            openGraphsMenu.getItems().add(menuItem);

        }
    }

    public void testSessad(){

        List<Node> listNode = new ArrayList<>();


        SessadGestion sessadGestion = new SessadGestion(1, listNode);

        //sessadGestion.generatePosition(listNode);


        Graph graph = new Graph("Graph Sessad", 1);


        for (Employee employee : sessadGestion.getListEmployee()){

            Color color;

            if (employee.getCentre().getId() == 1){
                float nuance = (float)employee.getId() * 1 / sessadGestion.getListEmployee().size();

                color = new Color(0,0, nuance, 1);
            }
            else{
                float nuance = (float)employee.getId() * 1 / sessadGestion.getListEmployee().size();
                color = new Color(0, nuance, 0, 1);
            }

            for (int day = 1; day < 6; day++ ){

                Node firstNode = employee.getCentre().getNode();

                for (Mission mission : employee.getListMission(day)){

                    graph.addLink(firstNode, mission.getNode());
                    firstNode = mission.getNode();
                }
                graph.addLink(firstNode, employee.getCentre().getNode());
            }
        }


        int i = 0;

        for (Node node : listNode){
            graph.addNode(node);
        }

        graphController.openGraph(graph);

        /*for (Centre centre : sessadGestion.getListCentre()){
            centre.getCircle().setFill(Color.ORANGE);
        }*/

    }

}