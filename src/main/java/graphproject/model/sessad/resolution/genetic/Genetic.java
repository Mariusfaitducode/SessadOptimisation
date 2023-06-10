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

    // Algorithme génétique de la 1ère étape, il est donc spécialisé pour trouver nombre maximal d'affectation
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

    // Algorithme génétique de la 2nd étape, il est donc spécialisé pour réduire le coût total tout en gardant le nombre maximal d'affectation
    public Genome secondPartGeneticAlgo(int generationNbr, double crossOverRateInit, double mutationRateInit, double bestFitness) {

        System.out.println("\n-------------------------------------------------------------------");
        System.out.println("Second part of genetic algo");
        System.out.println("-------------------------------------------------------------------");

        System.out.println("Best fitness determined by the first genetic algorithm : " + bestFitness);

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //Une fois que notre meilleure fitness en terme d'affectation on cherche à optimiser le coût

        //Evaluation de la population
        // pour la population initiale, on récupère la population de l'algorithme précédent
        double maxCost = population.evaluateCostPopulation(listMission, listEmployee, bestFitness);

        double bestCost = 1000;


        Genome bestGenomeFound = new Genome(listMission.size());


        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Moyenne cost : " + population.getMeanCostFitness());
                System.out.println("Ecart type cost : " + population.getStandardDeviationCostFitness());
                System.out.println("Best cost : " + bestCost);
                System.out.println("Similitude : " + population.getSimilarityRate());

                System.out.println("Best genome found : "+ bestGenomeFound.costFitness);
            }

            //Selection
            Genome parent1 = population.selectionCostRoulette();
            Genome parent2 = population.selectionCostRoulette();

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
            child1.evaluateCost(listMission, listEmployee, bestFitness, maxCost);
            child2.evaluateCost(listMission, listEmployee, bestFitness, maxCost);

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

    public Genome getBestGenome(){
        Genome bestGenome = population.getBestGenome();
        System.out.println("Best Genome :");
        bestGenome.displayGenome();
        System.out.println("Fitness = "+ bestGenome.fitness);
        return bestGenome;
    }
}
