package graphproject.model;

import graphproject.controller.GraphController;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.Place;
import graphproject.model.sessad.SessadGestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

public class App {

    private SessadGestion sessadGestion;

    private GraphController graphController;
    private List<Graph> graphs;

    private ChoiceBox<String> choiceBoxDay;
    private ChangeListener<Number> choiceBoxDayListener;

    public App(GraphController graphController, Pane parentCenterPane) {

        this.graphController = graphController;

        graphs = new ArrayList<>(0);

        choiceBoxDay = (ChoiceBox<String>) parentCenterPane.lookup("#graph-day-selection");

        if (choiceBoxDay == null) {
            System.out.println("ChoiceBox not found");
        } else {
            System.out.println("ChoiceBox found");
        }

        this.choiceBoxDayListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
    }

    public void createNewInstance(int idInstance) {

        graphs.clear();

        //TODO : Marius
        System.out.println("Creating and opening Instance : " + idInstance);

        List<Node> listNode = new ArrayList<>();


        SessadGestion sessadGestion = new SessadGestion(idInstance, listNode);

        generateGraphsFromSessadGestion(listNode);

        graphController.openGraph(graphs.get(0));
    }

    private void generateAllGraphsFromInstances() {
        System.out.println("7 instances loaded from files : ");
        for (int idInstance = 1; idInstance < 7 ; idInstance++) {

            String instanceName = mapInstance.get(idInstance);
            System.out.println(" - " + instanceName);
            Graph graph = new Graph(instanceName, idInstance);
            graphs.add(graph);
        }
    }

    public void generateGraphsFromSessadGestion(List<Node> listNode){

        //Nettoyage choiceBox
        this.choiceBoxDay.getItems().clear();
        choiceBoxDay.getSelectionModel().selectedIndexProperty().removeListener(choiceBoxDayListener);

        //Création graph global contenant toutes les nodes
        Graph initialGraph = new Graph("Global", 0);
        for (Node node : listNode){
            initialGraph.addNode(node);
        }
        graphs.add(initialGraph);
        this.choiceBoxDay.getItems().add("global");

        int max_day = 7;



        //Initialisation des graphs de chaque jours

        for (int i = 1; i < max_day; i++){
            Graph graph = new Graph("day"+i, i );
            graphs.add(graph);
            this.choiceBoxDay.getItems().add("day"+i);
        }


        for (Node node : listNode){

            //Parcours des jours
            for (int day = 1; day < max_day; day++){

                Node newNode = new Node(node.id, node.name, node.x, node.y);

                boolean isNodeInDay = false;

                //Parcours des différents événements de la node
                for(Place place : node.getListPlace()){

                    if (place.getType() == Place.Type.CENTRE){

                        newNode.listPlace.add(place);
                        newNode.setCentre(true);
                        isNodeInDay = true;
                    }
                    else{
                        Mission mission = (Mission)place;

                        if (mission.getDay() == day){
                            newNode.listPlace.add(place);
                            isNodeInDay = true;
                        }
                    }
                }
                if (isNodeInDay){
                    graphs.get(day).addNode(newNode);
                }

            }
        }

        //Change de graph en fonction du jour sélectionné
        choiceBoxDayListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < graphs.size()) {

                System.out.println("Graph changed to : " + graphs.get(new_val.intValue()).getName());
                graphController.openGraph(graphs.get(new_val.intValue()));
                //selectionPaneController.setNodePane(selectedNode);
            }
        };

        choiceBoxDay.getSelectionModel().selectedIndexProperty().addListener(choiceBoxDayListener);
    }
//    private void generateAllGraphsFromInstances() {
//        System.out.println("7 instances loaded from files : ");
//        for (int idInstance = 1; idInstance < 7 ; idInstance++) {
//
//            String instanceName = mapInstance.get(idInstance);
//            System.out.println(" - " + instanceName);
//            Graph graph = new Graph(instanceName, idInstance);
//            graphs.add(graph);
//        }
//    }
}
