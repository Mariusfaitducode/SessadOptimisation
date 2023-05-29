package graphproject.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ToolsController {

    private final Button test;
    private final ToggleButton geneticToggleButton;
    private final ToggleButton tabouToggleButton;

    private final ToggleButton optToggleButton;

    private final SelectionPaneController selectionPaneController;

    ToolsController(HBox toolsBarPane, SelectionPaneController selectionPaneController) {
        this.test = (Button) toolsBarPane.lookup("#id-toolsBar-test");
        this.geneticToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-genetic");
        this.tabouToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-tabou");
        this.optToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-2-opt");

        this.selectionPaneController = selectionPaneController;

//        listenerNodesButton();
//        listenerLinksButton();
//          listenerPathButton();
//        listenerDeleteButton();
    }

    public boolean isSelected_geneticToggleButton() {
        return geneticToggleButton.isSelected();
    }

    public boolean isSelected_tabouToggleButton() {
        return tabouToggleButton.isSelected();
    }

    public boolean isSelected_optToggleButton() {
        return optToggleButton.isSelected();
    }

//    public void listenerNodesButton() {
//        //initialize the color of the button
//        //createNodesButton.setStyle("-fx-background-color: #222;");
//
//        // Add listeners
//        createNodesButton.setOnMouseClicked(event -> {
//            setSelectedToggleButtons(createNodesButton, searchPathButton, createLinksButton, deleteButton);
//        });
//    }

//    public void listenerLinksButton() {
//        //initialize the color of the button
//        //createLinksButton.setStyle("-fx-background-color: #222;");
//
//        // Add listeners
//        createLinksButton.setOnMouseClicked(event2 -> {
//            setSelectedToggleButtons(createLinksButton, searchPathButton,  createNodesButton, deleteButton);
//        });
//    }

//    public void listenerPathButton() {
//        //initialize the color of the button
//        //searchPathButton.setStyle("-fx-background-color: #222;");
//
//        // Add listeners
//        /*searchPathButton.setOnMouseClicked(event3 -> {
//            setSelectedToggleButtons(searchPathButton, createLinksButton, createNodesButton, deleteButton);
//            if (searchPathButton.isSelected()){
//                selectionPaneController.setSearchPane();
//            }
//            else{
//                selectionPaneController.closeSearchPane();
//            }
//        });*/
//    }

//    public void listenerDeleteButton(){
//
//        deleteButton.setOnMouseClicked(event4 ->{
//            setSelectedToggleButtons(deleteButton, createNodesButton, createLinksButton, searchPathButton);
//        });
//    }

//    public void setSelectedToggleButtons(ToggleButton t1, ToggleButton t2, ToggleButton t3, ToggleButton t4){
//        if (t1.isSelected()) {
//            t1.setStyle("-fx-background-color: red;");
//            t2.setSelected(false);
//            t2.setStyle("-fx-background-color: #222;");
//            t3.setSelected(false);
//            t3.setStyle("-fx-background-color: #222;");
//            t4.setSelected(false);
//            t4.setStyle("-fx-background-color: #222;");
//        } else {
//            t1.setStyle("-fx-background-color: #222;");
//        }
//        //selectionPaneController.closeSearchPane();
//    }
}
