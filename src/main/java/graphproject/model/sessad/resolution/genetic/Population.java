package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static graphproject.model.sessad.SessadGestion.distMissionCentre;

public class Population {

    Genome[] population;

    private int sizePopulation;

    public Population(int sizePopulation){
        this.sizePopulation = sizePopulation;
        //On définit une population de taille sizePopulation
        population = new Genome[sizePopulation];
    }

    // Fonction permettant d'initialiser la population en suivant le principe du plus proche voisin
    public void initializePopulation(List<Mission> listMission, List<Centre> listCentre){
        int sizeGenome = listMission.size();

        List<Integer> listRef = new ArrayList<>(0);

        for (int i = 0; i < sizeGenome; i++){
            listRef.add(i);
        }

        int sizeCentre = listCentre.size();

        //Parcours de la population
        for (int i = 0; i < sizePopulation; i++){

            //Pour chaque génome on réinitialise la liste des missions et des centres

            for (Mission mission : listMission){
                mission.setEmployee(null);
            }
            for (Centre centre : listCentre){
                for (Employee employee : centre.getListEmployee()){
                    employee.setListMission(new ArrayList<>(0));
                }
            }

            //On définit un génome de taille sizeGenome
            population[i] = new Genome(sizeGenome);

            //Parcours des missions / chromosomes de manière aléatoire

            Collections.shuffle(listRef);

            for (Integer c : listRef){

//                System.out.println("c : "+c);

                double min = 1000000000;
                Centre centre = null;

                //Parcours des centres
                for (int l = 0; l < sizeCentre; l++){

                    //Détermine le centre le plus proche
                    if (distMissionCentre[c][l] < min){

                        min = distMissionCentre[c][l];

                        centre = listCentre.get(l);
                    }
                }
                //Ajout d'un chromosome avec le centre le plus proche
                population[i].addChromosome(listMission.get(c), centre);
            }
        }
        System.out.println("Population initialized : " + sizePopulation);
    }

    //Permet d'évaluer toute la population
    public void evaluatePopulation(List<Mission> listMission, List<Employee> listEmployee){
        for (Genome genome : population){

            Genome.clearInstance(listMission, listEmployee);

            genome.determineFitness(listMission, listEmployee);

//            System.out.println("Fitness : " + genome.fitness);
        }
    }

    //Permet d'évaluer le coût de toute la population
    public double evaluateCostPopulation(List<Mission> listMission, List<Employee> listEmployee, double bestFitness){

        double maxCost = 0;
        for (Genome genome : population){

            Genome.clearInstance(listMission, listEmployee);

            genome.determineFitness(listMission, listEmployee);

            if (genome.fitness < bestFitness){
                genome.fitness = 0;
            }

            genome.determineCostFitness(listMission, listEmployee);

            if (genome.costFitness > maxCost){
                maxCost = genome.costFitness;
            }
        }
        return maxCost;
    }

    // Permet de faire la moyenne des fitness de la population
    public int getMeanFitness(){
        int totalFitness = 0;
        for (Genome genome : population){
            totalFitness += genome.fitness;
        }
        return totalFitness / population.length;
    }

    // Permet de faire la moyenne des coûts de la population
    public double getMeanCostFitness(){
        double totalCostFitness = 0;
        for (Genome genome : population){
            totalCostFitness += genome.costFitness;
        }
        return totalCostFitness / population.length;
    }

    // Permet de déterminer l'écart type des fitness de la population
    public double getStandardDeviationFitness(){
        double meanFitness = getMeanFitness();
        double sum = 0;
        for (Genome genome : population){
            sum += Math.pow(genome.fitness - meanFitness, 2);
        }
        return Math.sqrt(sum / population.length);
    }

    // Permet de déterminer l'écart type des coûts de la population
    public double getStandardDeviationCostFitness() {
        double meanCostFitness = getMeanCostFitness();
        double sum = 0;
        for (Genome genome : population){
            sum += Math.pow(genome.costFitness - meanCostFitness, 2);
        }
        return Math.sqrt(sum / population.length);
    }

    // Permet de d'avoir une idée du taux de similarité dans la population
    public double getSimilarityRate() {
        double similarityRate = 0;
        for (int i = 0; i < population.length - 1; i++) {
            for (int j = i + 1; j < population.length; j++) {
                if (population[i].getSimilarity(population[j])) {
                    similarityRate++;
                }
            }
        }
        return similarityRate;
    }

    public void displayPopulation(){
        for (Genome genome : population){
            genome.displayGenome();
        }
    }

    // Retourne le meilleur génome de la population en terme de coût et d'affectation
    public Genome getBestGenome(){

        int maxFitness = 0;
        double minCost = 1000;
        Genome bestGenome = null;

        for (int i = 0; i < population.length; i++){

            if ( population[i].fitness >= maxFitness){
                maxFitness = population[i].fitness;

                if (population[i].costFitness <= minCost){
                    minCost = population[i].costFitness;
                    bestGenome = new Genome(population[i]);
                }

            }
        }
        bestGenome.fitness = maxFitness;
        bestGenome.costFitness = minCost;
        return bestGenome;
    }

