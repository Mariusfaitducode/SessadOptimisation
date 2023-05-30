package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.List;

public class Genome{
    int[] genome;
    double fitness;

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


    public void addChromosome(Mission mission, Centre centre){

        for (Employee employee : centre.getListEmployee()){

            if (employee.canTakeMission(mission)){

//                    System.out.println("Employee " + employee.getId() + " can take mission " + mission.getId() + "in centre " + centre.getId() + " day " + mission.getDay());

                genome[mission.getId() - 1] = employee.getId();

                for (Mission m : employee.getListMission()){
                    System.out.print(m.getId()+ " ");
                }

                mission.setEmployee(employee);

                break;
            }
            else{
                genome[mission.getId() - 1] = 0;
            }

        }
    }

    public void determineFitness(List<Mission> listMission, List<Employee> listEmployee){

        displayGenome();

        System.out.println();

        for (int i = 0; i < genome.length; i++){

            Mission mission = listMission.get(i);

            if (genome[i] != 0){

                Employee employee = listEmployee.get(genome[i] - 1);

//                System.out.println("Employee "+employee.getId()+ " ");
//
//                for (Mission m : employee.getListMission()){
//                    System.out.print(m.getId()+ " ");
//                }
//                System.out.println();

                if (employee.canTakeMission(mission)){

                    this.fitness++;
                }
                else {
                    this.fitness = 0;
                    break;
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

}

