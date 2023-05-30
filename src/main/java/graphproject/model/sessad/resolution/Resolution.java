package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;

import java.util.List;

public class Resolution {

    private Genetic genetic;

//    private Tabou tabou;

//    private Permutation permutation;

    public Resolution(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee){

        genetic = new Genetic(listMission, listCentre, listEmployee, 10);
    }

    public void startGeneticAlgo() {

        //création population initiale
        genetic.generatePopulation();

        genetic.getPopulation().displayPopulation();

        //fitness
        //genetic.fitness();



        //création de nouveaux individus

    }
}
