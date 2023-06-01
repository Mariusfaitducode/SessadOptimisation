package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;

import java.util.List;

public class Resolution {

    private Genetic genetic;

//    private Tabou tabou;

//    private Permutation permutation;

    public Resolution(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee){

        genetic = new Genetic(listMission, listCentre, listEmployee, 500);
    }

    public void startGeneticAlgo(int popSize, int generationNbr, double crossOverRate, double mutationRate) {

//        //création population initiale
//        genetic.generatePopulation();
//
//        //genetic.getPopulation().displayPopulation();
//
//
//
//        for (int i = 0; i < 500; i++){
//            System.out.println("------ Génération "+ i + " ---------------------");
//
//            //fitness
//            genetic.fitness();
//
//            //création de nouveaux individus
//            genetic.generateNewGeneration();
//        }
//
//        System.out.println("------ Last génération ---------------------");
//        genetic.fitness();
//        genetic.displayBestGenome();

        List<Genome> bestGenome = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);


    }
}
