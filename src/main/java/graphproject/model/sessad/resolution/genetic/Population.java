package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static graphproject.model.sessad.SessadGestion.distCentreCentre;
import static graphproject.model.sessad.SessadGestion.distMissionCentre;

public class Population {

    public class Genome{
        int[] genome;
        double fitness;

        public Genome(int sizeGenome){
            this.genome = new int[sizeGenome];
            //this.fitness = fitness;
        }

        public void addChromosome(Mission mission, Centre centre){

            for (Employee employee : centre.getListEmployee()){

                if (employee.canTakeMission2(mission)){

                    genome[mission.getId() - 1] = employee.getId();

                    mission.setEmployee(employee);

                    break;
                }

            }
        }


    }

    Genome[] population;

    public Population(int sizePopulation, List<Mission> listMission, List<Centre> listCentre){



        int sizeGenome = listMission.size();

        List<Integer> listRef = new ArrayList<>(0);

        for (int i = 0; i < sizeGenome; i++){
            listRef.add(i);
        }

        //On définit une population de taille sizePopulation
        population = new Genome[sizePopulation];

        int sizeCentre = distCentreCentre.length;

        //Parcours de la population
        for (int i = 0; i < sizePopulation; i++){

            //On définit un génome de taille sizeGenome
            population[i] = new Genome(sizeGenome);

            //Parcours des missions / chromosomes de manière aléatoire

            Collections.shuffle(listRef);

            for (Integer c : listRef){

                System.out.println("c : "+c);

                double min = 1000000000;
                Centre centre = null;

                //Parcours des centres
                for (int l = 0; l < sizeCentre; l++){

                    if (distMissionCentre[c][l] < min){

                        min = distMissionCentre[c][l];

                        centre = listCentre.get(l);
                    }
                }
                //Ajout d'un chromosome
                population[i].addChromosome(listMission.get(c), centre);
            }



        }
    }


}
