package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.List;

import static graphproject.model.sessad.resolution.genetic.Genetic.lastIdEmployee;
import static graphproject.model.sessad.resolution.genetic.Genetic.mutationRate;

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
                // TODO: faire une fonction can take mission globale car celle ci retepe beaucoup d'étapes
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

    public void mutation(){
        for (int i = 0; i < genome.length; i++) {
            if (Math.random() < mutationRate) { // mutationRate est une valeur entre 0 et 1, représentant la probabilité de mutation
                // Effectue la mutation sur le gène
                //offspring1.mutateGene(i);

                int val = (int)(Math.random() * lastIdEmployee);
                genome[i] = val;
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

