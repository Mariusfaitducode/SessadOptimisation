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

    public static float mutationRate = 0.8f;
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

        Genome bestGenome = population.getBestGenome();

        bestGenome.fitness = 0;

        newPopulation.population[0] = bestGenome;
        //newPopulation.population[1] = bestGenome;

        int index = 1;

        while (index < popSize){

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
            children[0].mutation(listMission, listEmployee);
            children[1].mutation(listMission, listEmployee);



            newPopulation.population[index] = children[0];
            index++;

            if (index < popSize){
                newPopulation.population[index] = children[1];
                index++;
            }
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
