package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

public class MenuController {

    //private MenuItem menuItem;

    private final Menu openGraphsMenu;

    private final MenuItem noRecentGraphMenuItem;

    MenuController(Menu openGraphsMenu, MenuItem noRecentGraphMenuItem){
        this.openGraphsMenu = openGraphsMenu;
        this.noRecentGraphMenuItem = noRecentGraphMenuItem;
    }


}
