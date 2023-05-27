package graphproject.controller.selection_pane;

import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LinkPane {

    public Node startNode;

    public Link selectedLink;
    public Node endNode;

    public Label startNodeID;
    public Label startNodeName;
    public Label endNodeID;
    public Label endNodeName;

    public Button deleteLinkButton;

    public LinkPane(Pane linkRightPane, Pane centerPane){
        startNodeID = (Label) linkRightPane.lookup("#start-node-id");
        startNodeName = (Label) linkRightPane.lookup("#start-node-name");

        endNodeID = (Label) linkRightPane.lookup("#end-node-id");
        endNodeName = (Label) linkRightPane.lookup("#end-node-name");

        deleteLinkButton = (Button) linkRightPane.lookup("#delete-link-button");

        deleteLinkButton.setOnMouseClicked(e ->{
            selectedLink.deleteLink(startNode, centerPane);
        });
    }

    public void setLinkPane(Node startNode, Link link, Node endNode){
        this.startNode = startNode;
        this.endNode = endNode;

        startNodeID.setText(Integer.toString(startNode.getId()));
        startNodeName.setText(startNode.getName());

        endNodeID.setText(Integer.toString(endNode.getId()));
        endNodeName.setText(endNode.getName());

        if (selectedLink != null){
            selectedLink.setSelection(false);
            selectedLink.getLine().setStroke(Color.BLACK);
            selectedLink.getArrowHead().setFill(Color.BLACK);
        }
        selectedLink = link;
        selectedLink.setSelection(true);
        selectedLink.getLine().setStroke(Color.RED);
        selectedLink.getArrowHead().setFill(Color.RED);
    }
}
