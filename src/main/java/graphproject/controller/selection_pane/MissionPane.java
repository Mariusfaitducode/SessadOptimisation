package graphproject.controller.selection_pane;

import graphproject.model.Node;
import graphproject.model.sessad.Mission;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MissionPane {
    public Node selectedNode;

    public Mission selectedMission;
    public Label textId;
    public Label textPosX;
    public Label textPosY;

    public Label textDay;
    public Label textPeriod;

    public Label textSkill;

    public Label textSpecialty;




    public MissionPane(Pane nodeRightPane){
        //Initialisation du champ de texte id
        this.textId = (Label) nodeRightPane.lookup("#mission-id");

        //Initialisation du champ de texte position X
        this.textPosX = (Label) nodeRightPane.lookup("#mission-posX");

        //Initialisation du champ de texte position X
        this.textPosY = (Label) nodeRightPane.lookup("#mission-posY");

        //Initialisation du champ de texte day
        this.textDay = (Label) nodeRightPane.lookup("#mission-day");

        //Initialisation du champ de texte period
        this.textPeriod = (Label) nodeRightPane.lookup("#mission-period");

        //Initialisation du champ de texte skill
        this.textSkill = (Label) nodeRightPane.lookup("#mission-skill");

        //Initialisation du champ de texte specialty
        this.textSpecialty = (Label) nodeRightPane.lookup("#mission-specialty");
    }



    public void setSelectedMission(Mission mission){
        if (this.selectedMission != null){

        }

        this.selectedMission = mission;

        textId.setText("Id : " + (selectedMission.getId()));
        textPosX.setText("X : "+ ((int)selectedMission.getX()));
        textPosY.setText("Y : " + ((int)selectedMission.getY()));

        textDay.setText("Jour : " + (selectedMission.getDay()));

        textPeriod.setText("Horaires : " + (selectedMission.getStart()) + " - " + (selectedMission.getEnd()));

        textSkill.setText("Compétence : " + selectedMission.getSkill().toString());

        textSpecialty.setText("Spécialité : " + selectedMission.getSpecialty().toString());

    }



}
