package graphproject.model;

import graphproject.controller.GraphController;
import graphproject.controller.PopupController;
import graphproject.controller.ToolsController;
import graphproject.controller.selection_pane.CentralPane;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.Place;
import graphproject.model.sessad.SessadGestion;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class App {

    private SessadGestion sessadGestion;

    private GraphController graphController;

    private List<List<Graph>> graphs;
    private int selectedStep;
    private int selectedDay;

    private CentralPane centralPane;

    private ToolsController toolsController;
    private PopupController popupController;

    public App(GraphController graphController, Pane parentCenterPane, HBox toolsBar, Pane popupPane) {

        this.graphController = graphController;
        this.toolsController = new ToolsController(toolsBar);
        this.popupController = new PopupController(popupPane);

        graphs = new ArrayList<>(0);

        listenerTest();
        listenerStart();

        centralPane = new CentralPane(parentCenterPane);
    }

    public void listenerTest(){

        toolsController.getTest().setOnMouseClicked(event->{
            if (!graphController.graphIsNull()) {
                popupController.setVisible(true);
                popupController.setParameters(200, 50000, 0.9, 0.9);
            }
        });
    }

    //Lance l'algorithme génétique
    public void listenerStart() {
        popupController.getStart().setOnMouseClicked(event -> {
            if (!graphController.graphIsNull()) {

                // hide popupPane
                popupController.setVisible(false);

                //Step 1 : compute the first step
                sessadGestion.getResolution().startInitiatingGeneticAlgo(popupController.getPopSize());
                centralPane.setLabel(sessadGestion.getResolution().getCentreAffected(), (float)sessadGestion.getResolution().getTravelCost(), sessadGestion.getResolution().getMatchingSpecialty());
                // display the first step
                System.out.println("Display initial step");
                setLinks(0, sessadGestion.getListEmployee());
                graphController.displayGraph();


                // Step 2 : compute the first genetic algorithm
                sessadGestion.getResolution().startGeneticAlgo(popupController.getPopSize(), popupController.getGenerationNbr(), popupController.getCrossOverRate(), popupController.getMutationRate());
                centralPane.setLabel(sessadGestion.getResolution().getCentreAffected(), (float)sessadGestion.getResolution().getTravelCost(), sessadGestion.getResolution().getMatchingSpecialty());
                // display after the first genetic algorithm
                System.out.println("Display first step");
                setLinks(1, sessadGestion.getListEmployee());
                graphController.displayGraph();

                // Step 3 : compute the first genetic algorithm
                sessadGestion.getResolution().secondPartGenetic(popupController.getPopSize(), popupController.getGenerationNbr(), popupController.getCrossOverRate(), popupController.getMutationRate());
                centralPane.setLabel(sessadGestion.getResolution().getCentreAffected(), (float)sessadGestion.getResolution().getTravelCost(), sessadGestion.getResolution().getMatchingSpecialty());
                // display after the first genetic algorithm
                System.out.println("Display second step");
                setLinks(2, sessadGestion.getListEmployee());
                graphController.displayGraph();

            }
        });
    }

    public void setLinks(int step, List<Employee> listEmployee) {


        int sizeList = listEmployee.size();

        for (Employee employee : listEmployee){

            for (int day = 1; day < 6; day++ ){

                Color colorEmployee = findColorForEmployee(employee.getId(), sizeList);

                Node firstNode = employee.getCentre().getNode();

                for (Mission mission : employee.getListMission(day)){

                    graphs.get(step).get(0).addLink(firstNode, mission.getNode(), colorEmployee);

                    graphs.get(step).get(day).addLink(firstNode, mission.getNode(), colorEmployee);

                    firstNode = mission.getNode();
                }
                if(!employee.getListMission(day).isEmpty()){
                    graphs.get(step).get(0).addLink(firstNode, employee.getCentre().getNode(), colorEmployee);

                    graphs.get(step).get(day).addLink(firstNode, employee.getCentre().getNode(), colorEmployee);
                }


            }
        }
    }

    public Color findColorForEmployee(int idEmployee, int sizeList) {
        int totalColor = (255 * 6 + 1);
        int idColor = (totalColor / sizeList) * idEmployee;
        if (idColor < 255) {
            return Color.rgb(0, idColor, 255);
        } else if (idColor < 255 * 2) {
            return Color.rgb(0, 255, 255 - (idColor - 255));
        } else if (idColor < 255 * 3) {
            return Color.rgb(idColor - 255 * 2, 255, 0);
        } else if (idColor < 255 * 4) {
            return Color.rgb(255, 255 - (idColor - 255 * 3), 0);
        } else if (idColor < 255 * 5) {
            return Color.rgb(255, 0, idColor - 255 * 4);
        } else {
            return Color.rgb(255 - (idColor - 255 * 5), 0, 255);
        }
    }

    public void createNewInstance(int idInstance) {

        graphs.clear();

        //TODO : Marius
        System.out.println("Creating and opening Instance : " + idInstance);

        List<Node> listNode = new ArrayList<>();

        this.sessadGestion = new SessadGestion(idInstance, listNode);

        centralPane.setChoiceBoxStep();
        centralPane.choiceBoxStepListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < 3) {

                System.out.println("Graphs changed to step : " + (new_val.intValue() + 1));
                selectedStep = new_val.intValue();
                graphController.openGraph(graphs.get(selectedStep).get(selectedDay));
            }
        };
        centralPane.choiceBoxStep.getSelectionModel().selectedIndexProperty().addListener(centralPane.choiceBoxStepListener);

        for (int i = 0 ; i < 3 ; i++) {
            graphs.add(new ArrayList<>(0));
            generateGraphsFromSessadGestion(listNode, i);
        }

        // When initialising, it opens the global graph of the first step
        //graphController.openGraph(graphs.get(0).get(0));
    }

    public void generateGraphsFromSessadGestion(List<Node> listNode, int step){

        //Création graph global contenant toutes les nodes
        Graph initialGraph = new Graph("Global");
        for (Node node : listNode){
            initialGraph.addNode(node);
        }
        graphs.get(step).add(initialGraph);


        //Nettoyage choiceBox
        centralPane.choiceBoxDay.getItems().clear();
        centralPane.choiceBoxDay.getSelectionModel().selectedIndexProperty().removeListener(centralPane.choiceBoxDayListener);

        centralPane.choiceBoxDay.getItems().add("global");

        int max_day = 6;

        //Initialisation des graphs de chaque jours

        for (int i = 1; i < max_day; i++){
            Graph graph = new Graph("day"+i);
            graphs.get(step).add(graph);
            centralPane.choiceBoxDay.getItems().add("day"+i);
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
                    graphs.get(step).get(day).addNode(newNode);
                }

            }
        }

        //Change de graph en fonction du jour sélectionné
        centralPane.choiceBoxDayListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < graphs.get(step).size()) {

                System.out.println("Graph changed to : " + graphs.get(selectedStep).get(new_val.intValue()).getName());
                selectedDay = new_val.intValue();
                graphController.openGraph(graphs.get(selectedStep).get(selectedDay));


                //selectionPaneController.setNodePane(selectedNode);
            }
        };

        centralPane.choiceBoxDay.getSelectionModel().selectedIndexProperty().addListener(centralPane.choiceBoxDayListener);
    }
}
