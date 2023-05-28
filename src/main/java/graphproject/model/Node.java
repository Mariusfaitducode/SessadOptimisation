package graphproject.model;

import graphproject.controller.graphics.Graphics;
import graphproject.model.sessad.Place;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Node {

    List<Place> listPlace;

    boolean centre;

    int id;
    String name;
    int x;
    int y;
    List<Link> links;

    boolean selected;

    //Liste des nodes ayant un lien avec cette node, permet de retrouver les liens entrant
    List<Node> linkedNodeList;


    Circle circle;

    public Node(int id, String name, int x, int y)
    {
        this.listPlace = new ArrayList<>(0);

        this.centre = false;

        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;

        this.links = new ArrayList<>(0);
        this.linkedNodeList = new ArrayList<>(0);

        this.selected = false;
    }

    public List<Place> getListPlace() {
        return listPlace;
    }

    public Place getPlace(int ref){
        return listPlace.get(ref);
    }

    public boolean isCentre() {
        return centre;
    }
    public void setCentre(boolean centre) {
        this.centre = centre;
    }


    public int getId(){return id;}

    public void setId(int id){this.id = id;}
    public int getX(){return x;}
    public int getY(){return y;}

    public void setPosX(int x){this.x = x;}
    public void setPosY(int y){this.y = y;}

    public void updateLinks(){
        for (Link link : links){
            Link.Arrow arrow = link.getArrow();
            Node linkedNode = link.getNode();

            Graphics.updateArrow(arrow, this, linkedNode);
        }
        for (Node linkedNode : linkedNodeList){

            for (Link comingLink : linkedNode.links){
                if (comingLink.getNode() == this){
                    Link.Arrow arrow = comingLink.getArrow();

                    Graphics.updateArrow(arrow, linkedNode, this);
                }
            }
        }
    }

    public void setX(int x){
        this.x = x;
        circle.setCenterX(x);

        updateLinks();
    }
    public void setY(int y){
        this.y = y;
        circle.setCenterY(y);

        updateLinks();
    }
    public void setPosition(int x, int y){
        this.x = x;
        circle.setCenterX(x);
        for (Link link : links){
            Link.Arrow arrow = link.getArrow();
            Graphics.updateArrow(arrow, this, link.getNode());
        }
    }


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Link> getLinks(){return links;}

    public List<Node> getLinkedNodeList(){return linkedNodeList;}

    public void setCircle(Circle circle){this.circle = circle;}

    public Circle getCircle(){return circle;}

    public void deleteCircle(){
        ((Pane) circle.getParent()).getChildren().remove(circle);
    }

    public boolean isSelected(){return selected;}
    public void setSelection(boolean b){this.selected = b;}

    public void deleteAllLinks(Pane centerPane){
        for (Link link : links){
            Line line = link.getLine();
            Polygon arrow = link.getArrowHead();

            centerPane.getChildren().remove(line);
            centerPane.getChildren().remove(arrow);

            Node linkedNode = link.getNode();
            linkedNode.linkedNodeList.remove(this);
        }
        links.clear();
        for (Node node : linkedNodeList){
            //List<Link> removeLink = new ArrayList<>(0);

            for (int i = 0; i < node.links.size(); i++){
                Link link = node.links.get(i);
                if (link.getNode() == this){
                    Line line = link.getLine();
                    Polygon arrow = link.getArrowHead();

                    centerPane.getChildren().remove(line);
                    centerPane.getChildren().remove(arrow);

                    node.links.remove(link);
                }
            }
        }
    }

    //public List <Node> getLinks(){return linkNodes;}

    public void displayNode(){
        System.out.println("node : "+ x+ " "+y);
    }

}
