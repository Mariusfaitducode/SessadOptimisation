package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static graphproject.model.sessad.SessadGestion.distCentreCentre;
import static graphproject.model.sessad.SessadGestion.distMissionCentre;

public class Population {

    Genome[] population;

    private int sizePopulation;

    public Population(int sizePopulation){
        this.sizePopulation = sizePopulation;
        //On définit une population de taille sizePopulation
        population = new Genome[sizePopulation];
    }

    public void initializePopulation( List<Mission> listMission, List<Centre> listCentre){
        int sizeGenome = listMission.size();



        List<Integer> listRef = new ArrayList<>(0);

        for (int i = 0; i < sizeGenome; i++){
            listRef.add(i);
        }



        int sizeCentre = distCentreCentre.length;

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
                //Ajout d'un chromosome
                population[i].addChromosome(listMission.get(c), centre);
            }
        }
        System.out.println("Population initialized : " + sizePopulation);
    }

    public void evaluatePopulation(List<Mission> listMission, List<Employee> listEmployee){
        for (Genome genome : population){

            Genome.clearInstance(listMission, listEmployee);

            genome.determineFitness(listMission, listEmployee);

//            System.out.println("Fitness : " + genome.fitness);
        }
    }

    public int getTotalFitness(){
        int totalFitness = 0;
        for (Genome genome : population){
            totalFitness += genome.fitness;
        }
        return totalFitness;
    }

    public int getMeanFitness(){
        int totalFitness = 0;
        for (Genome genome : population){
            totalFitness += genome.fitness;
        }
        return totalFitness / population.length;
    }

    public double getStandardDeviationFitness(){
        double meanFitness = getMeanFitness();
        double sum = 0;
        for (Genome genome : population){
            sum += Math.pow(genome.fitness - meanFitness, 2);
        }
        return Math.sqrt(sum / population.length);
    }

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

    public Genome[] selectParents(){
        double totalFitness = 0.0;
        for (Genome genome : population) {
            totalFitness += genome.fitness;
        }

        // Sélectionner un premier génome
        double randomValue1 = Math.random() * totalFitness;
        double cumulativeFitness = 0.0;
        Genome selectedGenome1 = null;
        for (Genome genome : population) {
            cumulativeFitness += genome.fitness;
            if (cumulativeFitness >= randomValue1) {
                selectedGenome1 = new Genome(genome);
                break;
            }
        }

        // Sélectionner un deuxième génome (assure-toi de ne pas sélectionner le même génome qu'auparavant)
        double randomValue2 = Math.random() * (totalFitness - selectedGenome1.fitness);
        cumulativeFitness = 0.0;
        Genome selectedGenome2 = null;
        for (Genome genome : population) {
            if (genome != selectedGenome1) {
                cumulativeFitness += genome.fitness;
                if (cumulativeFitness >= randomValue2) {
                    selectedGenome2 = new Genome(genome);
                    break;
                }
            }
        }

        Genome[] parents = new Genome[]{selectedGenome1, selectedGenome2};

        return parents;
    }

    public Genome[] crossover(Genome parent1, Genome parent2){

        int genomeLength = parent1.genome.length;

        int crossoverPoint = (int) (Math.random() * genomeLength); // genomeLength est la longueur du génome

        // Effectue le croisement
        Genome offspring1 = new Genome(genomeLength);
        Genome offspring2 = new Genome(genomeLength);

        for (int i = 0; i < genomeLength; i++) {
            if (i < crossoverPoint) {
                offspring1.setGene(i, parent1.getGene(i));
                offspring2.setGene(i, parent2.getGene(i));
            } else {
                offspring1.setGene(i, parent2.getGene(i));
                offspring2.setGene(i, parent1.getGene(i));
            }
        }
        Genome[] children = new Genome[]{offspring1, offspring2};

        return children;

    }

    public Genome getBestGenome(){

        double max = 0;
        Genome bestGenome = null;

        for (int i = 0; i < population.length; i++){

            if ( population[i].fitness > max){
                max = population[i].fitness;
                bestGenome = new Genome(population[i]);
            }
        }
        return bestGenome;
    }

    public Population selectBestElements() {

        Population newPopulation = new Population(this.population.length / 2);

        for (Genome genome : this.population) {

            for (int i = 0; i < newPopulation.population.length; i++) {

                if (newPopulation.population[i] == null) {
                    newPopulation.population[i] = genome;
                    break;
                } else if (genome.fitness > newPopulation.population[i].fitness) {
                    newPopulation.population[i] = genome;
                    break;
                }
            }
        }
        for (Genome genome : newPopulation.population) {
            genome.fitness = 0;
        }
        return newPopulation;
    }
    //------------------------------------------------------------------------------------------------------------------

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
    public void remplacementStrict(Genome child) {
        int worstFitness = Integer.MAX_VALUE;
        int worstFitnessIndex = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness < worstFitness) {
                worstFitness = population[i].fitness;
                worstFitnessIndex = i;
            }
        }
        population[worstFitnessIndex] = child;
    }

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
                            System.out.println("same employees : first version from Marius");
                            canAdd = false;
                        }
                    }
                    for (int i = 0; i < listGenome2.size(); i++){

                        if (listGenome2.get(i).getSimilarity(genome2)){
                            System.out.println("same centres : Added by Victor");
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

    public int getBestFitness() {
        int bestFitness = 0;
        for (Genome genome : population) {
            bestFitness = Math.max(bestFitness, genome.fitness);
        }
        return bestFitness;
    }

    public void getListBestGenomes(List<Mission> listMission) {

    }
}
