package graphproject.controller;

import graphproject.controller.selection_pane.CentrePane;
import graphproject.controller.selection_pane.ItineraryPane;
import graphproject.controller.selection_pane.MissionPane;
import graphproject.controller.selection_pane.SearchPane;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.model.sessad.Mission;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SelectionPaneController {

    //NodePane -> missionPane
    //LinkPane -> roadPane
    //add CentrePane
    private final Pane missionRightPane, itineraryRightPane, centreRightPane;

    private final HBox toolsBar;

    private MissionPane missionPane;

    private CentrePane centrePane;

    private SearchPane searchPane;

    private ItineraryPane itineraryPane;


    SelectionPaneController(Pane missionRightPane, Pane itineraryRightPane, Pane centreRightPane, HBox toolsBar, Pane centerPane){
        this.missionRightPane = missionRightPane;
        this.itineraryRightPane = itineraryRightPane;
        this.centreRightPane = centreRightPane;

        this.toolsBar = toolsBar;

        missionPane = new MissionPane(missionRightPane);

        centrePane = new CentrePane(centreRightPane);

        itineraryPane = new ItineraryPane(itineraryRightPane);
    }

    public void closeSelectionPane() {
        missionRightPane.setVisible(false);
        itineraryRightPane.setVisible(false);
        //searchPathRightPane.setVisible(false);
    }

    //Node Pane
    public void setMissionPane(Node selectedNode){
        missionRightPane.setVisible(true);
        itineraryRightPane.setVisible(false);
        centreRightPane.setVisible(false);

        missionPane.setSelectedNode(selectedNode);



        missionPane.setSelectedMission((Mission)selectedNode.getPlace(0));
    }
    public MissionPane getMissionPane(){return this.missionPane;}

    //public Pane getSearchPathRightPane(){return this.searchPathRightPane;}

    //Search Pane
    /*public void setSearchPane(){
        searchPathRightPane.setVisible(true);
        missionRightPane.setVisible(false);
        linkRightPane.setVisible(false);
    }

    public void closeSearchPane(){
        searchPathRightPane.setVisible(false);
    }*/

    //Search function

    public void setSearchNode(Node node){
        searchPane.setSearchNode(node);
    }


    //Link Pane
    public void setLinkPane(Node startNode, Link link, Node endNode){
        missionRightPane.setVisible(false);
        itineraryRightPane.setVisible(true);
        //searchPathRightPane.setVisible(false);

        //itineraryPane.setLinkPane(startNode, link, endNode);
    }

}
