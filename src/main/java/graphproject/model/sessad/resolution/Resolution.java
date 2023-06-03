package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;

import java.util.List;

public class Resolution {

    int centreAffected = 0;
    double travelCost = 0;
    int matchingSpecialty = 0;
    private List<Mission> listMission;
    private List<Centre> listCentre;
    private List<Employee> listEmployee;
    private Genetic genetic;

    public Genome firstGenome;
    public Genome secondGenome;

//    private Tabou tabou;

//    private Permutation permutation;

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
        Genome.clearInstance(listMission, listEmployee);
        randomGenome.instantiateGenome(listMission, listEmployee);
    }

    public void startGeneticAlgo(int popSize, int generationNbr, double crossOverRate, double mutationRate) {


        //Première étape de l'algorithme génétique pour l'affectation des missions
        firstGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        //Configuration configuration = new Configuration(firstGenome, listMission, listEmployee, listCentre);

        centreAffected = firstGenome.getFitness();
        travelCost = firstGenome.getCostFitness();
        //matchingSpecialty = configuration.getBestSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        firstGenome.instantiateGenome(listMission, listEmployee);

        /*List<Genome> listBestGenomes = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        int randomGenome = (int)(Math.random() * (listBestGenomes.size()));
        Genome genome = listBestGenomes.get(randomGenome);
        Genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);*/


//        for (Genome genome : listBestGenomes) {
//            genome.displayGenome();
//
//        }
//
//
//        Genome firstGenome = listBestGenomes.get(0);
//
//        Configuration configuration = new Configuration(firstGenome, listMission, listEmployee, listCentre);
//
//        centreAffected = (int)firstGenome.getFitness();
//        travelCost = configuration.getBestCost();
//        matchingSpecialty = configuration.getBestSpecialtyMatch();
//
//        Genome.clearInstance(listMission, listEmployee);
//
//        //Instantiation de Sessad Gestion
//        firstGenome.instantiateGenome(listMission, listEmployee);
//        configuration.getGenome().instantiateGenome(listMission, listEmployee);
//
//        System.out.println("Final genome !!");





        //configuration.getGenome().instantiateGenome(listMission, listEmployee);

//        configuration.getGenome().displayGenome();

    }

    public void secondPartGenetic(int popSize, int generationNbr, double crossOverRate, double mutationRate){

        //Deuxième étape de l'algorithme génétique pour la minimisation des coûts de déplacement
        secondGenome = genetic.secondPartGeneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        centreAffected = secondGenome.getFitness();
        travelCost = secondGenome.getCostFitness();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        secondGenome.instantiateGenome(listMission, listEmployee);
    }

    public int getCentreAffected() {
        return centreAffected;
    }

    public double getTravelCost() {
        return travelCost;
    }

    public int getMatchingSpecialty() {
        return matchingSpecialty;
    }
}
