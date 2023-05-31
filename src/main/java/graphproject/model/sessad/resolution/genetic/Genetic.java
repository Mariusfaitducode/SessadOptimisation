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

    public static float mutationRate = 0.01f;
    public static int lastIdEmployee;


    Population population;

    public Genetic(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee, int popSize){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.listEmployee = listEmployee;

        lastIdEmployee = listEmployee.size();

        this.popSize = popSize;

    }

    public Population getPopulation(){
        return this.population;
    }

    public void generatePopulation(){
        this.population = new Population(popSize);
        population.initializePopulation(listMission, listCentre);
    }

    public void fitness(){
        //population.displayPopulation();
        population.evaluatePopulation(listMission, listEmployee);
    }

    public void generateNewGeneration(){

        Population newPopulation = new Population(popSize);

        for (int i = 0; i < popSize / 2; i++){

            //Selection
            Genome[] parents = population.selectParents();

            /*System.out.println("Parent 1");
            parents[0].displayGenome();
            System.out.println("Parent 2");
            parents[1].displayGenome();*/

            //Croisement
            Genome[] children = population.crossover(parents[0], parents[1]);

            /*System.out.println("Children 1");
            children[0].displayGenome();
            System.out.println("Children 2");
            children[1].displayGenome();*/

            //Mutation
            children[0].mutation();
            children[1].mutation();


            int index = i * 2;
            newPopulation.population[index] = children[0];
            newPopulation.population[index+1] = children[1];

        }
        this.population = newPopulation;
    }

    public void displayBestGenome(){

        Genome bestGenome = population.getBestGenome();

        bestGenome.instantiateGenome(listMission, listEmployee);

        System.out.println("Best Genome :");
        bestGenome.displayGenome();
        System.out.println("Fitness = "+ bestGenome.fitness);
    }
}
