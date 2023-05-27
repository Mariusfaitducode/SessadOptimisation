package graphproject.model;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    private final List<Graph> graphs;

    public App() {
        graphs = new ArrayList<>(0);
        generateAllGraphsFromSave();
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    // create a new graph
    public void createNewGraph(String name) {
        Graph graph = new Graph(name);
        graphs.add(graph);
    }

    public Graph getLastGraph() {
        return graphs.get(graphs.size() - 1);
    }

    public int getNumberOfGraphs() {
        return graphs.size();
    }

    private void generateAllGraphsFromSave() {
        String directoryPath = "src\\main\\resources\\saves\\";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();


        if (files != null) {
            System.out.println(files.length + " graph(s) loaded from save : ");
            for (File file : files) {
                String fileName = file.getName();
                String graphName = fileName.substring(0, fileName.length() - 4);
                System.out.println(" - " + graphName);
                Graph graph = new Graph(graphName);
                graph.setName(graphName);
                graph.loadGraph(file);
                graphs.add(graph);
            }
        }
    }
}
