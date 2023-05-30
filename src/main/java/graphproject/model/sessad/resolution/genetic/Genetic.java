package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.List;

public class Genetic {

    List<Mission> listMission;
    List<Centre> listCentre;

    List<Employee> listEmployee;

    int popSize;


    Population population;

    public Genetic(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee, int popSize){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.listEmployee = listEmployee;

        this.popSize = popSize;
        //this.distanceMatrix = distanceMatrix;

        //Création population initiale
        //population = new Population(popSize, listMission, listCentre);


        //Fitness  

        //Creation de nouveux individus
    }

    public Population getPopulation(){
        return this.population;
    }

    public void generatePopulation(){
        this.population = new Population(popSize, listMission, listCentre);
    }

    public void fitness(){
        //population.displayPopulation();
        population.evaluatePopulation(listMission, listEmployee);
    }


}
