package graphproject.model.sessad;

import graphproject.model.Node;
import graphproject.model.sessad.resolution.Resolution;
import graphproject.model.sessad.resolution.genetic.Genetic;
import graphproject.model.sessad.utils.Instance;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

public class SessadGestion {

    public static double[][] distGlobal;
    public static double[][] distCentreCentre;
    public static double[][] distMissionCentre;
    public static double[][] distMissionMission;

    List<Centre> listCentre;
    List<Mission> listMission;
    List<Employee> listEmployee;

    Instance instance;

//    Map<Integer, String> mapInstance;

    Resolution resolution;



    public SessadGestion(int idInstanceList, List<Node> listNodes){

        listCentre = new ArrayList<>();
        listMission = new ArrayList<>();
        listEmployee = new ArrayList<>();

        generateInstance(idInstanceList);


        instance.loadCentres(listCentre);
        instance.loadEmployees(listEmployee, listCentre);
        instance.loadMissions(listMission);

        //gère affichage
        generatePosition(listNodes);

        //Algo de résolution
        resolution = new Resolution(listMission, listCentre);
    }

    public Resolution getResolution() {return resolution;}

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
        //SessadGestion sessadGestion = new SessadGestion();
        //sessadGestion.displayInformations();
    }

    public List<Centre> getListCentre() {
        return listCentre;
    }

    public List<Mission> getListMission() {
        return listMission;
    }

    public List<Employee> getListEmployee() {return listEmployee;}

    public void generatePosition(List<Node> listNodes){
        distGlobal = instance.loadDistances(listCentre, listMission, listNodes);

        distCentreCentre = new double[listCentre.size()][listCentre.size()];
        distMissionCentre = new double[listMission.size()][listCentre.size()];
        distMissionMission = new double[listMission.size()][listMission.size()];

        cutMatrix(distGlobal, distCentreCentre, distMissionCentre, distMissionMission, listCentre.size(), listMission.size());
    }



    public void cutMatrix(double[][] distGlobal, double[][] distCentreCentre, double[][] distCentreMission, double[][] distMissionMission, int sizeCentre, int sizeMission){

//        System.out.println("listCentre.size() : "+sizeCentre);
//        System.out.println("listMission.size() : "+sizeMission);
//        System.out.println("distancesMatrix.length : "+distGlobal.length);

        //Matrice centre centre
        for (int l = 0; l < sizeCentre; l++) {
            for (int c = 0; c < sizeCentre; c++) {
                distCentreCentre[l][c] = distGlobal[l][c];
            }
        }
        //Matrice centre mission
        for (int l = sizeCentre; l < sizeCentre+sizeMission; l++) {
            for (int c = 0; c < sizeCentre; c++) {
                distCentreMission[l-sizeCentre][c] = distGlobal[l][c];
            }
        }
        //Matrice mission mission
        for (int l = sizeCentre; l < sizeCentre+sizeMission; l++) {
            for (int c = sizeCentre; c < sizeCentre+sizeMission; c++) {
                distMissionMission[l-sizeCentre][c-sizeCentre] = distGlobal[l][c];
            }
        }
    }

}
