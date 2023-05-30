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

    public void singlePointCrossover(){
        //population.displayPopulation();
        //Genome parent1 = new Genome(listMission.size());
        //Genome parent2 = new Genome(listMission.size());

        Genome[] parents = population.selectParents();

        System.out.println("Parent 1");
        parents[0].displayGenome();
        System.out.println("Parent 2");
        parents[1].displayGenome();

        int genomeLength = listMission.size();

        int crossoverPoint = (int) (Math.random() * genomeLength); // genomeLength est la longueur du génome

        // Effectue le croisement
        Genome offspring1 = new Genome(genomeLength);
        Genome offspring2 = new Genome(genomeLength);

        for (int i = 0; i < genomeLength; i++) {
            if (i < crossoverPoint) {
                offspring1.setGene(i, parents[0].getGene(i));
                offspring2.setGene(i, parents[1].getGene(i));
            } else {
                offspring1.setGene(i, parents[1].getGene(i));
                offspring2.setGene(i, parents[0].getGene(i));
            }
        }
    }


}
