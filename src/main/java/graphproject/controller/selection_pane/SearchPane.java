package graphproject.controller.selection_pane;

import graphproject.controller.SelectionPaneController;
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

public class SearchPane{
    public Node startNode;
    public Node endNode;

    public List<Node> listVisitedNode;
    public TextField textStartNode;
    public TextField textEndNode;
    public Label normDistance;
    public Label pathDistance;
    public Label pathFoundText;
    public Button findButton;

    public Button resetButton;
    public ChoiceBox<String> pathFoundChoice;
    public SearchPath searchPath;

    private ChangeListener<Number> choicePathFoundListener;

    public SearchPane(Pane searchPathRightPane){

        listVisitedNode = new ArrayList<>(0);

        searchPath = new SearchPath();

        textStartNode = (TextField) searchPathRightPane.lookup("#name-start-node");

        textEndNode = (TextField) searchPathRightPane.lookup("#name-end-node");

        normDistance = (Label) searchPathRightPane.lookup("#text-norm-distance");

        pathDistance = (Label) searchPathRightPane.lookup("#text-path-distance");

        pathFoundText = (Label) searchPathRightPane.lookup("#text-path-found");

        pathFoundChoice = (ChoiceBox<String>) searchPathRightPane.lookup("#path-found-choice");

        resetButton = (Button) searchPathRightPane.lookup("#reset-path-button");

        //setResetButtonListener();

        findButton = (Button) searchPathRightPane.lookup("#find-path-button");

        this.choicePathFoundListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
    }

    //Listener

    public void searchFindButtonListener(HBox toolsBar, SelectionPaneController selectionPaneController){
        this.findButton.setOnMouseClicked(e ->
        {
            List<Node> listNodePath = new ArrayList<>(0);

            //Reinitialisation du chemin
            for (Node node : listVisitedNode){
                //System.out.println("node");
                if (node.getCircle().getFill() != Color.WHITE){

                    node.getCircle().setFill(Color.WHITE);
                }
            }
            startNode.getCircle().setFill(Color.GREEN);
            noPathSelected();

            //Calcul du chemin entre startNode et endNode
            float distance = this.searchPath.searchPath(this.startNode, this.endNode, listNodePath, listVisitedNode);

            if ( distance == 0){
                //Pas de chemin trouvé
                this.pathFoundText.setVisible(true);
                this.pathFoundText.setText("No path found");
                this.pathFoundChoice.setVisible(false);
            }
            else{
                this.pathDistance.setVisible(true);
                this.pathDistance.setText("Path dist : "+ (int)distance);

                this.pathFoundText.setVisible(true);
                this.pathFoundText.setText("Path found :");

                this.pathFoundChoice.setVisible(true);

                pathFoundChoice.getSelectionModel().selectedIndexProperty().removeListener(choicePathFoundListener);

                for (Node node : listNodePath){

                    this.pathFoundChoice.getItems().add(node.getName());
                }

                choicePathFoundListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if (new_val.intValue() >= 0 && new_val.intValue() < listNodePath.size()) {
                        ToggleButton searchPathButton = (ToggleButton) toolsBar.lookup("#id-toolBar-searchPath");
                        searchPathButton.setSelected(false);

                        Node selectedNode = listNodePath.get(new_val.intValue());
                        //selectionPaneController.setNodePane(selectedNode);
                    }
                };
                this.pathFoundChoice.getSelectionModel().selectedIndexProperty().addListener(choicePathFoundListener);
            }
        });
        resetButton.setOnMouseClicked(e ->{

            for (Node node : listVisitedNode){
                //System.out.println("node");
                if (node.getCircle().getFill() != Color.WHITE){

                    node.getCircle().setFill(Color.WHITE);
                }
            }
            listVisitedNode.clear();
            noPathSelected();
            this.normDistance.setText("Norm dist: ");
            if (startNode != null){
                deselectStartNode();
            }
            if(endNode != null){
                deselectEndNode();
            }
            this.resetButton.setDisable(true);
        });
    }


    public void noPathSelected(){

        if (!pathFoundChoice.getItems().isEmpty()){
            pathFoundChoice.getItems().clear();
        }
        //this.pathDistance.setText("Path dist: ");
        this.pathDistance.setVisible(false);

        this.pathFoundText.setVisible(false);
        this.pathFoundChoice.setVisible(false);
    }


    public void setNodeStart(Node startNode){
        this.startNode = startNode;
        startNode.getCircle().setFill(Color.GREEN);
        this.textStartNode.setText(startNode.getName());
    }

    public void setNodeEnd(Node endNode){
        this.endNode = endNode;
        endNode.getCircle().setFill(Color.BLUE);
        this.textEndNode.setText(endNode.getName());
    }

    public void deselectStartNode(){
        this.startNode.getCircle().setFill(Color.WHITE);
        this.startNode = null;
        this.textStartNode.setText("");
    }

    public void deselectEndNode(){
        this.endNode.getCircle().setFill(Color.WHITE);
        this.endNode = null;
        this.textEndNode.setText("");
    }

    public void setSearchNode(Node node){
        this.resetButton.setDisable(false);
        if (this.startNode == null){
            if( this.endNode != node){
                setNodeStart(node);
            }
            else {
                deselectEndNode();
            }
        }
        else if (this.startNode == node){
            deselectStartNode();
        }
        else{
            if (this.endNode == null){
                setNodeEnd(node);
            }
            else if (this.endNode == node){

                deselectEndNode();
            }
            else{
                deselectEndNode();
                setNodeEnd(node);
            }
        }
        if (this.startNode != null && this.endNode != null){
            this.normDistance.setText("Norm dist: "+ (int)this.searchPath.normeVect(
                    this.startNode.getX(), this.startNode.getY(),
                    this.endNode.getX(), this.endNode.getY()));

            this.findButton.setDisable(false);

        }
        else{
            this.normDistance.setText("Norm dist: ");
            this.findButton.setDisable(true);

            if (this.startNode == null && this.endNode == null && this.listVisitedNode.isEmpty()){
                this.resetButton.setDisable(true);
            }
            //noPathSelected();
        }
    }

}
