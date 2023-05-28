package graphproject.model.sessad;

import graphproject.model.Node;
import graphproject.model.sessad.utils.Instance;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessadGestion {

    List<Centre> listCentre;
    List<Mission> listMission;
    List<Employee> listEmployee;

    Instance instance;

    Map<Integer, String> mapInstance;

    public SessadGestion(){

        mapInstance = new HashMap<>();

        mapInstance.put(1, "30Missions-2centres");
        mapInstance.put(2, "66Missions-2centres");
        mapInstance.put(3, "94Missions-2centres");
        mapInstance.put(4, "94Missions-3centres");
        mapInstance.put(5, "100Missions-2centres");
        mapInstance.put(6, "200Missions-2centres");

        listCentre = new ArrayList<>();
        listMission = new ArrayList<>();
        listEmployee = new ArrayList<>();

        generateInstance(1);


        instance.loadCentres(listCentre);
        instance.loadEmployees(listEmployee, listCentre);
        instance.loadMissions(listMission);

        //
    }

    private void generateInstance(int instanceNumber) {
        String directoryPath = "src\\main\\resources\\instances\\"+mapInstance.get(instanceNumber)+"\\";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        instance = new Instance(files[0], files[1], files[2], files[3]);
    }

    public void displayInformations(){
        for(Centre centre : listCentre){
            centre.display();
        }
        System.out.println("--------------------------------------------------");
        for(Employee employee : listEmployee){
            employee.display();
        }
        System.out.println("--------------------------------------------------");
        for(Mission mission : listMission){
            mission.display();
        }
        System.out.println("--------------------------------------------------");
    }



    public static void main(String[] args) {

        // Launch the JavaFX application
        SessadGestion sessadGestion = new SessadGestion();
        //sessadGestion.displayInformations();
    }

    public List<Centre> getListCentre() {
        return listCentre;
    }

    public List<Mission> getListMission() {
        return listMission;
    }

    public void generatePosition(List<Node> listNodes){
        instance.loadDistances(listCentre, listMission, listNodes);
    }


}
