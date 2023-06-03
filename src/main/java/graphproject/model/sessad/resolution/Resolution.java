package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;

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

        //calculateAttributeGenome(randomGenome, listMission, listEmployee);

        this.centreAffected = (int)randomGenome.getFitness();
        this.travelCost = randomGenome.getCostFitness();


        Genome.clearInstance(listMission, listEmployee);
        randomGenome.instantiateGenome(listMission, listEmployee);
    }

    public void startGeneticAlgo(int popSize, int generationNbr, double crossOverRate, double mutationRate) {

        //Première étape de l'algorithme génétique pour l'affectation des missions
        firstGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        //Configuration configuration = new Configuration(firstGenome, listMission, listEmployee, listCentre);

        //calculateAttributeGenome(firstGenome, listMission, listEmployee);
        //matchingSpecialty = configuration.getBestSpecialtyMatch();

        System.out.println("Final genome !! fitnes :: " + firstGenome.getFitness());

        firstGenome.evaluateCost(listMission, listEmployee, 0, 0);
        this.centreAffected = (int)firstGenome.getFitness();
        this.travelCost = firstGenome.getCostFitness();

        //this.travelCost = firstGenome.getCostFitness();

        //calculateAttributeGenome(firstGenome, listMission, listEmployee);

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        firstGenome.instantiateGenome(listMission, listEmployee);

//        List<Genome> listBestGenomes = genetic.getListBestGenomeFirstAlgo();
//        double bestCost = Integer.MAX_VALUE;
//        for (Genome genome : listBestGenomes) {
//
//            Configuration configuration = new Configuration(genome, listMission, listEmployee, listCentre);
//            configuration.brutForceStep2();
//            bestCost = Math.min(bestCost, configuration.getBestCost());
//        }
//        System.out.println("Best cost from brut force algorithm after 1st algo : " + bestCost);

    }

    public void secondPartGenetic(int generationNbr, double crossOverRate, double mutationRate){

        //firstGenome.determineFitness(listMission, listEmployee);

        //Deuxième étape de l'algorithme génétique pour la minimisation des coûts de déplacement
        secondGenome = genetic.secondPartGeneticAlgo(generationNbr, crossOverRate, mutationRate, this.centreAffected);

        //calculateAttributeGenome(secondGenome, listMission, listEmployee);
        this.centreAffected = (int)secondGenome.getFitness();
        this.travelCost = secondGenome.getCostFitness();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        secondGenome.instantiateGenome(listMission, listEmployee);

//        List<Genome> listBestGenomes = genetic.getListBestGenomeSecondAlgo();
//        double bestCost = Integer.MAX_VALUE;
//        for (Genome genome : listBestGenomes) {
//
//            Configuration configuration = new Configuration(genome, listMission, listEmployee, listCentre);
//            configuration.brutForceStep2();
//            bestCost = Math.min(bestCost, configuration.getBestCost());
//        }
//        System.out.println("Best cost from brut force step 2 algorithm after second algo : " + bestCost);

        /*List<Genome> listBestGenomes = genetic.getListBestGenomeSecondAlgo();
        double bestCost = Integer.MAX_VALUE;
        for (Genome genome : listBestGenomes) {

            Configuration configuration = new Configuration(genome, listMission, listEmployee, listCentre);
            configuration.brutForceStep3();
            bestCost = Math.min(bestCost, configuration.getBestCost());
        }
        System.out.println("Best cost from brut force algorithm after second algo : " + bestCost);*/
    }

    public void calculateAttributeGenome(Genome genome, List<Mission> listMission, List<Employee> listEmployee){
        genome.evaluateCost(listMission, listEmployee, 0, 0);


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

    public int getMatchingSpecialty() {
        return matchingSpecialty;
    }
}
