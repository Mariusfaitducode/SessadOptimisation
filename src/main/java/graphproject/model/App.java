package graphproject.model;

import graphproject.model.sessad.SessadGestion;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.utils.Dictionary.mapInstance;

public class App {

    private SessadGestion sessadGestion;
    private List<Graph> graphs;

    public App() {
        graphs = new ArrayList<>(0);

//        generateAllGraphsFromInstances();
//        generateAllGraphsFromSave();
    }

    public void createNewInstance(int idInstance) {
        //TODO : Marius
        System.out.println("Creating and opening Instance : " + idInstance);
    }

//    public List<Graph> getGraphs() {
//        return graphs;
//    }

    // create a new graph
//    public void createNewGraph(String name) {
//        Graph graph = new Graph(name);
//        graphs.add(graph);
//    }

//    public Graph getLastGraph() {
//        return graphs.get(graphs.size() - 1);
//    }

//    public int getNumberOfGraphs() {
//        return graphs.size();
//    }

//    private void generateAllGraphsFromSave() {
//        String directoryPath = "src\\main\\resources\\saves\\";
//        File directory = new File(directoryPath);
//        File[] files = directory.listFiles();
//
//
//        if (files != null) {
//            System.out.println(files.length + " graph(s) loaded from save : ");
//            for (File file : files) {
//                String fileName = file.getName();
//                String graphName = fileName.substring(0, fileName.length() - 4);
//                System.out.println(" - " + graphName);
//                Graph graph = new Graph(graphName);
//                graph.setName(graphName);
//                graph.loadGraph(file);
//                graphs.add(graph);
//            }
//        }
//    }

//    private void generateAllGraphsFromInstances() {
//        System.out.println("7 instances loaded from files : ");
//        for (int idInstance = 1; idInstance < 7 ; idInstance++) {
//
//            String instanceName = mapInstance.get(idInstance);
//            System.out.println(" - " + instanceName);
//            Graph graph = new Graph(instanceName, idInstance);
//            graphs.add(graph);
//        }
//    }
}
