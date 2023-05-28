package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.Place;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeController {

    //private Node node;

    private Graph graph;

    private ContextMenu contextMenu;

    private Node selectedNode;



    private final Pane centerPane;
    private final ToolsController toolsController;

    private final SelectionPaneController selectionPaneController;

    NodeController(Graph graph, Pane centerPane, ToolsController toolsController, SelectionPaneController selectionPaneController){
        this.graph = graph;
        this.centerPane = centerPane;
        this.toolsController = toolsController;
        this.selectionPaneController = selectionPaneController;


        //selectionPaneController.getNodePane().deleteNodeButtonListener(this);

        this.contextMenu = new ContextMenu();

    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    // Add a node when the ToggleButton is true, and we click on the graph
    public void listenerAddNodeToGraph(GraphController graphController) {
        centerPane.setOnMouseClicked(event -> {

            //hide contextMenu
            hideContextMenu();

            if (toolsController.isSelected_createNodesButton() && graph != null) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                Node node = graph.addNode(x,y);



                // Updates the display of Nodes
                updateNode(node);

                // Display the information of the new node
                //Node node = graph.getNodeFromPos(x,y);
                //selectionPaneController.setNodePane(node);
            }
            event.consume();
        });
    }

    public void updateNode(Node node) {

        //Display new nodes
        if (node.getCircle()==null) {
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

            // Add event listener to the node
            listenerNode(circle, node, centerPane);

            // Add the circle to the pane
            node.setCircle(circle);
            centerPane.getChildren().add(circle);
        }

    }

    // Display the information of the node when clicked on it
    public void listenerNode(Circle circle, Node node, Pane centerPane) {

        //fonctions qui sélectionne une node si on clique dessus
        circle.setOnMouseClicked(event -> {

            /*if(toolsController.isSelected_deleteButton()){
                deleteNode(node);
            }
            else{
                // Display the information of the node
                selectionPaneController.setMissionPane(node);
            }*/

            //display the context menu with the list of missions and centers
            updateContextMenu(node, event.getScreenX(), event.getScreenY());

//            if (selectionPaneController.getSearchPathRightPane().isVisible()){
//
//                selectionPaneController.setSearchNode(node);
//            }
//            else if(toolsController.isSelected_deleteButton()){
//                deleteNode(node);
//            }
//            else{
//                // Display the information of the node
//                selectionPaneController.setNodePane(node);
//            }
            event.consume();

        });

        // permet de déplacer les nodes avec la souris
        circle.setOnMouseDragged(event -> {
            // Mise à jour des coordonnées du cercle avec les coordonnées de la souris
            node.setX((int)event.getX());
            node.setY((int)event.getY());
            event.consume();
        });

        // diférencie les nodes lorsque la souris est dessus
        circle.setOnMouseEntered(event -> {

            circle.setStroke(Color.RED); // Changement de couleur de la bordure lors du survol
            event.consume();
        });

        circle.setOnMouseExited(event -> {

            //hide the context menu
//            hideContextMenu();

            if (!node.isSelected()){
                circle.setStroke(Color.BLACK); // Rétablissement de la couleur de la bordure
            }

            event.consume();
        });

        // fonction qui ajoute des links
        circle.setOnMouseReleased(event -> {
            if (toolsController.isSelected_createLinksButton() && graph != null) {
                System.out.println("check");
                //find the red circle
                boolean isRedCircle = false;
                Node linkedNode = null;
                for (Node node2 : graph.getNodes()) {
                    Circle circle2 = node2.getCircle();
                    if (circle2.getFill()==Color.RED) {
                        isRedCircle = true;
                        linkedNode = node2;
                        break;
                    }
                }

                if (isRedCircle) {
                    // Create new link
                    graph.addLink(linkedNode, node);
                    Link.Arrow arrow = Graphics.DesignLineAndArrow(linkedNode, node, 10);
                    centerPane.getChildren().addAll(arrow.line, arrow.arrowHead);
                    Link link = graph.getLinkFromIds(linkedNode, node);
                    link.setOrientedLine(arrow);

                    //Reset Color to node
                    node.getCircle().setFill(Color.WHITE);
                    linkedNode.getCircle().setFill(Color.WHITE);
                } else {
                    node.getCircle().setFill(Color.RED);
                }
            }
        });
    }

    public void deleteNode(Node node){
        //node.deleteAllLinks();

        graph.getNodes().remove(node);

        node.deleteAllLinks(centerPane);

        node.deleteCircle();

        node = null;
    }

    private void updateContextMenu(Node node, double x, double y) {

        // Clear previous Menu Items
        contextMenu.getItems().clear();

        // Create Menu Items related to this node
        for (Place place: node.getListPlace()) {
            MenuItem menuItemPlace = new MenuItem(place.getName());
            menuItemPlace.setOnAction(actionEvent -> {
                System.out.println(place.getName());

                setSelectedNode(node);

                if (place.getType() == Place.Type.MISSION) {
                    selectionPaneController.setMissionPane((Mission)place);
                }
                else if (place.getType() == Place.Type.CENTRE) {
                    selectionPaneController.setCentrePane((Centre)place);
                }

            });

            contextMenu.getItems().addAll(menuItemPlace);
        }

        // Display the menu with all places
        contextMenu.show(centerPane, x, y);
    }

    private void hideContextMenu() {
        contextMenu.getItems().clear();
        contextMenu.hide();
    }


    public void setSelectedNode(Node selectedNode){

        if (this.selectedNode != null){
            this.selectedNode.getCircle().setScaleX(1);
            this.selectedNode.getCircle().setScaleY(1);
            this.selectedNode.getCircle().setStroke(Color.BLACK);
            this.selectedNode.getCircle().setStrokeWidth(1);
            this.selectedNode.setSelection(false);
        }

        //nodePane.lastSelectedNode = selectedNode;
        selectedNode.getCircle().setScaleX(1.1);
        selectedNode.getCircle().setScaleY(1.1);
        selectedNode.getCircle().setStroke(Color.RED);
        selectedNode.getCircle().setStrokeWidth(2);

        selectedNode.setSelection(true);

        this.selectedNode = selectedNode;
    }
}
