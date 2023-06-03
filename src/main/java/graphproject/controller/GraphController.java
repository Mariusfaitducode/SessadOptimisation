package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.model.sessad.Mission;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


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
    private SelectionPaneController selectionPaneController;
    private NodeController nodeController;

    private NavigationController navigationController;

    // App attribute
    private Graph graph;

    private Label graphTitle;

//    private Label zoomText;

    // Global variables

//    private double initialX;
//
//    private double initialY;

    GraphController(){};

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane missionRightPane, Pane itineraryRightPane, Label graphTitle, Pane centreRightPane, HBox toolsBar, Pane parentCenterPane, Label zoomText) {

        this.graph = null;
        this.contextMenu = new ContextMenu();

        // Graphic elements of the scene

        this.centerPane = pane;
        this.nodeRightPane = missionRightPane;
        this.linkRightPane = itineraryRightPane;
        this.graphTitle = graphTitle;
        this.toolsBar = toolsBar;
        this.parentCenterPane = parentCenterPane;


        // tools

        this.selectionPaneController = new SelectionPaneController(missionRightPane, itineraryRightPane, centreRightPane, toolsBar, centerPane);

        this.nodeController = new NodeController(graph, centerPane, contextMenu, selectionPaneController);

        this.navigationController = new NavigationController(centerPane, zoomText);

        // Initializing Graphic Rendering

        initializeCenterPaneSettings();

        // All initialize listeners

        listenerContextMenu();

    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {return graph;}

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


    // Hide Context Menu when clicking outside a node
    public void listenerContextMenu() {
        centerPane.setOnMouseClicked(event -> {

            //hide contextMenu
            hideContextMenu();

            event.consume();
        });
    }

    public void openGraph(Graph openedGraph) {
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
            graph.setNodeColor(circle, node);

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
                //listenerLink(node, link);

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
