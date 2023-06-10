package graphproject.model.sessad.utils;

import graphproject.model.Node;
import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.skill.Skill;
import graphproject.model.sessad.skill.Specialty;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

// Cette classe permet de récupérer les informations des instances
public class Instance {

    // Il est donc caractérisé par les 4 fichiers d'une instance
    File centers;
    File distances;
    File employees;
    File missions;

    public Instance(File centers, File distances, File employees, File missions) {
        this.centers = centers;
        this.distances = distances;
        this.employees = employees;
        this.missions = missions;
    }

    // Permet de récupère les données de centre.csv et les ajoute à la liste de centre
    public void loadCentres(List<Centre> listCentre) {

        try ( java.util.Scanner scanner = new java.util.Scanner(centers) ) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                String stringId = values[0].replaceAll("[^\\d.]", "");

                int id = Integer.parseInt(stringId);

                String name = values[1];

                Centre centre = new Centre(id, name);

                listCentre.add(centre);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Permet de récupère les données de employé.csv et les ajoute à la liste d'employé
    public void loadEmployees(List<Employee> listEmployee, List<Centre> listCentre){

        try ( java.util.Scanner scanner = new java.util.Scanner(employees) ) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                int employeeId = Integer.parseInt(values[0]);
                int centreId = Integer.parseInt(values[1]);

                Centre centre = getCentre(centreId, listCentre);

                Skill skill = Skill.valueOf(values[2]);
                Specialty specialty = Specialty.valueOf(values[3]);


                Employee employee = new Employee(employeeId, centre, skill, specialty);

                listEmployee.add(employee);

                centre.getListEmployee().add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Permet de récupère les données de missions.csv et les ajoute à la liste des missions
    public void loadMissions(List<Mission> listMission){

            try ( java.util.Scanner scanner = new java.util.Scanner(missions) ) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] values = line.split(",");

                    int missionId = Integer.parseInt(values[0]);
                    int day = Integer.parseInt(values[1]);

                    int startingPeriod = Integer.parseInt(values[2]);
                    int endingPeriod = Integer.parseInt(values[3]);

                    Skill skill = Skill.valueOf(values[4]);
                    Specialty specialty = Specialty.valueOf(values[5]);

                    Mission mission = new Mission(missionId,"Mission"+missionId ,day, startingPeriod, endingPeriod, skill, specialty);

                    listMission.add(mission);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    // Permet de récupère toutes les distances pour l'affichage et la matrice des distances
    public double[][] loadDistances(List<Centre> listCentre, List<Mission> listMission, List<Node> listNodes){

        int size = listCentre.size() + listMission.size();

        double[][] distancesMatrix = new double[size][size];


        try ( java.util.Scanner scanner = new java.util.Scanner(distances) ) {

            int l = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                for (int c = 0; c < values.length; c++) {
                    distancesMatrix[l][c] = Double.parseDouble(values[c]);
                }
                l++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setPositionFromDistances(distancesMatrix, listCentre, listMission, listNodes);

        return distancesMatrix;
    }

    // Est uniquement utiliser pour l'affichage
    // Pour afficher tous les centres et missions sur l'interface graphique avec les bonnes distances
    public void setPositionFromDistances(double[][] distanceMatrix, List<Centre> listCentre, List<Mission> listMission, List<Node> listNodes){

        //convert matrix distance to matrix position
        int n = distanceMatrix.length;
        double[][] similarityMatrix = new double[n][n];

        double totalSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                totalSum += distanceMatrix[i][j];
            }
        }

        double overallMean = totalSum / (n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                similarityMatrix[i][j] = -0.5 * (distanceMatrix[i][j] - overallMean);
            }
        }

        RealMatrix similarityMatrixApache = new Array2DRowRealMatrix(similarityMatrix);
        EigenDecomposition eigenDecomposition = new EigenDecomposition(similarityMatrixApache);

        RealMatrix eigenVectors = eigenDecomposition.getV();

        double[] xCoordinates = eigenVectors.getColumn(0);
        double[] yCoordinates = eigenVectors.getColumn(1);

        for (int i = 0; i < distanceMatrix.length; i++) {

            double x = xCoordinates[i]* 2000 + 4000;
            double y = yCoordinates[i]* 2000 + 3120;

            Node node = findNode(listNodes, (int)x, (int)y );

            if(node == null ){
                node = new Node(listNodes.size(), "node"+listNodes.size(), (int)x, (int)y );
                listNodes.add(node);
            }

            if ( i < listCentre.size() ){

                listCentre.get(i).setX(x);
                listCentre.get(i).setY(y);

                node.getListPlace().add(listCentre.get(i));

                node.setCentre(true);

                listCentre.get(i).setNode(node);
            }
            else{
                listMission.get(i - listCentre.size()).setX(x);
                listMission.get(i - listCentre.size()).setY(y);

                node.getListPlace().add(listMission.get(i - listCentre.size()));

                listMission.get(i - listCentre.size()).setNode(node);
            }
        }
    }

    public Node findNode(List<Node> listNodes, int x, int y){
        for(Node node : listNodes){
            if(node.getX() == x && node.getY() == y){
                return node;
            }
        }
        return null;
    }



    public Centre getCentre(int id, List<Centre> listCentre) {
        for (Centre centre : listCentre) {
            if (centre.getId() == id) {
                return centre;
            }
        }
        return null;
    }


}
