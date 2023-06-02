package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    Genome genome;
    List<LittleGenome> listLittleGenome;

    public Configuration(Genome genome, List<Mission> listMission, List<Employee> listEmployee, List<Centre> listCentre){

        this.genome = genome;
        this.listLittleGenome = new ArrayList<>(0);

        // Creation des LittleGenome, il y en a : n centres X 2 skills X 5 days
        initializeLittleGenome(listCentre);

        genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);

        // On ajoute les employees aux LittleGenome
        addEmployeeToLittleGenome(listEmployee);

        // On split le genome en LittleGenome
        splitGenomeIntoLittleGenome(genome, listMission, listEmployee, listCentre);

        double totalCost = 0;

        for (LittleGenome littleGenome : listLittleGenome){
            if (!littleGenome.getListMission().isEmpty()){
                List<List<Integer>> combinations = littleGenome.generateCombinations();

                Genome bestGenome = new Genome(littleGenome.getListMission().size());
                bestGenome = littleGenome.evaluateAllCombinations(combinations);

                bestGenome.displayGenome();

                adaptGenome(bestGenome, littleGenome);

                //System.out.println("Same solutions = "+ bestCombinations.size());

                totalCost += littleGenome.getBestCost();
            }
        }

        System.out.println("Total cost = " + (float)totalCost);


    }

    public Genome getGenome(){return genome;}

    public void adaptGenome(Genome miniGenome, LittleGenome littleGenome){

        for (int i = 0; i < miniGenome.getGenome().length; i++){

            Mission mission = littleGenome.getListMission().get(i);
            Employee employee = littleGenome.getListEmployee().get(miniGenome.getGene(i) - 1);

            this.genome.setGene(mission.getId() - 1, employee.getId() - 1);
        }
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
}
