package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genome;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    //List<int[]> listConfigCentre;
    List<List<Mission>> listCentreMission;

    public Configuration(Genome genome, List<Mission> listMission, List<Employee> listEmployee){

        listCentreMission = new ArrayList<>(3);

        Genome.clearInstance(listMission, listEmployee);
        for (int i = 0; i < genome.getSizeGenome(); i++){

            Mission mission = listMission.get(i);

            if (genome.getGene(i) != 0){

                Employee employee = listEmployee.get(genome.getGene(i) - 1);

                Centre centre = employee.getCentre();

                listCentreMission.get(centre.getId() - 1).add(mission);
            }
        }
    }
}
