package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.resolution.genetic.Genetic.lastIdEmployee;
import static graphproject.model.sessad.resolution.genetic.Genetic.mutationRate;

public class Genome{
    int[] genome;
    int fitness;

    public Genome(int sizeGenome){
        this.genome = new int[sizeGenome];
        this.fitness = 0;
    }

    public Genome(Genome genome){
        this.genome = genome.genome;
        this.fitness = genome.fitness;
    }

    public void setGene(int index, int gene){
        this.genome[index] = gene;
    }

    public int getGene(int index){
        return this.genome[index];
    }

    public int getSizeGenome(){
        return this.genome.length;
    }

    public void addChromosome(Mission mission, Centre centre){

        for (Employee employee : centre.getListEmployee()){

            if (employee.canTakeMission(mission)){

                genome[mission.getId() - 1] = employee.getId();

                mission.setEmployee(employee);
                employee.addMission(mission);

                break;
            }
            else{

                genome[mission.getId() - 1] = 0;
            }

        }
    }

    public void determineFitness(List<Mission> listMission, List<Employee> listEmployee){

        //displayGenome();

        //System.out.println();
        this.fitness = 0;
        for (int i = 0; i < genome.length; i++){

            Mission mission = listMission.get(i);

            if (genome[i] != 0){

                Employee employee = listEmployee.get(genome[i] - 1);

                if (employee.canTakeMission(mission)){

                    mission.setEmployee(employee);
                    employee.addMission(mission);

                    this.fitness++;
                }
                else {
                    this.fitness = 0;
                    break;
                }
            }
        }
    }

    public void mutation(List<Mission> listMission, List<Employee> listEmployee){
        for (int i = 0; i < genome.length; i++) {
            if (Math.random() < mutationRate) { // mutationRate est une valeur entre 0 et 1, représentant la probabilité de mutation
                // Effectue la mutation sur le gène
                //offspring1.mutateGene(i);

                int lastVal = genome[i];

                int val = (int)(Math.random() * lastIdEmployee);
                genome[i] = val;

                //TODO complexity improvement : validity by only checking new mutation
                determineFitness(listMission, listEmployee);

                if (fitness == 0){
                    genome[i] = lastVal;
                }
            }
        }
    }

    public void displayGenome(){
        System.out.println("Genome : ");
        for (int i = 0; i < genome.length; i++){
            System.out.print(genome[i] + " ");
        }
        System.out.println();
    }


    public void instantiateGenome(List<Mission> listMission, List<Employee> listEmployee){

        clearInstance(listMission, listEmployee);

        for (int i = 0; i < genome.length; i++){

            Mission mission = listMission.get(i);

            if (genome[i] != 0){

                Employee employee = listEmployee.get(genome[i] - 1);

                mission.setEmployee(employee);
                employee.addMission(mission);
            }
        }
    }

    public void clearInstance(List<Mission> listMission, List<Employee> listEmployee){
        for (Mission mission : listMission){
            mission.setEmployee(null);
        }
        for (Employee employee : listEmployee){
            employee.setListMission(new ArrayList<>(0));
        }
    }

    public void mutation() {
        int index1 = (int)(Math.random() * genome.length);
        int index2 = (int)(Math.random() * genome.length);

        while (index1 == index2){
            index2 = (int)(Math.random() * genome.length);
        }

        int temp = genome[index1];
        genome[index1] = genome[index2];
        genome[index2] = temp;
    }

    public void evaluate(List<Mission> listMission, List<Employee> listEmployee){

        clearInstance(listMission, listEmployee);
        determineFitness(listMission, listEmployee);

    }

    public boolean getSimilarity(Genome genome2) {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] != genome2.getGene(i)) {
                return false;
            }
        }
        return true;
    }

    public void checkValidity(List<Mission> listMission, List<Employee> listEmployee) {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == 0) {
                Mission mission = listMission.get(i);
                System.out.println("-------------- Mission " + listMission.get(i).getId() + "-------------");

                if (mission.getDay() == 1) {
                    for (Employee employee : listEmployee) {
                        if (employee.canTakeMission(mission)) {
                            System.out.println("Employee " + employee.getId() + " can take mission " + mission.getId() + " but is not assigned to it.");
                        } else {
                            System.out.println(employee.getId() + " can't take mission ");
                        }
                    }
                }
            }
        }
        System.out.println("-------------- Employee -------------");
        for (Employee employee : listEmployee) {
            for (Mission mission : employee.getListMission()) {

                System.out.println("Employee " + employee.getId() + " is assigned to mission " + mission.getId());

            }
        }
    }
}

