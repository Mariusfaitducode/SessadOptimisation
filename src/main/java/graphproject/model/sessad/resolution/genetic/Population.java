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

    Genome[] population;

    private int sizePopulation;

    public Population(int sizePopulation, List<Mission> listMission, List<Centre> listCentre){

        int sizeGenome = listMission.size();

        this.sizePopulation = sizePopulation;

        List<Integer> listRef = new ArrayList<>(0);

        for (int i = 0; i < sizeGenome; i++){
            listRef.add(i);
        }

        //On définit une population de taille sizePopulation
        population = new Genome[sizePopulation];

        int sizeCentre = distCentreCentre.length;

        //Parcours de la population
        for (int i = 0; i < sizePopulation; i++){

            //Pour chaque génome on réinitialise la liste des missions et des centres

            for (Mission mission : listMission){
                mission.setEmployee(null);
            }
            for (Centre centre : listCentre){
                for (Employee employee : centre.getListEmployee()){
                    employee.setListMission(new ArrayList<>(0));
                }
            }

            //On définit un génome de taille sizeGenome
            population[i] = new Genome(sizeGenome);

            //Parcours des missions / chromosomes de manière aléatoire

            Collections.shuffle(listRef);

            for (Integer c : listRef){

//                System.out.println("c : "+c);

                double min = 1000000000;
                Centre centre = null;

                //Parcours des centres
                for (int l = 0; l < sizeCentre; l++){

                    //Détermine le centre le plus proche
                    if (distMissionCentre[c][l] < min){

                        min = distMissionCentre[c][l];

                        centre = listCentre.get(l);
                    }
                }
                //Ajout d'un chromosome
                population[i].addChromosome(listMission.get(c), centre);
            }
        }
        System.out.println("Population initialized : " + sizePopulation);
    }

    public void evaluatePopulation(List<Mission> listMission, List<Employee> listEmployee){
        for (Genome genome : population){

            for (Mission mission : listMission){
                mission.setEmployee(null);
            }
            for (Employee employee : listEmployee){
                employee.setListMission(new ArrayList<>(0));
            }

            genome.determineFitness(listMission, listEmployee);

            System.out.println("Fitness : " + genome.fitness);
        }
    }

    public void displayPopulation(){
        for (Genome genome : population){
            genome.displayGenome();
        }
    }

    public Genome[] selectParents(){
        double totalFitness = 0.0;
        for (Genome genome : population) {
            totalFitness += genome.fitness;
        }

        // Sélectionner un premier génome
        double randomValue1 = Math.random() * totalFitness;
        double cumulativeFitness = 0.0;
        Genome selectedGenome1 = null;
        for (Genome genome : population) {
            cumulativeFitness += genome.fitness;
            if (cumulativeFitness >= randomValue1) {
                selectedGenome1 = new Genome(genome);
                break;
            }
        }

        // Sélectionner un deuxième génome (assure-toi de ne pas sélectionner le même génome qu'auparavant)
        double randomValue2 = Math.random() * (totalFitness - selectedGenome1.fitness);
        cumulativeFitness = 0.0;
        Genome selectedGenome2 = null;
        for (Genome genome : population) {
            if (genome != selectedGenome1) {
                cumulativeFitness += genome.fitness;
                if (cumulativeFitness >= randomValue2) {
                    selectedGenome2 = new Genome(genome);
                    break;
                }
            }
        }

        Genome[] parents = new Genome[]{selectedGenome1, selectedGenome2};

        return parents;
    }
}
