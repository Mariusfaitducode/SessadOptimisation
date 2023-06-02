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
    List<littleGenome> listLittleGenome;

    public Configuration(Genome genome, List<Mission> listMission, List<Employee> listEmployee, int nbrCentre){

        this.genome = genome;
        this.listLittleGenome = new ArrayList<>(0);



        genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);

        for (int i = 0; i < genome.getSizeGenome(); i++){

            Mission mission = listMission.get(i);

            if (genome.getGene(i) != 0){

                int centreId = mission.getEmployee().getCentre().getId();
                int day = mission.getDay();
                Skill skill = mission.getSkill();


            }
        }

        for (int i = 0 ; i < listMission.size() ; i++) {
            if (listMission.get(i).getEmployee() != null) {
                if (listMission.get(i).getEmployee().getCentre().getId() == 1) {
                    if (listMission.get(i).getSkill() == Skill.LPC) {
                        if (listMission.get(i).getDay() == 1) {
                            System.out.println("Mission " + i + " : " + listMission.get(i).getEmployee().getId() + ", Employee " + listMission.get(i).getEmployee().getId() + ", Centre " + listMission.get(i).getEmployee().getCentre().getId() + ", Skill " + listMission.get(i).getSkill() + ", Day " + listMission.get(i).getDay());
                        }
                    }
                }
            }
        }
    }

    private void initializeLittleGenome() {
    	
    }
}
