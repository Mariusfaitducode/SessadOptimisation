package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
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

    public Genome geneticAlgo(int popSize, int generationNbr, double crossOverRateInit, double mutationRateInit) {

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //création population initiale
        this.population = new Population(popSize);
        population.initializePopulation(listMission, listCentre);

        //Evaluation de la population
        population.evaluatePopulation(listMission, listEmployee);

        //Affichage de la meilleure solution
        Genome bestGenome = getBestGenome();
        int bestFitness = bestGenome.fitness;

        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Moyenne fitness : " + population.getMeanFitness());
                System.out.println("Ecart type : " + population.getStandardDeviationFitness());
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Similitude : " + population.getSimilarityRate());
                System.out.println("-----------------------------");
            }

//            crossOverRate = crossOverRateInit - (iter * crossOverRateInit / generationNbr);
//            mutationRate = mutationRateInit - (iter * mutationRateInit / generationNbr);


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

        Genome finalBestGenome = secondPartGeneticAlgo(generationNbr, crossOverRateInit, mutationRateInit, bestGenome.fitness);

        //population.evaluateCostPopulation(listMission, listEmployee, bestFitness);




        //bestGenome = population.getBestGenome();
        Genome.clearInstance(listMission, listEmployee);
        finalBestGenome.instantiateGenome(listMission, listEmployee);


        System.out.println("Best genome found : "+ bestGenome.fitness);

        List<Genome> bestPopulation = population.getFitnessPopulation(listEmployee);
        System.out.println("Similitude : " + population.getSimilarityRate());
        System.out.println("Taille population finale : "+ bestPopulation.size());

        return finalBestGenome;
//        bestGenome.displayGenome();
//        bestGenome.checkValidity(listMission, listEmployee);

    }

    public Genome secondPartGeneticAlgo(int generationNbr, double crossOverRateInit, double mutationRateInit, double bestFitness) {

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //Une fois que notre meilleure fitness en terme d'affectation on cherche à optimiser le coût

        //Evaluation de la population
        double maxCost = population.evaluateCostPopulation(listMission, listEmployee, bestFitness);

        //Affichage de la meilleure solution
        Genome bestGenome = getBestGenome();
        double bestCost = 1000;

        System.out.println("Max cost : "+ maxCost);

        for (Genome genome : population.population) {
            System.out.println("Fitness : " + genome.fitness);
        	System.out.println("Cost : " + genome.costFitness);
        }

        List<Genome> bestGenomeFound = new ArrayList<>();

        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Moyenne fitness : " + population.getMeanFitness());
                System.out.println("Ecart type : " + population.getStandardDeviationFitness());
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Moyenne cost : " + population.getMeanCostFitness());
                System.out.println("Best cost : " + bestCost);
                System.out.println("Similitude : " + population.getSimilarityRate());
                System.out.println("-----------------------------");
            }

            Genome bestGenomeTemp1 = population.population[0];
            Genome bestGenomeTemp2 = population.population[1];


            //System.out.println("Best genome : " + bestGenomeTemp.costFitness);

//            crossOverRate = crossOverRateInit - (iter * crossOverRateInit / generationNbr);
//            mutationRate = mutationRateInit - (iter * mutationRateInit / generationNbr);


            //Selection
            Genome parent1 = population.selectionCostRoulette(maxCost);
            Genome parent2 = population.selectionCostRoulette(maxCost);

            //System.out.println("Cost parent1 : " + parent1.costFitness);
            //System.out.println("Cost parent2 : " + parent2.costFitness);

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
            maxCost = child1.evaluateCost(listMission, listEmployee, bestFitness, maxCost);
            maxCost = child2.evaluateCost(listMission, listEmployee, bestFitness, maxCost);

            //System.out.println("Cost child1 : " + child1.costFitness);
            //System.out.println("Cost child2 : " + child2.costFitness);

            //Remplacement
            population.remplacementCostRoulette(child1, maxCost);
            population.remplacementCostRoulette(child2, maxCost);

            // Check if better
            if (child1.fitness > bestFitness) {
                bestFitness = child1.fitness;
                System.out.println("Better genome found : "+ bestFitness);
            }
            if (child2.fitness > bestFitness) {
                bestFitness = child2.fitness;
                System.out.println("Better genome found : "+ bestFitness);
            }
            if (child1.costFitness < bestCost) {
                if (child1.fitness != 0){
                    bestCost = child1.costFitness;
                    System.out.println("Better cost genome found : "+ bestCost);
                    population.population[0] = child1;
                    bestGenomeFound.add(child1);
                }
                else{
                    population.population[0] = bestGenomeTemp1;
                }
            }
            if (child2.costFitness < bestCost) {
                if (child2.fitness != 0){
                    bestCost = child2.costFitness;
                    System.out.println("Better cost genome found : "+ bestCost);
                    population.population[1] = child2;
                    bestGenomeFound.add(child2);
                }
                else{
                    population.population[1] = bestGenomeTemp2;
                }
            }


        }

        for (Genome genome : bestGenomeFound) {
            System.out.println("Fitness : " + genome.fitness);
            System.out.println("Cost : " + genome.costFitness);
        }
        return bestGenomeFound.get(bestGenomeFound.size()-1);
    }

    public void littleGeneticAlgo(List<List<Integer>> combinations, int popSize, int generationNbr, double crossOverRateInit, double mutationRateInit){

        double crossOverRate = crossOverRateInit;
        double mutationRate = mutationRateInit;

        //création population initiale
        this.population = new Population(popSize);
        population.initializeLittlePopulation(combinations, listMission, listCentre);

        //Evaluation de la population
        //population.evaluateCostPopulation(listMission, listEmployee);

        for (Genome genome : population.population) {
        	genome.displayGenome();
            System.out.println("Fitness : " + genome.fitness);
            System.out.println("Cost : " + genome.costFitness);

        }
/*
        //Affichage de la meilleure solution
        Genome bestGenome = getBestGenome();
        int bestFitness = bestGenome.fitness;

        for (int iter = 0 ; iter < generationNbr ; iter++) {
            if (iter % (generationNbr/10) == 0) {
                System.out.println("-----------------------------");
                System.out.println("Generation : " + iter);
                System.out.println("Moyenne fitness : " + population.getMeanFitness());
                System.out.println("Ecart type : " + population.getStandardDeviationFitness());
                System.out.println("Best fitness : " + bestFitness);
                System.out.println("Similitude : " + population.getSimilarityRate());
                System.out.println("-----------------------------");
            }

//            crossOverRate = crossOverRateInit - (iter * crossOverRateInit / generationNbr);
//            mutationRate = mutationRateInit - (iter * mutationRateInit / generationNbr);


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
        bestGenome = population.getBestGenome();
        Genome.clearInstance(listMission, listEmployee);
        bestGenome.instantiateGenome(listMission, listEmployee);

        System.out.println("Best genome found : "+ bestGenome.fitness);

        List<Genome> bestPopulation = population.getFitnessPopulation(bestGenome.fitness);
        System.out.println("Similitude : " + population.getSimilarityRate());
        System.out.println("Taille population finale : "+ bestPopulation.size());

        //return bestPopulation;*/
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

        //newPopulation.population[0] = bestGenome;
        //newPopulation.population[1] = bestGenome;

        int index = 0;

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
