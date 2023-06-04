package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.skill.Skill;

import java.util.*;

public class LittleGenome {

    private Centre centre;
    private Skill skill;
    private int day;
    private List<Mission> listMission;
    private List<Employee> listEmployee;

    private double bestCost;

    private int bestSpecialtyMatch;

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;

        this.listMission = new ArrayList<>(0);
        this.listEmployee = new ArrayList<>(0);
    }

    public double getBestSpecialtyMatch(){return bestSpecialtyMatch;}
    public double getBestCost(){return bestCost;}

    public Centre getCentre(){
        return this.centre;
    }

    public Skill getSkill(){
        return this.skill;
    }

    public int getDay(){
        return this.day;
    }

    public List<Mission> getListMission(){
        return this.listMission;
    }

    public List<Employee> getListEmployee(){
        return this.listEmployee;
    }

    public void addMission(Mission mission){
        this.listMission.add(mission);
    }

    public void addEmployee(Employee employee){
        this.listEmployee.add(employee);
    }

    public void determineWithGenetic(){

        List<Centre> listCentre = new ArrayList<>(0);
        listCentre.add(centre);

        Genetic genetic = new Genetic(listMission, listCentre, listEmployee, 200);
    }


    public Genome evaluateAllCombinations(List<List<Integer>> combinations){

        double bestCost = 1000000000;
        Genome bestGenome = new Genome(listMission.size());
        int bestSpecialtyMatch = 0;

        for(List<Integer> combination : combinations){

            Genome.clearInstance(listMission, listEmployee);
            Genome genome = new Genome(listMission.size());

            for (int i = 0; i < combination.size(); i++){


                genome.setGene(i, combination.get(i) + 1);

                //Mission mission = listMission.get(i);
                //Employee employee = listEmployee.get(combination.get(i));

                //mission.setEmployee(employee);
                //employee.addMission(mission);
            }
            genome.determineFitness(listMission, listEmployee);
            //genome.instantiateGenome(listMission, listEmployee);

            double totalCost = 0;
            int specialtyMatch = 0;

            if (genome.getFitness() > 0){

                //Genome.clearInstance(listMission, listEmployee);
                //genome.instantiateGenome(listMission, listEmployee);

                for (Employee employee : listEmployee){
                    totalCost += employee.findCost();
                    specialtyMatch += employee.nbrMissionWithGoodSpecialty();
                }
                if (totalCost < bestCost){
                    bestCost = totalCost;
                    bestGenome = genome;
                    bestSpecialtyMatch = specialtyMatch;
                    //listBestGenome.clear();
                    //listBestGenome.add(genome);
                }
                else if (totalCost == bestCost){

                    //Compare le match des spécialités entre genome et bestGenome
                    if (bestSpecialtyMatch < specialtyMatch){
                        bestGenome = genome;
                    }
                }
                //System.out.println("Cost = "+ totalCost);
            }
        }
        this.bestCost = bestCost;
        this.bestSpecialtyMatch = bestSpecialtyMatch;
        //return listBestGenome;

//        bestGenome.displayGenome();

        return bestGenome;
    }

    public List<List<Integer>> generateCombinations(boolean withGenetic) {

        List<Integer> initialGenome = new ArrayList<>();

        System.out.println("Centre : "+ centre.getId());
        System.out.println("Skill : " + skill.toString());
        System.out.println("Day : "+ day);
        for (Mission mission : listMission){
            System.out.println("Mission : "+ mission.getId() + " Employee : "+ mission.getEmployee().getId());
            for (int i = 0; i < listEmployee.size(); i++){

                if (mission.getEmployee().getId() == listEmployee.get(i).getId()){
                    initialGenome.add(i);
                }
            }
        }
        List<List<Integer>> combinations = new ArrayList<>();

        if (withGenetic){
            generateUniqueCombinationsRecursive(initialGenome, new ArrayList<>(), combinations, new HashSet<>());
            //generateUniqueCombinationsRecursive(initialGenome, 0, new ArrayList<>(), combinations);
        }
        else {
            generateCombinationsRecursive(initialGenome, new ArrayList<>(), combinations);
        }

        return combinations;
    }

    private static void generateCombinationsRecursive(List<Integer> remaining, List<Integer> currentCombination, List<List<Integer>> combinations) {
        if (remaining.isEmpty()) {
            combinations.add(new ArrayList<>(currentCombination));
        } else {
            for (int i = 0; i < remaining.size(); i++) {
                int currentNumber = remaining.get(i);
                currentCombination.add(currentNumber);

                List<Integer> remainingNumbers = new ArrayList<>(remaining);
                remainingNumbers.remove(i);

                generateCombinationsRecursive(remainingNumbers, currentCombination, combinations);

                currentCombination.remove(currentCombination.size() - 1);

                while (i + 1 < remaining.size() && remaining.get(i + 1) == currentNumber) {
                    i++;
                }
            }
        }
    }

    private static void generateCombinationsRecursive(List<Integer> remaining, List<Integer> currentCombination, List<List<Integer>> combinations, int maxCombinations) {

        if (combinations.size() >= maxCombinations || currentCombination.size() >= remaining.size()) {
            if (currentCombination.size() >= remaining.size()) {
                combinations.add(new ArrayList<>(currentCombination));
            }
            return; // Sortir de la récursion si le nombre maximum de combinaisons est atteint ou si tous les éléments ont été utilisés
        }


        for (int i = 0; i < remaining.size(); i++) {
            int currentNumber = remaining.get(i);
            currentCombination.add(currentNumber);

            List<Integer> remainingNumbers = new ArrayList<>(remaining);
            remainingNumbers.remove(i);

            generateCombinationsRecursive(remainingNumbers, currentCombination, combinations);

            currentCombination.remove(currentCombination.size() - 1);

            while (i + 1 < remaining.size() && remaining.get(i + 1) == currentNumber) {
                i++;
            }
        }
    }

    private static void generateUniqueCombinationsRecursive(List<Integer> numbers, List<Integer> currentCombination, List<List<Integer>> combinations, Set<Integer> usedIndices) {
        if (currentCombination.size() == numbers.size()) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = 0; i < numbers.size(); i++) {
            if (usedIndices.contains(i) || (i > 0 && numbers.get(i) == numbers.get(i - 1) && !usedIndices.contains(i - 1))) {
                continue; // Skip duplicate values or values that have already been used
            }

            currentCombination.add(numbers.get(i));
            usedIndices.add(i);

            generateUniqueCombinationsRecursive(numbers, currentCombination, combinations, usedIndices);

            currentCombination.remove(currentCombination.size() - 1);
            usedIndices.remove(i);
        }
    }


    public static List<int[]>  generatePermutations(List<Integer> numbers){

        List<Integer> listValues = new ArrayList<>();

        List<List<Integer>> listListIndices = new ArrayList<>();

        //prépare la liste des valeurs différentes
        for (int i = 0; i < numbers.size(); i++){
            if (!listValues.contains(numbers.get(i))){
                listValues.add(numbers.get(i));
            }
        }


        //prépare la liste des indices pour chaque valeur
        for (int i = 0; i < listValues.size(); i++){

            List<Integer> listIndices = new ArrayList<>();
            for (int j = 0; j < numbers.size(); j++){

                if (Objects.equals(listValues.get(i), numbers.get(j))){
                    listIndices.add(j);
                }
            }
            listListIndices.add(listIndices);
        }

        //affiche la liste des valeurs
        System.out.println("Liste des valeurs : "+ listValues);

        //affiche la liste des indices pour chaque valeur
        System.out.println("Liste des indices pour chaque valeur : "+ listListIndices);

        List<List<Integer>> combinations = new ArrayList<>();

        generateCombinationsRecursive(listValues, new ArrayList<>(), combinations);

        //affiche la liste des combinaisons
        System.out.println("Liste des combinaisons : "+ combinations);

        List<int[]> permutations = new ArrayList<>();

        for (List<Integer> combination : combinations){

            int[] permutation = new int[numbers.size()];

            for (int i = 0; i < combination.size(); i++){

                for (Integer indices : listListIndices.get(i)){
                    permutation[indices] = combination.get(i);
                }
            }
            permutations.add(permutation);
        }

        System.out.println("Liste des permutations : "+ permutations);

        for (int[] permutation : permutations){
            for (int i : permutation){
                System.out.print(i + " ");
            }
            System.out.println();
        }
        return permutations;
    }

    public List<int[]> generateCombinationsStep3() {

        List<Integer> initialGenome = new ArrayList<>();

        System.out.println("Centre : "+ centre.getId());
        System.out.println("Skill : " + skill.toString());
        System.out.println("Day : "+ day);

        for (Mission mission : listMission){
            System.out.println("Mission : "+ mission.getId() + " Employee : "+ mission.getEmployee().getId());
            
            for (int i = 0; i < listEmployee.size(); i++){

                if (mission.getEmployee().getId() == listEmployee.get(i).getId()){
                    initialGenome.add(i);
                }
            }
        }
        List<int[]> combinations = generatePermutations(initialGenome);

        return combinations;
    }

    public Genome evaluateAllCombinationsStep3(List<int[]> combinations){

        Genome bestGenome = new Genome(listMission.size());
        int bestSpecialtyMatch = 0;

        for(int[] combination : combinations){

            Genome.clearInstance(listMission, listEmployee);
            Genome genome = new Genome(listMission.size());

            for (int i = 0; i < combination.length; i++){

                genome.setGene(i, combination[i]);

            }
            //genome.determineCostFitness(listMission, listEmployee);
            //genome.instantiateGenome(listMission, listEmployee);

            int specialtyMatch = 0;

            Genome.clearInstance(listMission, listEmployee);
            genome.instantiateGenome(listMission, listEmployee);

            for (Employee employee : listEmployee){
                specialtyMatch += employee.nbrMissionWithGoodSpecialty();
            }
            System.out.println("Specialty match = "+ specialtyMatch);

            if (specialtyMatch > bestSpecialtyMatch){
                bestSpecialtyMatch = specialtyMatch;
                bestGenome = new Genome(genome);
            }

            //System.out.println("Cost = "+ totalCost);

        }
        System.out.println("Best specialty match = "+ bestSpecialtyMatch);
        this.bestSpecialtyMatch = bestSpecialtyMatch;

        //bestGenome.displayGenome();
        return bestGenome;
    }

}
