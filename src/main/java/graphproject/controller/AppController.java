package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import graphproject.model.Node;
import graphproject.model.sessad.SessadGestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController implements Initializable {

    //Tout les attributs de la scène qu'on va modifier

    //id des éléments
    @FXML
    private Pane centerPane, searchPathRightPane, parentCenterPane;
    @FXML
    private Pane  missionRightPane, centreRightPane, itineraryRightPane;

    @FXML
    private HBox toolsBar;

    @FXML
    private Label graphTitle, zoomText;

    //Elements de la barre de menu
    @FXML
    private MenuItem noRecentGraphMenuItem, buttonSaveGraph;

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

    private PopupController popupController;

    private MenuController menuController;


    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        popupPane.setVisible(false);
        missionRightPane.setVisible(true);
        itineraryRightPane.setVisible(false);
        //searchPathRightPane.setVisible(false);

        graphController = new GraphController(centerPane, missionRightPane, itineraryRightPane, graphTitle, centreRightPane, toolsBar, parentCenterPane, zoomText, buttonSaveGraph);
        menuController = new MenuController(openGraphsMenu, noRecentGraphMenuItem);

        app = new App();
    }

    //Tout ce qui déclenche les actions
    //
    @FXML
    public void createNewGraphPopup() {
        popupController = new PopupController(popupPane, rbutton1, rbutton2, rbutton3, nameGraph, nodesNumber, app);
        popupController.setVisible(true);
    }

    public void generateGraph() {
        graphController.openGraph(popupController.generateGraph(centerPane));
    }

    public void openExistingGraphsItems() {
        menuController.openExistingGraphsItem(app, graphController);
    }

    public void closeGraph() {
        graphController.closeGraph();
    }

    public void testSessad(){

        List<Node> listNode = new ArrayList<>();


        SessadGestion sessadGestion = new SessadGestion(listNode);

        //sessadGestion.generatePosition(listNode);


        Graph graph = new Graph("Graph Sessad");



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