package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;


//Permet de modifier un graphe
public class GraphController {

    // Graphic Attributes of the graph


    private  Pane centerPane;
    private Pane parentCenterPane;

    private HBox toolsBar;

    private Pane nodeRightPane;
    private Pane linkRightPane;

    private ContextMenu contextMenu;
//    private Pane searchPathRightPane;

//    private MenuItem buttonSaveGraph;

    //tools
    private SessadGestionController sessadGestionController;
    private SelectionPaneController selectionPaneController;
    private NodeController nodeController;

    private ToolsController toolsController;

    // App attribute
    private Graph graph;

    private Label graphTitle;

    private Label zoomText;

    // Global variables

    private double initialX;

    private double initialY;

    GraphController(){};

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane missionRightPane, Pane itineraryRightPane, Label graphTitle, Pane centreRightPane, HBox toolsBar, Pane parentCenterPane, Label zoomText) {

        this.graph = null;
        this.contextMenu = new ContextMenu();

        // Graphic elements of the scene

        this.centerPane = pane;
        this.nodeRightPane = missionRightPane;
        this.linkRightPane = itineraryRightPane;
//        this.searchPathRightPane = searchPathRightPane;
        this.graphTitle = graphTitle;
        this.toolsBar = toolsBar;
        this.parentCenterPane = parentCenterPane;
        this.zoomText = zoomText;
//        this.buttonSaveGraph = buttonSaveGraph;

        // tools

        this.selectionPaneController = new SelectionPaneController(missionRightPane, itineraryRightPane, centreRightPane, toolsBar, centerPane);

        this.nodeController = new NodeController(graph, centerPane, contextMenu, selectionPaneController);

        this.toolsController = new ToolsController(toolsBar, selectionPaneController);

        // Initializing Graphic Rendering

        initializeCenterPaneSettings();

        // All initialize listeners

        listenerContextMenu();
        listenerZoomGraph();
        listenerMoveOnGraph();
        listenerCoordinateOnMousePressed();
        listenerTest();
//        listenerSaveGraph();

        // All global variables

        initialX = 0;
        initialY = 0;

    }

    public void setGraph(Graph graph) {

        this.graph = graph;
        this.sessadGestionController = new SessadGestionController(graph);
    }

    public void clearGraph() {
        centerPane.getChildren().clear();
    }

    public boolean graphIsNull() {
        return graph == null;
    }



    public void closeGraph() {

        // Deselecting current / opened graph
        graph = null;

        // Clear graphic visual
        clearGraph();

        //Close Right Sidebar
        selectionPaneController.closeSelectionPane();
    }
    public void listenerTest(){

        toolsController.getTest().setOnMouseClicked(event->{
            if (graph != null) {
                sessadGestionController.getSessadGestion().getResolution().startGeneticAlgo();
                //graph.setLink();
                displayGraph();
                List<Employee> employees = sessadGestionController.getSessadGestion().getListEmployee();
                for (Employee employee : employees) {
                    List<Mission> missions = employee.getListMission();
                    for (Mission mission : missions) {
                        System.out.println("Centre : " + employee.getCentre().getId() + ",Employee : " + employee.getId() + ",day " + mission.getDay() + ",mission : " + mission.getName());
                    }
                }
            }
        });
    }

    // Hide Context Menu when clicking outside a node
    public void listenerContextMenu() {
        centerPane.setOnMouseClicked(event -> {

            //hide contextMenu
            hideContextMenu();

            event.consume();
        });
    }

    private void listenerZoomGraph() {
        centerPane.setOnScroll(event -> {

            double zoomFactor;

            if (event.getDeltaY() > 0 ) {
                zoomFactor = 0.1;
            } else {
                zoomFactor = -0.1;
            }

            double translateX = centerPane.getTranslateX();
            double translateY = centerPane.getTranslateY();

            double newScaleX = centerPane.getScaleX() + zoomFactor;
            double newScaleY = centerPane.getScaleX() + zoomFactor;

            double dX;
            double dY;

            if (event.getDeltaY() > 0 ) {
                dX = - ((event.getX() - (centerPane.getWidth()/2)) * (1 - centerPane.getScaleX() / newScaleX));
                dY = - ((event.getY() - (centerPane.getHeight()/2)) * (1 - centerPane.getScaleY() / newScaleY));
            } else {
                dX = (event.getX() - (centerPane.getWidth()/2)) * (centerPane.getScaleX() / newScaleX - 1);
                dY = (event.getY() - (centerPane.getHeight()/2)) * (centerPane.getScaleY() / newScaleY - 1);
            }

            newScaleX = (double)Math.round(newScaleX * 10) / 10;
            newScaleY = (double)Math.round(newScaleY * 10) / 10;

            if (newScaleX >= 0.099 && newScaleY >= 0.099) {

                centerPane.setScaleX(newScaleX);
                centerPane.setScaleY(newScaleY);
                zoomText.setText("ZOOM : " + (int)(newScaleX*100) + " %");

                centerPane.setTranslateX(translateX+dX*(centerPane.getBoundsInParent().getWidth()/8000));
                centerPane.setTranslateY(translateY+dY*(centerPane.getBoundsInParent().getHeight()/6240));

                double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
                double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
                double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
                double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

                if (borderLeft > 0) {
                    centerPane.setTranslateX(centerPane.getTranslateX() - borderLeft);
                } else if (borderRight < 0) {
                    centerPane.setTranslateX(centerPane.getTranslateX() - borderRight);
                }

                if (borderTop > 0) {
                    centerPane.setTranslateY(centerPane.getTranslateY() - borderTop);
                } else if (borderBottom < 0) {
                    centerPane.setTranslateY(centerPane.getTranslateY() - borderBottom);
                }
            }

        });
    }

    private void listenerCoordinateOnMousePressed() {
        centerPane.setOnMousePressed(event -> {
            
            initialX = event.getX();
            initialY = event.getY();
        });
    }

    private void listenerMoveOnGraph() {
        centerPane.setOnMouseDragged(event -> {

            double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
            double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
            double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
            double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

            double dX = (event.getX() - initialX) * (centerPane.getBoundsInParent().getWidth()/8000);
            double dY = (event.getY() - initialY) * (centerPane.getBoundsInParent().getHeight()/6240);

            if (dX < 0) {
                if (borderRight < 0) {
                    System.out.println("out!!!! right");
                } else  {
                    centerPane.setTranslateX(centerPane.getTranslateX() + dX);
                }
            } else {
                if (borderLeft > 0) {
                    System.out.println("out!!!! left");
                } else  {
                    centerPane.setTranslateX(centerPane.getTranslateX() + dX);
                }
            }

            if (dY < 0) {
                if (borderBottom < 0) {
                    System.out.println("out!!!! bottom");
                } else  {
                    centerPane.setTranslateY(centerPane.getTranslateY() + dY);
                }
            } else {
                if (borderTop > 0) {
                    System.out.println("out!!!! top");
                } else  {
                    centerPane.setTranslateY(centerPane.getTranslateY() + dY);
                }
            }

        });
    }

    //Listener link
    public void listenerLink(Node node, Link link){

        Node linkedNode = link.getNode();

        link.getLine().setOnMouseClicked(event ->{
            link.setSelection(true);
            selectionPaneController.setLinkPane(node, link, linkedNode);
            event.consume();
//            if (toolsController.isSelected_optToggleButton()){
//                link.deleteLink(node, centerPane);
//            }
//            else{
//                link.setSelection(true);
//                selectionPaneController.setLinkPane(node, link, linkedNode);
//                event.consume();
//            }
        });
        link.getLine().setOnMouseEntered(event ->{
            link.getLine().setStroke(Color.RED);
            link.getArrowHead().setFill(Color.RED);
            event.consume();
        });
        link.getLine().setOnMouseExited(event ->{
            if (!link.isSelected()){
                link.getLine().setStroke(Color.BLACK);
                link.getArrowHead().setFill(Color.BLACK);
            }
            event.consume();
        });
    }

    public void openGraph(Graph openedGraph){
        closeGraph();
        setGraph(openedGraph);
        displayGraph();

        centerPane.setTranslateX(0);
        centerPane.setTranslateY(0);

        graphTitle.setText(openedGraph.getName());
        nodeController.setGraph(graph);
    }

    // Display graph on the graphic window
    public void displayGraph() {

        updateAllNodes();

        updateLinks();

    }

    public void updateAllNodes() {
        //Positionne les nodes
        for (Node node: graph.getNodes()) {

            // Crée un cercle avec un rayon de 10 pixels
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);
            sessadGestionController.setNodeColor(circle, node);

            // Add event listener to the node
            nodeController.listenerNode(circle, node, centerPane);

            // Add the circle to the pane
            node.setCircle(circle);
            centerPane.getChildren().add(circle);
        }
    }

    public void updateLinks() {
        for (Node node: graph.getNodes()) {

            for (Link link: node.getLinks()){

                Node linkedNode = link.getNode();

                Link.Arrow arrow = Graphics.DesignLineAndArrow(node, linkedNode, 10);

                if (link.getColor() != null){
                    arrow.line.setStroke(link.getColor());
                    arrow.arrowHead.setFill(link.getColor());
                }

                centerPane.getChildren().addAll(arrow.line, arrow.arrowHead);

                link.setOrientedLine(arrow);

                //Update listener of link
                listenerLink(node, link);

            }
        }
    }

    private void initializeCenterPaneSettings() {

        double width = 8000.0;
        double height = 6240.0;

        double layoutX = - (width - (width/10) ) / 2;
        double layoutY = - (height - (height/10) ) / 2;

        // Initialize zoom to 1.0
        centerPane.setScaleX(1.0);
        centerPane.setScaleY(1.0);

        // Initialize max length and width of centerPane
        centerPane.setPrefSize(width, height);

        // Center the centerPane
        centerPane.setLayoutX(layoutX);
        centerPane.setLayoutY(layoutY);

        System.out.println("Display parameters :");
        System.out.println(" - Origin of graph (coord 0,0) : Top Left Corner");
        System.out.println(" - Max dimension of graph : 8000 px x 6240 px");
        System.out.println(" - Spawning point : center of graph (coord 4000,3120)");
        System.out.println("\n");
    }

    private void hideContextMenu() {
        contextMenu.getItems().clear();
        contextMenu.hide();
    }

//    private void listenerSaveGraph() {
//        buttonSaveGraph.setOnAction(actionEvent -> {
//            if (graph == null) {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Warning");
//                alert.setHeaderText("No graph to save");
//                alert.setContentText("Create or open a graph before saving it");
//                alert.showAndWait();
//            }
//            else {
//                graph.saveGraph();
//            }
//        });
//
//    }
}
