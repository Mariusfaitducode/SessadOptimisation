package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeController {

    //private Node node;

    private Graph graph;

    private final Pane centerPane;
    private final ToolsController toolsController;

    private final SelectionPaneController selectionPaneController;

    NodeController(Graph graph, Pane centerPane, ToolsController toolsController, SelectionPaneController selectionPaneController){
        this.graph = graph;
        this.centerPane = centerPane;
        this.toolsController = toolsController;
        this.selectionPaneController = selectionPaneController;

        selectionPaneController.getNodePane().deleteNodeButtonListener(this);
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    // Add a node when the ToggleButton is true, and we click on the graph
    public void listenerAddNodeToGraph(GraphController graphController) {
        centerPane.setOnMouseClicked(event -> {

            if (toolsController.isSelected_createNodesButton() && graph != null) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                Node node = graph.addNode(x,y);



                // Updates the display of Nodes
                updateNode(node);

                // Display the information of the new node
                //Node node = graph.getNodeFromPos(x,y);
                selectionPaneController.setNodePane(node);
            }
            event.consume();
        });
    }

    public void updateNode(Node node) {

        //Display new nodes
        if (node.getCircle()==null) {
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

            // Add event listener to the node
            listenerNode(circle, node);

            // Add the circle to the pane
            node.setCircle(circle);
            centerPane.getChildren().add(circle);
        }

    }

    // Display the information of the node when clicked on it
    public void listenerNode(Circle circle, Node node) {

        //fonctions qui sélectionne une node si on clique dessus
        circle.setOnMouseClicked(event -> {

            if (selectionPaneController.getSearchPathRightPane().isVisible()){

                selectionPaneController.setSearchNode(node);
            }
            else if(toolsController.isSelected_deleteButton()){
                deleteNode(node);
            }
            else{
                // Display the information of the node
                selectionPaneController.setNodePane(node);
            }
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

}
