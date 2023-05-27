package graphproject.controller;

import graphproject.controller.selection_pane.LinkPane;
import graphproject.controller.selection_pane.NodePane;
import graphproject.controller.selection_pane.SearchPane;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.model.SearchPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SelectionPaneController {

    private final Pane nodeRightPane, linkRightPane, searchPathRightPane;
    private final HBox toolsBar;

    private NodePane nodePane;

    private SearchPane searchPane;

    private LinkPane linkPane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane, HBox toolsBar, Pane centerPane){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        this.toolsBar = toolsBar;

        nodePane = new NodePane(nodeRightPane);
        //nodePane.deleteNodeButtonListener(nodeController);

        searchPane = new SearchPane(searchPathRightPane);

        searchPane.searchFindButtonListener(toolsBar, this);

        linkPane = new LinkPane(linkRightPane, centerPane);
    }

    public void closeSelectionPane() {
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);
    }

    //Node Pane
    public void setNodePane(Node selectedNode){
        nodeRightPane.setVisible(true);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);

        nodePane.setSelectedNode(selectedNode);

        nodePane.textId.setText(Integer.toString(selectedNode.getId()));
        nodePane.textName.setText(selectedNode.getName());
        nodePane.textPosX.setText(Integer.toString(selectedNode.getX()));
        nodePane.textPosY.setText(Integer.toString(selectedNode.getY()));

        nodePane.setChoiceBox(this);
    }
    public NodePane getNodePane(){return this.nodePane;}

    public Pane getSearchPathRightPane(){return this.searchPathRightPane;}

    //Search Pane
    public void setSearchPane(){
        searchPathRightPane.setVisible(true);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
    }

    public void closeSearchPane(){
        searchPathRightPane.setVisible(false);
    }

    //Search function

    public void setSearchNode(Node node){
        searchPane.setSearchNode(node);
    }


    //Link Pane
    public void setLinkPane(Node startNode, Link link, Node endNode){
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(true);
        searchPathRightPane.setVisible(false);

        linkPane.setLinkPane(startNode, link, endNode);
    }

}
