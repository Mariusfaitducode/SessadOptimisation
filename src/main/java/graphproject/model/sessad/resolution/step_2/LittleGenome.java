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

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;
    }

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


    public void evaluateAllCombinations(List<List<Integer>> combinations){

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

            if (genome.getFitness() > 0){

                for (Employee employee : listEmployee){

                    totalCost += employee.findCost();
                }
            }
            System.out.println("Cost = "+ totalCost);
        }
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

}
