package graphproject.controller.selection_pane;

import graphproject.controller.NodeController;
import graphproject.controller.SelectionPaneController;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class NodePane{
    public Node selectedNode;
    public TextField textId;
    public TextField textName;
    public TextField textPosX;
    public TextField textPosY;
    public ChoiceBox<String> goingLinks;
    public ChoiceBox<String> comingLinks;

    private ChangeListener<Number> choiceGoingBoxListener;
    private ChangeListener<Number> choiceComingBoxListener;

    public Button deleteNodeButton;


    public NodePane(Pane nodeRightPane){
        //Initialisation du champ de texte id
        this.textId = (TextField) nodeRightPane.lookup("#id-node-id");
        this.textId.setOnKeyTyped(e ->{
            if (!textId.getText().isEmpty()){
                this.selectedNode.setId(Integer.parseInt(textId.getText()));
            }
        });
        //Initialisation du champ de texte name
        this.textName = (TextField) nodeRightPane.lookup("#id-node-name");
        this.textName.setOnKeyTyped(e ->{
            if (!textName.getText().isEmpty()){
                this.selectedNode.setName(textName.getText());
            }
        });
        //Initialisation du champ de texte position X
        this.textPosX = (TextField) nodeRightPane.lookup("#id-node-posX");
        this.textPosX.setOnKeyTyped(e ->{
            if (!textPosX.getText().isEmpty()){
                this.selectedNode.setX(Integer.parseInt(textPosX.getText()));
            }
        });
        //Initialisation du champ de texte position X
        this.textPosY = (TextField) nodeRightPane.lookup("#id-node-posY");
        this.textPosY.setOnKeyTyped(e ->{
            if (!textPosY.getText().isEmpty()){
                this.selectedNode.setY(Integer.parseInt(textPosY.getText()));
            }
        });

        this.goingLinks = (ChoiceBox<String>) nodeRightPane.lookup("#node-going-links");
        this.comingLinks = (ChoiceBox<String>) nodeRightPane.lookup("#node-coming-links");

        this.choiceGoingBoxListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
        this.choiceComingBoxListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };

        this.deleteNodeButton = (Button) nodeRightPane.lookup("#delete-node-button");


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

    public void setChoiceBox(SelectionPaneController selectionPaneController){

        //Going lists

        List<Node> listGoingNode = new ArrayList<>(0);
        if (!goingLinks.getItems().isEmpty()){
            goingLinks.getItems().clear();
        }

        goingLinks.getSelectionModel().selectedIndexProperty().removeListener(choiceGoingBoxListener);

        for (Link link : selectedNode.getLinks()){

            goingLinks.getItems().add(link.getNode().getName());
            listGoingNode.add(link.getNode());
            //this.pathFoundChoice.getItems().add(node.getName());
        }
        choiceGoingBoxListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < listGoingNode.size()) {
                Node linkedNode = listGoingNode.get(new_val.intValue());
                selectionPaneController.setNodePane(linkedNode);
            }
        };
        goingLinks.getSelectionModel().selectedIndexProperty().addListener(choiceGoingBoxListener);

        //Coming list

        List<Node> listComingNode = new ArrayList<>(0);
        if (!comingLinks.getItems().isEmpty()){
            comingLinks.getItems().clear();
        }

        comingLinks.getSelectionModel().selectedIndexProperty().removeListener(choiceComingBoxListener);

        for (Node node : selectedNode.getLinkedNodeList()){

            comingLinks.getItems().add(node.getName());
            listComingNode.add(node);
            //this.pathFoundChoice.getItems().add(node.getName());
        }
        choiceComingBoxListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < listComingNode.size()) {
                Node linkedNode = listComingNode.get(new_val.intValue());
                selectionPaneController.setNodePane(linkedNode);
            }
        };
        comingLinks.getSelectionModel().selectedIndexProperty().addListener(choiceComingBoxListener);
    }

    public void deleteNodeButtonListener(NodeController nodeController){
        this.deleteNodeButton.setOnMouseClicked(e ->{
            nodeController.deleteNode(selectedNode);
        });
    }

}
