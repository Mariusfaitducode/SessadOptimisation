package graphproject.model.sessad;

import graphproject.model.Node;

import java.util.ArrayList;
import java.util.List;

// Hérite de la classe Place et ajoute les informations uniquement relative au centre tel que le nom
public class Centre extends Place {

    Node node;
    private String name;
    List<Employee> listEmployee;

    public Centre(int id, String name) {

        super(id, name);
        this.name = name;
        this.type = Type.CENTRE;

        this.listEmployee = new ArrayList<>(0);

    }

    public void setNode(Node node){this.node = node;}
    public Node getNode(){return node;}

    public List<Employee> getListEmployee() {
        return listEmployee;
    }

    public void display(){
        System.out.println("Centre id : "+id);
        System.out.println("Centre name : "+name);
        System.out.println("Centre x : "+x);
        System.out.println("Centre y : "+y);
    }
}
