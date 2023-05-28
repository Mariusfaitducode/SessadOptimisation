package graphproject.controller.selection_pane;

import graphproject.model.sessad.Centre;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CentrePane {

    public Centre selectedCentre;
    public Label textId;

    public Label textName;
    public Label textPosX;
    public Label textPosY;

    public ChoiceBox<String> choiceBoxEmployee;

    public CentrePane(Pane centreRightPane){
        //Initialisation du champ de texte id
        this.textId = (Label) centreRightPane.lookup("#centre-id");

        //Initialisation du champ de texte name
        this.textName = (Label) centreRightPane.lookup("#centre-name");

        //Initialisation du champ de texte position X
        this.textPosX = (Label) centreRightPane.lookup("#centre-posX");

        //Initialisation du champ de texte position X
        this.textPosY = (Label) centreRightPane.lookup("#centre-posY");


    }
}
