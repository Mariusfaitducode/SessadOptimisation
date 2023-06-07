package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;
import graphproject.model.sessad.resolution.step_2.Configuration;

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

        // Get stats of the best genome
        this.centreAffected = firstGenome.getFitness();
        this.travelCost = firstGenome.getCostFitness();
        this.matchingSpecialty = firstGenome.getSpecialtyMatch();

//        firstGenome.evaluateCost(listMission, listEmployee, 0, 0);
//        this.centreAffected = (int)firstGenome.getFitness();
//        this.travelCost = firstGenome.getCostFitness();
//        firstGenome.determineSpecialtyMatch(listMission, listEmployee);
//        this.matchingSpecialty = firstGenome.getSpecialtyMatch();

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

        this.centreAffected = secondGenome.getFitness();
        this.travelCost = secondGenome.getCostFitness();
        this.matchingSpecialty = secondGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        secondGenome.instantiateGenome(listMission, listEmployee);

//        List<Genome> listBestGenomes = genetic.getListBestGenomeSecondAlgo();
//        double bestCost = Integer.MAX_VALUE;
//        System.out.println("All genomes studied : ");
//        for (Genome genome : listBestGenomes) {
//
//            Configuration configuration = new Configuration(genome, listMission, listEmployee, listCentre);
//            configuration.brutForceStep2();
//            bestCost = Math.min(bestCost, configuration.getBestCost());
//            configuration.getGenome().displayGenome();
//            configuration.getGenome().determineCostFitness(listMission, listEmployee);
//            System.out.println("Cost fitness : " + configuration.getGenome().getCostFitness());
//            configuration.getGenome().determineSpecialtyMatch(listMission, listEmployee);
//            System.out.println("Match specialty : " + configuration.getGenome().getSpecialtyMatch());
//
//        }
//        boolean isSimilar = false;
//        for (Genome genomeInPopulation : listBestGenomes) {
//            if (genomeInPopulation.getSimilarity(secondGenome)) {
//                isSimilar = true;
//                break;
//            }
//        }
//        if (!isSimilar) {
//            System.out.println("Genome not found in population");
//        } else  {
//            System.out.println("Genome found in population");
//        }
//
//        Configuration configuration = new Configuration(secondGenome, listMission, listEmployee, listCentre);
//        configuration.brutForceStep2();
//        System.out.println("Best cost from brut force step 2 algorithm after second algooo : " + configuration.getBestCost());

    }

    public void brutForceStep3(){

        Configuration configuration = new Configuration(secondGenome, listMission, listEmployee, listCentre);
        configuration.brutForceStep3(listMission, listEmployee);

        //Récupération du résultat
        Genome bestGenome = configuration.getGenome();

        bestGenome.displayGenome();
        
        bestGenome.deternimeFitnessWithoutChecking();
        System.out.println("Fitness : " + bestGenome.getFitness());
        this.centreAffected = bestGenome.getFitness();
        bestGenome.determineCostFitness(listMission, listEmployee);
        System.out.println("Cost fitness : " + bestGenome.getCostFitness());
        this.travelCost = bestGenome.getCostFitness();
        bestGenome.determineSpecialtyMatch(listMission, listEmployee);
        System.out.println("Match specialty : " + bestGenome.getSpecialtyMatch());
        this.matchingSpecialty = bestGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        bestGenome.instantiateGenome(listMission, listEmployee);


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

    public int getMatchingSpecialty() {return matchingSpecialty;}
}
