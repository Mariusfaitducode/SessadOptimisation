package graphproject.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class SearchPath {

    public class PathNode{
        public Node node;
        public float weight;
        public float passedDistance;

        PathNode(Node node, float weight, float passedDistance){
            this.node = node;
            this.weight = weight;
            this.passedDistance = passedDistance;
        }

    }

    // A star algorithm pour trouver le plus court chemin
    public float searchPath(Node nodeStart, Node nodeEnd, List<Node> listNodePath, List<Node> listVisitedNodePath){

        List<Node> listVisitedNode = new ArrayList<>(0);

        List<PathNode> listPathNode = new ArrayList<>(0);

        PathNode actualChooseNode = new PathNode(nodeStart, 0, 0);


        listVisitedNode.add(actualChooseNode.node);

        float weight = 0;
        float distance = 0;

        while( actualChooseNode.node != nodeEnd){

            for(Link link : actualChooseNode.node.links){


                Node actualNode = link.getNode();

                //Si la node n'a pas déjà été visité
                if (!listVisitedNode.contains(actualNode)){


                    distance = actualChooseNode.passedDistance + normeVect(actualChooseNode.node.getX(), actualChooseNode.node.getY(), actualNode.getX(), actualNode.getY());
                    weight = distance + normeVect(actualNode.getX(), actualNode.getY(), nodeEnd.getX(), nodeEnd.getY());

                    //listToVisitNode.add(actualNode);
                    //listWeightNode.add(weight);

                    listPathNode.add(new PathNode(actualNode, weight, distance));
                }
            }

            if (listPathNode.isEmpty()){

                // Le chemin n'est pas possible
                return 0;
            }

            //Choix de la node la plus intéressante
            actualChooseNode = chooseNodeToExplore(listPathNode);

            actualChooseNode.node.getCircle().setFill(Color.RED);
            listVisitedNode.add(actualChooseNode.node);
            listVisitedNodePath.add(actualChooseNode.node);
        }

        // Chemin trouvé

        distance = actualChooseNode.passedDistance;

        // actualNode = endNode

        Node lastNode = actualChooseNode.node;
        listVisitedNode.remove(listVisitedNode.size()-1);
        listVisitedNodePath.remove(listVisitedNode.size()-1);

        Node actualChooseBackNode = listVisitedNode.get(listVisitedNode.size() -1);

        //listVisitedNodePath.addAll(listVisitedNode);

        while (actualChooseBackNode != nodeStart){

            if (areLinked(actualChooseBackNode, lastNode)){
                actualChooseBackNode.getCircle().setFill(Color.MAGENTA);
                listNodePath.add(actualChooseBackNode);
                lastNode = actualChooseBackNode;
            }

            listVisitedNode.remove(listVisitedNode.size()-1);
            actualChooseBackNode = listVisitedNode.get(listVisitedNode.size() -1);
        }
        return distance;
    }

    public boolean areLinked(Node node, Node linkedNode){

        for (Link link : node.links){
            if (link.getNode() == linkedNode){
                return true;
            }
        }
        return false;
    }

    public PathNode chooseNodeToExplore(List<PathNode> listPathNode){

        //On choisit la node qui a le poids le plus faible
        float min = 100000000000f;
        int ref = 0;

        for(int i = 0; i < listPathNode.size(); i++){

            if (listPathNode.get(i).weight < min){
                min = listPathNode.get(i).weight;
                ref = i;
            }
        }

        PathNode choosedPath = listPathNode.get(ref);
        listPathNode.remove(ref);



        return choosedPath;
    }

    public float normeVect(int startX, int startY, int endX, int endY){

        float a = endX - startX;
        float b = endY - startY;

        return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
