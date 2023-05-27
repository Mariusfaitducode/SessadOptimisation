package graphproject.controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ToolsController {

    private final ToggleButton createNodesButton;
    private final ToggleButton createLinksButton;
    private final ToggleButton searchPathButton;

    private final ToggleButton deleteButton;

    private final SelectionPaneController selectionPaneController;

    ToolsController(HBox toolsBarPane, SelectionPaneController selectionPaneController) {
        this.createNodesButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createNodes");
        this.createLinksButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createLinks");
        this.searchPathButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-searchPath");
        this.deleteButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-delete");

        this.selectionPaneController = selectionPaneController;

        listenerNodesButton();
        listenerLinksButton();
        listenerPathButton();
        listenerDeleteButton();
    }

    public boolean isSelected_createNodesButton() {
        return createNodesButton.isSelected();
    }

    public boolean isSelected_createLinksButton() {
        return createLinksButton.isSelected();
    }

    public boolean isSelected_deleteButton() {
        return deleteButton.isSelected();
    }

    public void listenerNodesButton() {
        //initialize the color of the button
        //createNodesButton.setStyle("-fx-background-color: #222;");

        // Add listeners
        createNodesButton.setOnMouseClicked(event -> {
            setSelectedToggleButtons(createNodesButton, searchPathButton, createLinksButton, deleteButton);
        });
    }

    public void listenerLinksButton() {
        //initialize the color of the button
        //createLinksButton.setStyle("-fx-background-color: #222;");

        // Add listeners
        createLinksButton.setOnMouseClicked(event2 -> {
            setSelectedToggleButtons(createLinksButton, searchPathButton,  createNodesButton, deleteButton);
        });
    }

    public void listenerPathButton() {
        //initialize the color of the button
        //searchPathButton.setStyle("-fx-background-color: #222;");

        // Add listeners
        searchPathButton.setOnMouseClicked(event3 -> {
            setSelectedToggleButtons(searchPathButton, createLinksButton, createNodesButton, deleteButton);
            if (searchPathButton.isSelected()){
                selectionPaneController.setSearchPane();
            }
            else{
                selectionPaneController.closeSearchPane();
            }
        });
    }

    public void listenerDeleteButton(){

        deleteButton.setOnMouseClicked(event4 ->{
            setSelectedToggleButtons(deleteButton, createNodesButton, createLinksButton, searchPathButton);
        });
    }

    public void setSelectedToggleButtons(ToggleButton t1, ToggleButton t2, ToggleButton t3, ToggleButton t4){
        if (t1.isSelected()) {
            t1.setStyle("-fx-background-color: red;");
            t2.setSelected(false);
            t2.setStyle("-fx-background-color: #222;");
            t3.setSelected(false);
            t3.setStyle("-fx-background-color: #222;");
            t4.setSelected(false);
            t4.setStyle("-fx-background-color: #222;");
        } else {
            t1.setStyle("-fx-background-color: #222;");
        }
        selectionPaneController.closeSearchPane();
    }
}
