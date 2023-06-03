package graphproject.model;

import graphproject.model.sessad.Mission;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private String name;
    private final List <Node> nodes;

    //private SessadGestion sessadGestion;

    public Graph(String name){
        this.name = name;
        this.nodes = new ArrayList<>(0);
        //this.sessadGestion = new SessadGestion(idInstance, nodes);
    }

    //public SessadGestion getSessadGestion(){return sessadGestion;}

    public List <Node> getNodes(){return nodes;}

//    public Node getNodeFromPos(int x, int y) {
//        for (Node node : nodes) {
//            if (node.getX()==x && node.getY()==y)
//                return node;
//        }
//        // By default, return the first node
//        return nodes.get(0);
//    }

    public Node getNodeFromId(int id) {
        for (Node node : nodes) {
            if (node.getId()==id)
                return node;
        }
        // By default, return the first node
        return nodes.get(0);
    }

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public void setNodeColor(Circle circle, Node node) {
        if (node.isCentre()){
            switch (node.getIdCentre()){
                case 1:
                    circle.setFill(Color.BLUE);
                    break;
                case 2:
                    circle.setFill(Color.ORANGE);
                    break;
                case 3:
                    circle.setFill(Color.GREEN);
                    break;
            }
        }
        else{
            Mission mission = (Mission) node.getListPlace().get(0);

            if (mission.getEmployee() != null){

                switch (mission.getEmployee().getCentre().getId()){
                    case 1:
                        circle.setFill(Color.rgb(0,128,255));
                        break;
                    case 2:
                        circle.setFill(Color.rgb(255,178,102));
                        break;
                    case 3:
                        circle.setFill(Color.rgb(128,255,0));
                        break;
                }
            }
        }
    }


//    public Node addNode(int x, int y){
//        int id = nodes.size();
//        String name = "node" + id;
//
//        Node node = new Node(id, name, x, y);
//        nodes.add(node);
//        return node;
//    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public void addLink(Node node, Node linkedNode) {
        Node correspondingNode = getNodeFromId(node.getId());
        Node correspondingLinkedNode = getNodeFromId(linkedNode.getId());

        correspondingNode.links.add(new Link(correspondingLinkedNode));
        correspondingLinkedNode.linkedNodeList.add(correspondingNode);
    }

    public void addLink(Node node, Node linkedNode, Color color) {
        //Récupère les nodes du graph actuel correspondant aux nodes du graph par défault (graph0)
        //Pas la même adresse mémoire
        Node correspondingNode = getNodeFromId(node.getId());
        Node correspondingLinkedNode = getNodeFromId(linkedNode.getId());

        correspondingNode.links.add(new Link(correspondingLinkedNode, color));
        correspondingLinkedNode.linkedNodeList.add(correspondingNode);
    }

//    public Link getLinkFromIds(Node node, Node linkedNode) {
//        for (Link link : node.getLinks()) {
//            if (link.getNode()==linkedNode) {
//                return link;
//            }
//        }
//        //default value
//        return node.getLinks().get(0);
//    }

//    public void generateRandomNodes(int number, Pane centerPane){
//
//        // Nombre de node maximum pour 500 * 500 pixels
//        double standardSurface = 500 * 500;
//        double maxNumberOfPerson = 10;
//        double maxDensity = maxNumberOfPerson / standardSurface;
//
//        double size = number * (1 / maxDensity);
//        double scale = (800 * 624) / size;
//
//
//        scale = (double) Math.round(scale * 10) / 10;
//
//        if (scale < 0.1) {
//            scale = 0.1;
//        } else if (scale > 2.0) {
//            scale = 2.0;
//        }
//
//        centerPane.setScaleX(scale);
//        centerPane.setScaleY(scale);
//
//        for(int i = 0; i < number; i++){
//
//            nodes.add(new Node(i, "node" + i,(int)(4000 - (800 / scale) / 2 + Math.random() * (800 / scale)), (int)(3120 - (624 / scale) / 2 + Math.random() * (624 / scale))));
//
//        }
//    }

//    public void generateRandomLinks(){
//        for(Node node: nodes){
//            int links_count = (int)(Math.random() * 4);
//
//            for (int i = 0; i < links_count; i++){
//
//                Node linkedNode = nodes.get((int)(Math.random() * nodes.size()));
//
//                Link link = new Link(linkedNode);
//
//                // On ajoute un lien avec un node aléatoire
//                node.links.add(link);
//                linkedNode.linkedNodeList.add(node);
//            }
//        }
//    }

//    public void setRandomNodesAndLinks(int number, Pane centerPane) {
//        generateRandomNodes(number, centerPane);
//        generateRandomLinks();
//    }


//    public void displayGraph() {
//        System.out.println("Name of graph : " + name);
//    }

//    public void saveGraph(){
//
//        String path = "src\\main\\resources\\saves\\" + name + ".csv";
//
//        StringBuilder text = new StringBuilder();
//
//        for (Node node : nodes) {
//            String line = "node," + node.getId() + "," + node.getName() + "," + node.getX() + "," + node.getY() + "\n";
//            text.append(line);
//        }
//
//        for (Node node : nodes) {
//            for (Link link : node.getLinks()) {
//                String line = "link," + node.getId() + "," + link.getNode().getId() + "\n";
//                text.append(line);
//            }
//        }
//
//        try ( FileWriter fileWriter = new FileWriter(path) ) {
//
//            fileWriter.write(text.toString());
//            System.out.println("Successfully saved the file.");
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("File saved");
//            alert.setHeaderText("The graph has been successfully saved");
//            alert.setContentText("File location : \n" + path);
//            alert.showAndWait();
//
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public void loadGraph(File file) {
//
//
//            try ( java.util.Scanner scanner = new java.util.Scanner(file) ) {
//
//                while (scanner.hasNextLine()) {
//                    String line = scanner.nextLine();
//                    String[] values = line.split(",");
//
//                    if (values[0].equals("node")) {
//                        int id = Integer.parseInt(values[1]);
//                        String name = values[2];
//                        int x = Integer.parseInt(values[3]);
//                        int y = Integer.parseInt(values[4]);
//
//                        Node node = new Node(id, name, x, y);
//                        nodes.add(node);
//
//                    } else if (values[0].equals("link")) {
//                        int id = Integer.parseInt(values[1]);
//                        int linkedId = Integer.parseInt(values[2]);
//
//                        Node node = nodes.get(id);
//                        Node linkedNode = nodes.get(linkedId);
//
//                        Link link = new Link(linkedNode);
//                        node.links.add(link);
//                        linkedNode.linkedNodeList.add(node);
//                    }
//                }
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//    }

//    public void addNodes(List<Node> listNodes) {
//        nodes.addAll(listNodes);
//    }
}
