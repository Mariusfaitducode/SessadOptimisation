package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class LittleGenome {

    private Centre centre;
    private Skill skill;
    private int day;
    private List<Mission> listMission;
    private List<Employee> listEmployee;

    private double bestCost;

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;

        this.listMission = new ArrayList<>(0);
        this.listEmployee = new ArrayList<>(0);
    }

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

    public void testAllPossibility(){}


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

                for (Employee employee : listEmployee){
                    totalCost += employee.findCost();
                    specialtyMatch += employee.nbrMissionWithGoodSpecialty();
                }

                if (totalCost < bestCost && totalCost != 0){
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
        //return listBestGenome;
        return bestGenome;
    }

    public List<List<Integer>> generateCombinations() {

        List<Integer> initialGenome = new ArrayList<>();

        System.out.println("Centre : "+ centre.getId());
        System.out.println("Skill : " + skill.toString());
        System.out.println("Day : "+ day);
        for (Mission mission : listMission){
            System.out.println("Mission : "+ mission.getId());
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

}
