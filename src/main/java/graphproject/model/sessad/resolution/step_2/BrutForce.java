package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class BrutForce {

    Genome genome;
    List<LittleGenome> listLittleGenome;

    double bestCost;

    int bestSpecialtyMatch;

    public BrutForce(Genome genome, List<Mission> listMission, List<Employee> listEmployee, List<Centre> listCentre){

        this.genome = new Genome(genome.getGenome().length);
        this.listLittleGenome = new ArrayList<>(0);

        // Creation des LittleGenome, il y en a : n centres X 2 skills X 5 days
        initializeLittleGenome(listCentre);

        Genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);

        // On ajoute les employees aux LittleGenome
        addEmployeeToLittleGenome(listEmployee);

        // On split le genome en LittleGenome
        splitGenomeIntoLittleGenome(genome, listMission, listEmployee, listCentre);

    }

    public double getBestCost(){return bestCost;}
    public int getBestSpecialtyMatch(){return bestSpecialtyMatch;}
    public Genome getGenome(){return genome;}

    public void adaptGenome(Genome miniGenome, LittleGenome littleGenome){

        for (int i = 0; i < miniGenome.getGenome().length; i++){

            Mission mission = littleGenome.getListMission().get(i);
            Employee employee = littleGenome.getListEmployee().get(miniGenome.getGene(i));

            this.genome.setGene(mission.getId()-1, employee.getId());
        }
        this.genome.displayGenome();
    }

    private void initializeLittleGenome(List<Centre> listCentre){

        for (Centre centre : listCentre) {

            for (Skill skill : Skill.values()) {

                for (int day = 1; day <= 5; day++) {

                    LittleGenome littleGenome = new LittleGenome(centre, skill, day);

                    listLittleGenome.add(littleGenome);
                }
            }
        }
    }

    private void splitGenomeIntoLittleGenome(Genome genome, List<Mission> listMission, List<Employee> listEmployee, List<Centre> listCentre){
        for (int i = 0; i < genome.getSizeGenome(); i++){

            if (genome.getGene(i) != 0){

                Mission mission = listMission.get(i);

                Centre centre = mission.getEmployee().getCentre();
                Skill skill = mission.getSkill();
                int day = mission.getDay();

                LittleGenome littleGenome = getLittleGenome(centre, skill, day);
                littleGenome.addMission(mission);
            }
        }
    }

    private void addEmployeeToLittleGenome(List<Employee> listEmployee){
        for (LittleGenome littleGenome : listLittleGenome){
            for (Employee employee : listEmployee) {
                if (employee.getCentre().equals(littleGenome.getCentre()) && employee.getSkill().equals(littleGenome.getSkill())){
                    littleGenome.addEmployee(employee);
                }
            }
        }
    }

    public LittleGenome getLittleGenome(Centre centre, Skill skill, int day){
        for (LittleGenome littleGenome : listLittleGenome){
            if (littleGenome.getCentre().equals(centre) && littleGenome.getSkill().equals(skill) && littleGenome.getDay() == day){
                return littleGenome;
            }
        }
        return null;
    }

    public void brutForceStep2() {
        double totalCost = 0;
        int totalSpecialtyMatch = 0;

        //Resolution du génome
        for (LittleGenome littleGenome : listLittleGenome){
            if (!littleGenome.getListMission().isEmpty()){

                List<List<Integer>> combinations = littleGenome.generateCombinations();
                Genome bestGenome = littleGenome.evaluateAllCombinations(combinations);

                adaptGenome(bestGenome, littleGenome);
                totalCost += littleGenome.getBestCost();
                totalSpecialtyMatch += littleGenome.getBestSpecialtyMatch();

            }
        }

//        System.out.println("Total cost = " + (float)totalCost);
        this.bestCost = totalCost;
        this.bestSpecialtyMatch = totalSpecialtyMatch;
    }

    public void brutForceStep3(List<Mission> listMission, List<Employee> listEmployee) {

        int totalSpecialtyMatch = 0;

        for (LittleGenome littleGenome : listLittleGenome) {

            if (!littleGenome.getListMission().isEmpty() && !littleGenome.getListEmployee().isEmpty()) {

                System.out.println("----------------------");
                System.out.println("Centre : " + littleGenome.getCentre().getId() + ", Specialty : " + littleGenome.getSkill() + ", Day : " + littleGenome.getDay());

                List<int[]> permutations = littleGenome.generateCombinationsStep3();
                Genome bestGenome = littleGenome.evaluateAllCombinationsStep3(permutations);

                totalSpecialtyMatch += littleGenome.getBestSpecialtyMatch();

                //bestGenome.displayGenome();

//                for (int i = 0; i < littleGenome.getListEmployee().size(); i++) {
//                    System.out.println("Employee " + i + " : " + littleGenome.getListEmployee().get(i).getId());
//                }
//                for (int i = 0; i < littleGenome.getListMission().size(); i++) {
//                    System.out.println("Mission " + i + " : " + littleGenome.getListMission().get(i).getId());
//                }


                //totalSpecialtyMatch += littleGenome.getBestSpecialtyMatch();


                adaptGenome(bestGenome, littleGenome);
            }
        }
        System.out.println("Total specialty match = " + totalSpecialtyMatch);
        this.genome.displayGenome();

        this.genome.determineSpecialtyMatch(listMission, listEmployee);

        System.out.println("Total specialty match calculated = " + this.genome.getSpecialtyMatch());
    }
}
