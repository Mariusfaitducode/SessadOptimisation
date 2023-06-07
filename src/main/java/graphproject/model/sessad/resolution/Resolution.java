package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;
import graphproject.model.sessad.resolution.step_2.BrutForce;

import java.time.Instant;
import java.util.List;

public class Resolution {

    //int bestFitness = 0;
    int centreAffected = 0;
    double travelCost = 0;
    int matchingSpecialty = 0;
    private List<Mission> listMission;
    private List<Centre> listCentre;
    private List<Employee> listEmployee;
    private Genetic genetic;

    public Genome firstGenome;
    public Genome secondGenome;

    public Resolution(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.listEmployee = listEmployee;

        //this.nbrCentre = listCentre.size();
        genetic = new Genetic(listMission, listCentre, listEmployee, 500);
    }

    public void startInitiatingGeneticAlgo(int popSize) {
        Population population = new Population(popSize);
        population.initializePopulation(listMission, listCentre);
        Genome randomGenome = population.getRandomGenome();

        randomGenome.deternimeFitnessWithoutChecking();
        this.centreAffected = randomGenome.getFitness();
        randomGenome.determineCostFitness(listMission, listEmployee);
        this.travelCost = randomGenome.getCostFitness();
        randomGenome.determineSpecialtyMatch(listMission, listEmployee);
        this.matchingSpecialty = randomGenome.getSpecialtyMatch();


        Genome.clearInstance(listMission, listEmployee);
        randomGenome.instantiateGenome(listMission, listEmployee);
    }

    public void startGeneticAlgo(int popSize, int generationNbr, double crossOverRate, double mutationRate) {

        //Première étape de l'algorithme génétique pour l'affectation des missions
        firstGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        firstGenome.deternimeFitnessWithoutChecking();
        firstGenome.determineCostFitness(listMission, listEmployee);
        firstGenome.determineSpecialtyMatch(listMission, listEmployee);

        // Get stats of the best genome
        this.centreAffected = firstGenome.getFitness();
        this.travelCost = firstGenome.getCostFitness();
        this.matchingSpecialty = firstGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        firstGenome.instantiateGenome(listMission, listEmployee);

    }

    public void secondPartGenetic(int generationNbr, double crossOverRate, double mutationRate){

        //firstGenome.determineFitness(listMission, listEmployee);

        //Deuxième étape de l'algorithme génétique pour la minimisation des coûts de déplacement
        secondGenome = genetic.secondPartGeneticAlgo(generationNbr, crossOverRate, mutationRate, this.centreAffected);

        //calculateAttributeGenome(secondGenome, listMission, listEmployee);

        // Get stats of the best genome
        secondGenome.deternimeFitnessWithoutChecking();
        secondGenome.determineCostFitness(listMission, listEmployee);
        secondGenome.determineSpecialtyMatch(listMission, listEmployee);

        this.centreAffected = secondGenome.getFitness();
        this.travelCost = secondGenome.getCostFitness();
        this.matchingSpecialty = secondGenome.getSpecialtyMatch();


        System.out.println("Final Cost Fitness : " + secondGenome.getCostFitness());

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        secondGenome.instantiateGenome(listMission, listEmployee);
    }

    public void brutForceStep3(){

        BrutForce brutForce = new BrutForce(secondGenome, listMission, listEmployee, listCentre);
        brutForce.brutForceStep3(listMission, listEmployee);
        //bestCost = Math.min(bestCost, configuration.getBestCost());

        //System.out.println("Best cost from brut force algorithm after second algo : " + bestCost);

        //Récupération du résultat
        Genome bestGenome = brutForce.getGenome();

        bestGenome.displayGenome();




//        bestGenome.evaluateCost(listMission, listEmployee, 0, 0);
        bestGenome.deternimeFitnessWithoutChecking();
        bestGenome.determineCostFitness(listMission, listEmployee);
        bestGenome.determineSpecialtyMatch(listMission, listEmployee);

        this.centreAffected = bestGenome.getFitness();
        this.travelCost = bestGenome.getCostFitness();
        this.matchingSpecialty = bestGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        bestGenome.instantiateGenome(listMission, listEmployee);

    }



    public void calculateAttributeGenome(Genome genome, List<Mission> listMission, List<Employee> listEmployee){
        genome.evaluateCost(listMission, listEmployee, 0);


        travelCost = genome.getCostFitness();

        genome.determineFitness(listMission, listEmployee);
        System.out.println("Final Fitness : " + genome.getFitness() + " Cost : " + genome.getCostFitness());
        centreAffected = genome.getFitness();
    }

    public int getCentreAffected() {
        return centreAffected;
    }

    public double getTravelCost() {
        return travelCost;
    }

    public int getMatchingSpecialty() {return matchingSpecialty;}
}
