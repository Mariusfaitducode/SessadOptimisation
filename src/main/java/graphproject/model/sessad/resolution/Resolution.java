package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;

import java.util.List;

import static graphproject.model.sessad.SessadGestion.distMissionCentre;

public class Resolution {

    private Genetic genetic;

//    private Tabou tabou;

//    private Permutation permutation;

    public Resolution(List<Mission> listMission, List<Centre> listCentre){
        genetic = new Genetic(listMission, listCentre, distMissionCentre);
    }

    public void startGeneticAlgo() {
        genetic.generatePopulation(1);
    }
}
