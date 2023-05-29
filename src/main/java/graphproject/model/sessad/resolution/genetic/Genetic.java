package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Mission;

import java.util.List;

public class Genetic {

    List<Mission> listMission;
    List<Centre> listCentre;
    double[][] distanceMatrix;

    Population population;

    public Genetic(List<Mission> listMission, List<Centre> listCentre, double[][] distanceMatrix){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.distanceMatrix = distanceMatrix;

        //Création population initiale
        population = new Population(1, listMission, listCentre);

        //Fitness  

        //Creation de nouveux individus
    }

    public void Initialisation(){

    }
}
