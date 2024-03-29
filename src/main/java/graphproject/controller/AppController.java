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


    // attribut app qui stocke toutes les données de l'application

    private App app;

    //Tout ce qui contient les actions

    private GraphController graphController;



    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        popupPane.setVisible(false);
        missionRightPane.setVisible(true);
        itineraryRightPane.setVisible(false);
        //searchPathRightPane.setVisible(false);

        graphController = new GraphController(centerPane, missionRightPane, itineraryRightPane, graphTitle, centreRightPane, toolsBar, parentCenterPane, zoomText);

        app = new App(graphController, parentCenterPane, toolsBar, popupPane);
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
}