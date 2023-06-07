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

    private List<Genome> listBestGenomeFirstAlgo;

    private List<Genome> listBestGenomeSecondAlgo;

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

    public List<Genome> getListBestGenomeFirstAlgo() {
        return listBestGenomeFirstAlgo;
    }

    public List<Genome> getListBestGenomeSecondAlgo() {
        return listBestGenomeSecondAlgo;
    }

    public Genome geneticAlgo(int popSize, int generationNbr, double crossOverRateInit, double mutationRateInit) {

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //création population initiale
        this.population = new Population(popSize);
        population.initializePopulation(listMission, listCentre);

        //Evaluation de la population
        population.evaluatePopulation(listMission, listEmployee);

        //Récupération de la meilleur solution de la population initiale
        Genome bestGenome = getBestGenome();
        int bestFitness = bestGenome.fitness;

        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Moyenne fitness : " + population.getMeanFitness());
                System.out.println("Ecart type fitness : " + population.getStandardDeviationFitness());
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Similitude : " + population.getSimilarityRate());
                bestGenome = population.getBestGenome();
                bestGenome.deternimeFitnessWithoutChecking();
                System.out.println("Fitness from algo: " + bestFitness);
                System.out.println("Fitness : " + bestGenome.fitness);
                bestGenome.determineCostFitness(listMission, listEmployee);
                System.out.println("Cost fitness : " + bestGenome.costFitness);
                bestGenome.determineSpecialtyMatch(listMission, listEmployee);
                System.out.println("Match specialty : " + bestGenome.specialtyMatch);
            }

        	//Selection
        	Genome parent1 = population.selectionRoulette();
            Genome parent2 = population.selectionRoulette();

        	//Croisement avec taux de crossOver
            Genome child1 = new Genome(parent1.getSizeGenome());
            Genome child2 = new Genome(parent2.getSizeGenome());
            if (Math.random() < crossOverRate) {
                population.crossOver(parent1, parent2, child1, child2);
            } else {
                child1 = parent1;
                child2 = parent2;
            }

        	//Mutation 1 avec taux de mutation
            if (Math.random() < mutationRate) {
                child1.mutation();
            }

            //Mutation 2 avec taux de mutation
            if (Math.random() < mutationRate) {
                child2.mutation();
            }

        	//Evaluation des 2 enfants
            child1.evaluate(listMission, listEmployee);
            child2.evaluate(listMission, listEmployee);

            //Remplacement
            population.remplacementRoulette(child1);
            population.remplacementRoulette(child2);

        	// Check if better
            if (child1.fitness > bestFitness) {
                bestFitness = child1.fitness;
                System.out.println("Better genome found : "+ bestFitness);
            }
            if (child2.fitness > bestFitness) {
                bestFitness = child2.fitness;
                System.out.println("Better genome found : "+ bestFitness);
            }

        }
        listBestGenomeFirstAlgo = population.getFitnessPopulation(listEmployee);

        System.out.println("-----------------------------------------------------------");
        System.out.println("--------------Result first genetic algorithm---------------");

        System.out.println("Moyenne fitness : " + population.getMeanFitness());
        System.out.println("Ecart type : " + population.getStandardDeviationFitness());
        System.out.println("Best fitness : " + bestFitness);
        System.out.println("Similitude : " + population.getSimilarityRate());
        System.out.println("Part of pop with different mission/centre associations : " + listBestGenomeFirstAlgo.size() + " / " + popSize);
        System.out.println("--------------Best genome---------------");

        bestGenome = population.getBestGenome();
        bestGenome.displayGenome();
        bestGenome.deternimeFitnessWithoutChecking();
        System.out.println("Fitness from algo: " + bestFitness);
        System.out.println("Fitness : " + bestGenome.fitness);
        bestGenome.determineCostFitness(listMission, listEmployee);
        System.out.println("Cost fitness : " + bestGenome.costFitness);
        bestGenome.determineSpecialtyMatch(listMission, listEmployee);
        System.out.println("Match specialty : " + bestGenome.specialtyMatch);

        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");

        return bestGenome;
    }

    public Genome secondPartGeneticAlgo(int generationNbr, double crossOverRateInit, double mutationRateInit, double bestFitness) {

        System.out.println("\n-------------------------------------------------------------------");
        System.out.println("Second part of genetic algo");
        System.out.println("-------------------------------------------------------------------");

        System.out.println("Best fitness determined by the first genetic algorithm : " + bestFitness);

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //Une fois que notre meilleure fitness en terme d'affectation on cherche à optimiser le coût

        //Evaluation de la population
        double maxCost = population.evaluateCostPopulation(listMission, listEmployee, bestFitness);

        //Affichage de la meilleure solution
        //Genome bestGenome = getBestGenome();
        double bestCost = 1000;


        Genome bestGenomeFound = new Genome(listMission.size());


        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Moyenne fitness : " + population.getMeanFitness());
                System.out.println("Ecart type fitness: " + population.getStandardDeviationFitness());
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Moyenne cost : " + population.getMeanCostFitness());
                System.out.println("Ecart type cost : " + population.getStandardDeviationCostFitness());
                System.out.println("Best cost : " + bestCost);
                //System.out.println("Max cost" + maxCost);
                System.out.println("Similitude : " + population.getSimilarityRate());

                bestGenomeFound.determineCostFitness(listMission, listEmployee);
                System.out.println("Best genome found : "+ bestGenomeFound.costFitness);
            }

            //Selection
            Genome parent1 = population.selectionCostRoulette();
            Genome parent2 = population.selectionCostRoulette();

            //System.out.println("Cost parent1 : " + parent1.costFitness);
            //System.out.println("Cost parent2 : " + parent2.costFitness);


            //System.out.println("Mission " + i + " : " + parent1.getGene(i) + " - " + parent2.getGene(i));

            //Croisement avec taux de crossOver
            Genome child1 = new Genome(parent1.getSizeGenome());
            Genome child2 = new Genome(parent2.getSizeGenome());

            if (Math.random() < crossOverRate) {
                population.crossOver(parent1, parent2, child1, child2);
            } else {
                child1 = new Genome(parent1);
                child2 = new Genome(parent2);
            }

            //Mutation 1 avec taux de mutation
            if (Math.random() < mutationRate) {
                child1.mutation();
            }

            //Mutation 2 avec taux de mutation
            if (Math.random() < mutationRate) {
                child2.mutation();
            }

            //Evaluation des 2 enfants
            child1.evaluateCost(listMission, listEmployee, bestFitness);
            child2.evaluateCost(listMission, listEmployee, bestFitness);

            //System.out.println("Cost child1 : " + child1.costFitness);
            //System.out.println("Cost child2 : " + child2.costFitness);

            //Remplacement
            if (child1.fitness >= bestFitness){
                population.remplacementCostRoulette(child1);
            }
            if (child2.fitness >= bestFitness){
                population.remplacementCostRoulette(child2);
            }

            // Check if better

            if (child1.costFitness < bestCost) {
                if (child1.fitness != 0) {
                    bestCost = child1.costFitness;
                    System.out.println("Better cost genome found : " + bestCost);
                    //population.population[0] = child1;
                    bestGenomeFound = new Genome(child1);
                    population.replaceWithBestSolution(child1);
                }
            }
            if (child2.costFitness < bestCost) {
                if (child2.fitness != 0) {
                    bestCost = child2.costFitness;
                    System.out.println("Better cost genome found : " + bestCost);
                    //population.population[1] = child2;
                    bestGenomeFound = new Genome(child2);
                    population.replaceWithBestSolution(child2);
                }
            }

        }
        listBestGenomeSecondAlgo = population.getFitnessPopulation(listEmployee);

        System.out.println("-----------------------------------------------------------");
        System.out.println("--------------Result second genetic algorithm---------------");

        System.out.println("Moyenne fitness : " + population.getMeanFitness());
        System.out.println("Ecart type : " + population.getStandardDeviationFitness());
        System.out.println("Best fitness : " + bestFitness);
        System.out.println("Similitude : " + population.getSimilarityRate());
        System.out.println("Moyenne cost : " + population.getMeanCostFitness());
        System.out.println("Ecart type cost : " + population.getStandardDeviationCostFitness());
        System.out.println("Best cost : " + bestCost);
        System.out.println("Part of pop with different mission/centre associations : " + listBestGenomeFirstAlgo.size() + " / " + popSize);

        System.out.println("--------------Best genome---------------");

        bestGenomeFound.displayGenome();
        bestGenomeFound.deternimeFitnessWithoutChecking();
        System.out.println("Fitness from algo: " + bestFitness);
        System.out.println("Fitness : " + bestGenomeFound.fitness);
        //bestGenomeFound.determineCostFitness(listMission, listEmployee);
        System.out.println("Cost fitness : " + bestGenomeFound.costFitness);
        bestGenomeFound.determineSpecialtyMatch(listMission, listEmployee);
        System.out.println("Match specialty : " + bestGenomeFound.specialtyMatch);

        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");

        return bestGenomeFound;
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

        Population newPopulation = new Population(popSize * 2);

        Genome bestGenome = population.getBestGenome();

        bestGenome.fitness = 0;

        int index = 0;

        while (index < popSize){

            //Selection
            Genome[] parents = population.selectParents();

            //Croisement
            Genome[] children = population.crossover(parents[0], parents[1]);

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

        System.arraycopy(population.population, 0, newPopulation.population, index, population.population.length);
        this.population = newPopulation.selectBestElements();
    }

    public void displayBestGenome(){

        Genome bestGenome = population.getBestGenome();

        Genome.clearInstance(listMission, listEmployee);
        bestGenome.instantiateGenome(listMission, listEmployee);

        System.out.println("Best Genome :");
        bestGenome.displayGenome();
        System.out.println("Fitness = "+ bestGenome.fitness);
    }

    public Genome getBestGenome(){
        Genome bestGenome = population.getBestGenome();
        System.out.println("Best Genome :");
        bestGenome.displayGenome();
        System.out.println("Fitness = "+ bestGenome.fitness);
        return bestGenome;
    }
}
