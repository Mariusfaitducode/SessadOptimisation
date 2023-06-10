package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.genetic.Population;
import graphproject.model.sessad.resolution.step_2.Configuration;

import java.util.List;

// Cette class regroupe tous les algorithmes et résoudre le problème d'une seule traite
public class Resolution {

    // Il est donc finalement caractérisé par les 3 paramètres principaux
    int centreAffected = 0;
    double travelCost = 0;
    int matchingSpecialty = 0;
    private List<Mission> listMission;
    private List<Centre> listCentre;
    private List<Employee> listEmployee;
    private Genetic genetic;

    // Ce sont les génomes ressortissants des algorithmes génétiques 1 et 2.
    public Genome firstGenome;
    public Genome secondGenome;

    public Resolution(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.listEmployee = listEmployee;

        //this.nbrCentre = listCentre.size();
        genetic = new Genetic(listMission, listCentre, listEmployee, 500);
    }

    // Initialisation de notre population de solutions initiales et affiche les stats
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

    // Lancement de l'algorithme génétique pour l'affectation des missions
    public void startGeneticAlgo(int popSize, int generationNbr, double crossOverRate, double mutationRate) {

        //Première étape de l'algorithme génétique pour l'affectation des missions
        firstGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        // Get stats of the best genome
        this.centreAffected = firstGenome.getFitness();
        this.travelCost = firstGenome.getCostFitness();
        this.matchingSpecialty = firstGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        firstGenome.instantiateGenome(listMission, listEmployee);

    }

    // Lancement de l'algorithme génétique pour la minimisation des coûts de déplacement
    public void secondPartGenetic(int generationNbr, double crossOverRate, double mutationRate){

        //Deuxième étape de l'algorithme génétique pour la minimisation des coûts de déplacement
        secondGenome = genetic.secondPartGeneticAlgo(generationNbr, crossOverRate, mutationRate, this.centreAffected);

        //calculateAttributeGenome(secondGenome, listMission, listEmployee);

        this.centreAffected = secondGenome.getFitness();
        this.travelCost = secondGenome.getCostFitness();
        this.matchingSpecialty = secondGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        secondGenome.instantiateGenome(listMission, listEmployee);

    }

    // Lancement de l'algorithme permettant de maximiser le nombre de spécialités respectées
    // Nous prenons le meilleur génome trouvé en terme de coût de déplacement et de nombre de centres affectés
    // et nous permuttons les itinéraires des employés pour maximiser le nombre de spécialités respectées
    public void brutForceStep3(){

        Configuration configuration = new Configuration(secondGenome, listMission, listEmployee, listCentre);
        configuration.brutForceStep3(listMission, listEmployee);

        //Récupération du résultat
        Genome bestGenome = configuration.getGenome();
        bestGenome.deternimeFitnessWithoutChecking();
        bestGenome.determineCostFitness(listMission, listEmployee);
        bestGenome.determineSpecialtyMatch(listMission, listEmployee);


        System.out.println();
        System.out.println("--------------Result Brut Force Step 3---------------");
        System.out.println("Nombre de possibilités testées : " + configuration.getTotOperation());

        System.out.println("--------------Best genome---------------");

        bestGenome.displayGenome();
        System.out.println("Fitness : " + bestGenome.getFitness());
        System.out.println("Cost fitness : " + bestGenome.getCostFitness());
        System.out.println("Match specialty : " + bestGenome.getSpecialtyMatch());

        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");
        System.out.println();

        this.centreAffected = bestGenome.getFitness();
        this.travelCost = bestGenome.getCostFitness();
        this.matchingSpecialty = bestGenome.getSpecialtyMatch();

        //Instantiation de Sessad Gestion
        Genome.clearInstance(listMission, listEmployee);
        bestGenome.instantiateGenome(listMission, listEmployee);


    }

    public int getCentreAffected() {
        return centreAffected;
    }

    public double getTravelCost() {
        return travelCost;
    }

    public int getMatchingSpecialty() {return matchingSpecialty;}
}
