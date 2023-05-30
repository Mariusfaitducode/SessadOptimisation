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
        //this.distanceMatrix = distanceMatrix;

        //Création population initiale


        //Fitness  

        //Creation de nouveux individus
    }

    public void generatePopulation(int popSize){
        population = new Population(popSize, listMission, listCentre);
    }
}
