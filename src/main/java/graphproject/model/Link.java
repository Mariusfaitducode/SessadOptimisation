package graphproject.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Link {

    public static class Arrow{
        public Line line;
        public Polygon arrowHead;
    }

    private Arrow arrow;

    private final Node linkedNode;
    //private final Node initialNode;
    private boolean selected;

    private Color color;

    Link(Node node){
        this.linkedNode = node;
        //this.initialNode = initialNode;
        selected = false;
    }

    Link(Node node, Color color){
        this.linkedNode = node;
        this.color = color;
        //this.initialNode = initialNode;
        selected = false;
    }

    public Color getColor(){return color;}
    public void setColor(Color color){this.color = color;}

    public Line getLine(){return arrow.line;}
    public Polygon getArrowHead(){return arrow.arrowHead;}

    public  Arrow getArrow(){return arrow;}
    public Node getNode(){return linkedNode;}

    public void setOrientedLine(Arrow arrow){
        this.arrow = arrow;
    }

    public void setSelection(boolean b){selected = b;}
    public boolean isSelected(){return selected;}

    public void deleteLink(Node node, Pane centerPane){
        centerPane.getChildren().remove(getLine());
        centerPane.getChildren().remove(getArrowHead());
        linkedNode.linkedNodeList.remove(node);

        node.links.remove(this);
    }
}
