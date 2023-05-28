package graphproject.controller.selection_pane;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ItineraryPane {

    //public Itinerary itinerary;

    public Label textEmployee;
    public Label textCentre;
    public Label textDay;
    public Label textPeriod;
    public Label textDistance;
    public Label textCost;



    public ItineraryPane(Pane itineraryRightPane){

        //Initialisation du champ de texte employee
        this.textEmployee = (Label) itineraryRightPane.lookup("#itinerary-employee");

        //Initialisation du champ de texte centre
        this.textCentre = (Label) itineraryRightPane.lookup("#itinerary-centre");

        //Initialisation du champ de texte day
        this.textDay = (Label) itineraryRightPane.lookup("#itinerary-day");

        //Initialisation du champ de texte period
        this.textPeriod = (Label) itineraryRightPane.lookup("#itinerary-period");

        //Initialisation du champ de texte distance
        this.textDistance = (Label) itineraryRightPane.lookup("#itinerary-distance");

        //Initialisation du champ de texte cost
        this.textCost = (Label) itineraryRightPane.lookup("#itinerary-cost");


    }

    /*public void setLinkPane(Node startNode, Link link, Node endNode){
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
    }*/
}
