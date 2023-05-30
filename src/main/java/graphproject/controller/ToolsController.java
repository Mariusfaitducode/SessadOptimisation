package graphproject.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ToolsController {

    private final Button test;
    private final ToggleButton geneticToggleButton;
    private final ToggleButton tabouToggleButton;

    private final ToggleButton optToggleButton;

    //private final SelectionPaneController selectionPaneController;

    public ToolsController(HBox toolsBarPane) {
        this.test = (Button) toolsBarPane.lookup("#id-toolsBar-test");
        this.geneticToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-genetic");
        this.tabouToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-tabou");
        this.optToggleButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-2-opt");

    }

    public Button getTest() {return test;}

    public boolean isSelected_geneticToggleButton() {
        return geneticToggleButton.isSelected();
    }

    public boolean isSelected_tabouToggleButton() {
        return tabouToggleButton.isSelected();
    }

    public boolean isSelected_optToggleButton() {
        return optToggleButton.isSelected();
    }

}
