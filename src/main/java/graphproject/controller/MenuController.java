package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuController {

    //private MenuItem menuItem;

    private final Menu openGraphsMenu;

    private final MenuItem noRecentGraphMenuItem;

    MenuController(Menu openGraphsMenu, MenuItem noRecentGraphMenuItem){
        this.openGraphsMenu = openGraphsMenu;
        this.noRecentGraphMenuItem = noRecentGraphMenuItem;
    }

    public void openExistingGraphsItem(App app, GraphController graphController){
        if (app.getNumberOfGraphs() > 0) {
            int i = 0;
            for (Graph graph : app.getGraphs()) {

                String graphName = graph.getName();
                boolean set = false;

                for (MenuItem item : openGraphsMenu.getItems()) {
                    if (item.getText().equals(graphName)) {
                        set = true;
                    }
                }

                if (!set) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setText(graphName);

                    //On déclare ce qui se passe lorsqu'on clique sur les sous menu
                    //(Ouvrir le graphe correspondant)
                    menuItem.setOnAction(actionEvent -> graphController.openGraph(graph));

                    //on ajoute les sous-menu dans le menu
                    openGraphsMenu.getItems().add(menuItem);
                }
            }
            noRecentGraphMenuItem.setVisible(false);
        } else {
            noRecentGraphMenuItem.setVisible(true);
        }
    }
}
