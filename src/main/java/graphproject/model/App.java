package graphproject.model;

import graphproject.controller.GraphController;
import graphproject.controller.ToolsController;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.Place;
import graphproject.model.sessad.SessadGestion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

public class App {

    private SessadGestion sessadGestion;

    private GraphController graphController;
    private List<Graph> graphs;

    private ChoiceBox<String> choiceBoxDay;
    private ChangeListener<Number> choiceBoxDayListener;

    private ToolsController toolsController;

    public App(GraphController graphController, Pane parentCenterPane, HBox toolsBar) {

        this.graphController = graphController;

        this.toolsController = new ToolsController(toolsBar);

        graphs = new ArrayList<>(0);

        choiceBoxDay = (ChoiceBox<String>) parentCenterPane.lookup("#graph-day-selection");

        listenerTest(toolsController);

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

    public void listenerTest(ToolsController toolsController){

        toolsController.getTest().setOnMouseClicked(event->{
            if (!graphController.graphIsNull()) {
                sessadGestion.getResolution().startGeneticAlgo();
                setLinks(sessadGestion.getListEmployee(), sessadGestion.getListMission());
                graphController.displayGraph();
//                List<Employee> employees = sessadGestion.getListEmployee();
//                for (Employee employee : employees) {
//                    List<Mission> missions = employee.getListMission();
//                    for (Mission mission : missions) {
//                        System.out.println("Centre : " + employee.getCentre().getId() + ",Employee : " + employee.getId() + ",day " + mission.getDay() + ",mission : " + mission.getName());
//                    }
//                }
            }
        });
    }

    public void setLinks(List<Employee> listEmployee, List<Mission> listMission) {


        int sizeList = listEmployee.size();



        /*for (Employee employee : listEmployee){
            Color colorEmployee = findColorForEmployee(employee.getId(), sizeList);
            Node firstNode = employee.getCentre().getNode();

            for (Mission mission : employee.getListMission()){

                graphs.get(0).addLink(firstNode, mission.getNode(), colorEmployee);
                firstNode = mission.getNode();
            }
        }*/

        for (Employee employee : listEmployee){

            for (int day = 1; day < 6; day++ ){

//                Color colorEmployee = createColorGradientDependingOnDay(employee.getId(), day, sizeList);
                Color colorEmployee = findColorForEmployee(employee.getId(), sizeList);

                Node firstNode = employee.getCentre().getNode();

                for (Mission mission : employee.getListMission(day)){

                    graphs.get(0).addLink(firstNode, mission.getNode(), colorEmployee);

                    graphs.get(day).addLink(firstNode, mission.getNode(), colorEmployee);

                    firstNode = mission.getNode();
                }
                if(!employee.getListMission(day).isEmpty()){
                    graphs.get(0).addLink(firstNode, employee.getCentre().getNode(), colorEmployee);

                    graphs.get(day).addLink(firstNode, employee.getCentre().getNode(), colorEmployee);
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

    public Color createColorGradientDependingOnDay(int idEmployee, int day, int sizeList) {
        day -= 1;
        int totalColor = (255 * 6 + 1);
        double deltaColor = totalColor / (sizeList + 0.4);
        double modifiedDeltaColor =  0.1 * deltaColor;

        int idColor = (int)(deltaColor * idEmployee) + (int)(day * modifiedDeltaColor);
        if (idColor < 0) {
            return Color.rgb(0, 0, 255);
        } else if (idColor < 255) {
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

        int max_day = 6;

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
