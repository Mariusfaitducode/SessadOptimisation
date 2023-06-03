package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;
import graphproject.model.sessad.resolution.step_2.Configuration;

import java.util.List;

public class Resolution {

    int centreAffected = 0;
    double travelCost = 0;
    int matchingSpecialty = 0;
    private List<Mission> listMission;
    private List<Centre> listCentre;
    private List<Employee> listEmployee;
    private Genetic genetic;

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

//        //création population initiale
//        genetic.generatePopulation();
//
//        //genetic.getPopulation().displayPopulation();
//
//
//
//        for (int i = 0; i < 500; i++){
//            System.out.println("------ Génération "+ i + " ---------------------");
//
//            //fitness
//            genetic.fitness();
//
//            //création de nouveaux individus
//            genetic.generateNewGeneration();
//        }
//
//        System.out.println("------ Last génération ---------------------");
//        genetic.fitness();
//        genetic.displayBestGenome();

        //List<Genome> listBestGenomes = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);




        Genome firstGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        //Configuration configuration = new Configuration(firstGenome, listMission, listEmployee, listCentre);

        centreAffected = firstGenome.getFitness();
        travelCost = firstGenome.getCostFitness();
        //matchingSpecialty = configuration.getBestSpecialtyMatch();

        List<Genome> listBestGenomes = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        int randomGenome = (int)(Math.random() * (listBestGenomes.size()));
        Genome genome = listBestGenomes.get(randomGenome);
        Genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);


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




        //Instantiation de Sessad Gestion
        firstGenome.instantiateGenome(listMission, listEmployee);
        //configuration.getGenome().instantiateGenome(listMission, listEmployee);

//        configuration.getGenome().displayGenome();

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