    // Permet de sélectionner un génome de manière aléatoire en prenant en compte le fitness de chaque génome
    // Plus le fitness est élevé, plus le génome a de chance d'être sélectionné
    public Genome selectionRoulette() {
        // Calculer la somme des fitness de la population
        int totalFitness = 0;
        for (Genome genome : population) {
            totalFitness += genome.fitness;
        }

        // Retourne
        int randomNbr = (int)(Math.random() * totalFitness);
        int cumulativeFitness = 0;

        for (Genome genome : population) {
            cumulativeFitness += genome.fitness;
            if (cumulativeFitness >= randomNbr) {
                return genome;
            }
        }
        return null;
    }

    // Permet de sélectionner un génome de manière aléatoire en prenant en compte le coût de chaque génome
    // Plus le coût est élevé, plus le génome a de chance d'être sélectionné mais seulement si sa fitness est valide
    public Genome selectionCostRoulette() {

        List<Genome> listGenome = new ArrayList<Genome>();

        double maxCost = 0;

        for (Genome genome : population) {
            maxCost = Math.max(maxCost, genome.costFitness);
        }

        // Calculer la somme des fitness de la population
        int totalCostFitness = 0;
        for (Genome genome : population) {

            if (genome.fitness != 0){
                totalCostFitness += (maxCost - genome.costFitness);

                listGenome.add(genome);
            }
        }

        // Retourne
        int randomNbr = (int)(Math.random() * totalCostFitness);
        int cumulativeFitness = 0;

        for (Genome genome : listGenome) {
            cumulativeFitness += (maxCost - genome.costFitness);
            if (cumulativeFitness >= randomNbr) {
                return genome;
            }
        }
        return population[0];
    }

    // Permet de croiser deux génomes pour en créer deux nouveaux
    // Le croisement se fait à un point aléatoire
    public void crossOver(Genome parent1, Genome parent2, Genome child1, Genome child2) {

        int genomeLength = parent1.genome.length;

        int crossoverPoint = (int) (Math.random() * genomeLength); // genomeLength est la longueur du génome

        for (int i = 0; i < genomeLength; i++) {
            if (i < crossoverPoint) {
                child1.setGene(i, parent1.getGene(i));
                child2.setGene(i, parent2.getGene(i));
            } else {
                child1.setGene(i, parent2.getGene(i));
                child2.setGene(i, parent1.getGene(i));
            }
        }
    }

    // Permet de choisir un des plus mauvais génome de la population et de le remplacer par un enfant
    public void remplacementRoulette(Genome child) {
        // Calculer la somme des fitness de la population
        int totalFitness = 0;
        int bestFitness = 0;
        for (Genome genome : population) {
            bestFitness = Math.max(bestFitness, genome.fitness);
        }

        for (Genome genome : population) {
            totalFitness += (bestFitness - genome.fitness);
        }

        // Retourne
        int randomNbr = (int)(Math.random() * totalFitness);
        int cumulativeFitness = 0;

        for (int i = 0; i < population.length; i++) {
            cumulativeFitness += (bestFitness - population[i].fitness);
            if (cumulativeFitness >= randomNbr) {
                population[i] = child;
                break;
            }
        }
    }

    // Permet de choisir un des plus mauvais génome de la population et de le remplacer par un enfant en fonction du coût

    public void remplacementCostRoulette(Genome child) {
        // Calculer la somme des fitness de la population
        int totalFitness = 0;

        for (Genome genome : population) {
            totalFitness += genome.costFitness;
        }

        // Retourne
        int randomNbr = (int)(Math.random() * totalFitness);
        int cumulativeFitness = 0;

        for (int i = 0; i < population.length; i++) {
            cumulativeFitness += population[i].costFitness;
            if (cumulativeFitness >= randomNbr) {

                population[i] = new Genome(child);

                break;
            }
        }
    }

    // Permet de remplacer un génome de la population non valide par le meilleur génome de la population précédente
    public void replaceWithBestSolution(Genome bestChild){

        for (int i = 0; i < population.length; i++) {

            if (population[i].fitness == 0) {

                population[i] = new Genome(bestChild);

                break;
            }
        }
    }

    //Retourne la liste des génomes ayant la même fitness

    public List<Genome> getFitnessPopulation(List<Employee> listEmployee){
        List<Genome> listGenome = new ArrayList<>(0);
        List<Genome> listGenome2 = new ArrayList<>(0);

        int size = 0;
        int bestFitness = getBestFitness();

        for (Genome genome : population){
            Genome genome2 = new Genome(genome.getSizeGenome());
            for (int i = 0; i<genome.getSizeGenome(); i++) {
                genome2.setGene(i, genome.getGene(i));
            }
            genome2.convertEmployeeToCentre(listEmployee);

            if (genome.fitness == bestFitness){
                size++;

                if (listGenome.size() == 0){
                    listGenome.add(genome);
                    listGenome2.add(genome2);

                }
                else{
                    boolean canAdd = true;
                    for (int i = 0; i < listGenome.size(); i++){

                        if (listGenome.get(i).getSimilarity(genome)){
                            canAdd = false;
                        }
                    }

                    if (canAdd){
                        listGenome.add(genome);
                        listGenome2.add(genome2);

                    }
                }
            }
        }
        System.out.println("Number of genome of the population with the same size : "+ size);

        return listGenome;
    }

    // Détermine la meilleutre fitness de la population
    public int getBestFitness() {
        int bestFitness = 0;
        for (Genome genome : population) {
            bestFitness = Math.max(bestFitness, genome.fitness);
        }
        return bestFitness;
    }


    public Genome getRandomGenome() {
        int randomIndex = (int) (Math.random() * (sizePopulation));
        return population[randomIndex];
    }
}
