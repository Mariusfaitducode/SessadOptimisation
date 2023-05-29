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

        population = new Population(1, listMission, listCentre);
    }

    public void Initialisation(){

    }
}
