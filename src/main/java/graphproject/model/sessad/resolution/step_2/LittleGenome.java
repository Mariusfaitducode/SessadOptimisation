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
    private int operation;

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;

        this.listMission = new ArrayList<>(0);
        this.listEmployee = new ArrayList<>(0);

        this.operation = 0;
    }

    public double getBestSpecialtyMatch(){return bestSpecialtyMatch;}
    public double getBestCost(){return bestCost;}

    public Centre getCentre(){
        return this.centre;
    }

    public int getOperation(){
        return this.operation;
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

    public Genome evaluateAllCombinations(List<List<Integer>> combinations){

        double bestCost = 1000000000;
        Genome bestGenome = new Genome(listMission.size());
        int bestSpecialtyMatch = 0;

        for(List<Integer> combination : combinations){

            Genome.clearInstance(listMission, listEmployee);
            Genome genome = new Genome(listMission.size());

            for (int i = 0; i < combination.size(); i++){


                genome.setGene(i, combination.get(i) + 1);

//                Mission mission = listMission.get(i);
//                Employee employee = listEmployee.get(combination.get(i));
//
//                mission.setEmployee(employee);
//                employee.addMission(mission);
            }
            genome.determineFitness(listMission, listEmployee);
            //genome.instantiateGenome(listMission, listEmployee);

            double totalCost = 0;
            int specialtyMatch = 0;

            if (genome.getFitness() > 0){

                for (Employee employee : listEmployee){
                    totalCost += employee.findCost();
                    specialtyMatch += employee.nbrMissionWithGoodSpecialty();
                }
                if (totalCost < bestCost){
                    bestCost = totalCost;
                    bestGenome = genome;
                    bestSpecialtyMatch = specialtyMatch;
                }
                else if (totalCost == bestCost){

                    //Compare le match des spécialités entre genome et bestGenome
                    if (bestSpecialtyMatch < specialtyMatch){
                        bestGenome = genome;
                        bestSpecialtyMatch = specialtyMatch;
                    }
                }
                //System.out.println("Cost = "+ totalCost);
            }
        }
        this.bestCost = bestCost;
        this.bestSpecialtyMatch = bestSpecialtyMatch;

//        bestGenome.displayGenome();

        return bestGenome;
    }

    public List<List<Integer>> generateCombinations() {

        List<Integer> initialGenome = new ArrayList<>();

        for (Mission mission : listMission){
            for (int i = 0; i < listEmployee.size(); i++){
                if (mission.getEmployee().getId() == listEmployee.get(i).getId()){
                    initialGenome.add(i);
                }
            }
        }
        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinationsRecursive(initialGenome, new ArrayList<>(), combinations);

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

        List<List<Integer>> combinations = new ArrayList<>();

        generateCombinationsRecursive(listValues, new ArrayList<>(), combinations);

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

//        System.out.println(permutations.size() + " combination(s) generated by Brut Force 3");
        return permutations;
    }

    public List<int[]> generateCombinationsStep3() {

        List<Integer> initialGenome = new ArrayList<>();

        for (Mission mission : listMission){
            for (int i = 0; i < listEmployee.size(); i++){

                if (mission.getEmployee().getId() == listEmployee.get(i).getId()){
                    initialGenome.add(i);
                }
            }
        }

        List<int[]> combinations = generatePermutations(initialGenome);
        operation = combinations.size();
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

                Mission mission = listMission.get(i);
                Employee employee = listEmployee.get(combination[i]);

                mission.setEmployee(employee);
                employee.addMission(mission);

            }

            int specialtyMatch = 0;

            for (Employee employee : listEmployee){
                specialtyMatch += employee.nbrMissionWithGoodSpecialty();
            }

            if (specialtyMatch > bestSpecialtyMatch){
                bestSpecialtyMatch = specialtyMatch;
                bestGenome = new Genome(genome);
            }

        }
        this.bestSpecialtyMatch = bestSpecialtyMatch;
        return bestGenome;
    }

}
