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

    double bestCost;

    int bestSpecialtyMatch;

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

    }

    public double getBestCost(){return bestCost;}
    public int getBestSpecialtyMatch(){return bestSpecialtyMatch;}
    public Genome getGenome(){return genome;}

    public void adaptGenome(Genome miniGenome, LittleGenome littleGenome){

        for (int i = 0; i < miniGenome.getGenome().length; i++){

            //TODO : Erreur qui arrive parfois, à corriger
            Mission mission = littleGenome.getListMission().get(i);
            Employee employee = littleGenome.getListEmployee().get(miniGenome.getGene(i) - 1);

            this.genome.setGene(mission.getId() - 1, employee.getId());
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

    public void brutForceStep2() {
        double totalCost = 0;
        int totalSpecialtyMatch = 0;

        //Resolution du génome
        for (LittleGenome littleGenome : listLittleGenome){
            if (!littleGenome.getListMission().isEmpty()){

                if (littleGenome.getListMission().size() > 10){
//                    System.out.println("LittleGenome size = " + littleGenome.getListMission().size());

                    List<Centre> littleGenomeCentre = new ArrayList<>(0);
                    littleGenomeCentre.add(littleGenome.getCentre());

                    List<List<Integer>> combinations = littleGenome.generateCombinations(true);

                    //Genetic genetic = new Genetic(littleGenome.getListMission(), littleGenomeCentre, littleGenome.getListEmployee(), 200);
                    //genetic.littleGeneticAlgo(combinations, 200, 1000, 0.9, 0.9);
                }
                else{
                    List<List<Integer>> combinations = littleGenome.generateCombinations(false);

                    Genome bestGenome;

                    bestGenome = littleGenome.evaluateAllCombinations(combinations);

//                    bestGenome.displayGenome();

                    adaptGenome(bestGenome, littleGenome);

                    //System.out.println("Same solutions = "+ bestCombinations.size());

                    totalCost += littleGenome.getBestCost();
                    totalSpecialtyMatch += littleGenome.getBestSpecialtyMatch();
                }

            }
        }

//        System.out.println("Total cost = " + (float)totalCost);
        this.bestCost = totalCost;
        this.bestSpecialtyMatch = totalSpecialtyMatch;
    }

    public void brutForceStep3() {
        for (LittleGenome littleGenome : listLittleGenome) {
            System.out.println("----------------------");
            if (!littleGenome.getListMission().isEmpty()) {
                littleGenome.generateCombinationsStep3();
            }
        }
    }
}
