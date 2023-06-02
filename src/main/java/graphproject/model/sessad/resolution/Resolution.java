package graphproject.model.sessad.resolution;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.resolution.step_2.Configuration;
import graphproject.model.sessad.skill.Skill;

import java.util.List;

public class Resolution {

    int nbrCentre;
    private List<Mission> listMission;
    private List<Centre> listCentre;
    private List<Employee> listEmployee;
    private Genetic genetic;

//    private Tabou tabou;

//    private Permutation permutation;

    public Resolution(List<Mission> listMission, List<Centre> listCentre, List<Employee> listEmployee){
        this.listMission = listMission;
        this.listCentre = listCentre;
        this.listEmployee = listEmployee;

        this.nbrCentre = listCentre.size();
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

        List<Genome> listBestGenomes = genetic.geneticAlgo(popSize, generationNbr, crossOverRate, mutationRate);

        for (Genome genome : listBestGenomes) {
            Configuration configuration = new Configuration(genome, listMission, listEmployee, listCentre);
        }

//        Genome firstGenome = bestGenome.get(0);
//        firstGenome.clearInstance(listMission, listEmployee);
//        firstGenome.instantiateGenome(listMission, listEmployee);
//
//        for (int i = 0 ; i < listMission.size() ; i++) {
//            if (listMission.get(i).getEmployee() != null) {
//                if (listMission.get(i).getEmployee().getCentre().getId() == 1) {
//                    if (listMission.get(i).getSkill() == Skill.LPC) {
//                        if (listMission.get(i).getDay() == 1) {
//                            System.out.println("Mission " + i + " : " + listMission.get(i).getEmployee().getId() + ", Employee " + listMission.get(i).getEmployee().getId() + ", Centre " + listMission.get(i).getEmployee().getCentre().getId() + ", Skill " + listMission.get(i).getSkill() + ", Day " + listMission.get(i).getDay());
//                        }
//                    }
//                }
//            }
//        }
    }
}
