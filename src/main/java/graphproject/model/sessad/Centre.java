package graphproject.model.sessad;

import graphproject.model.Node;

import java.util.ArrayList;
import java.util.List;

public class Centre extends Place {

    Node node;

    //private int id;
    private String name;
    //private int x;
    //private int y;

    List<Employee> listEmployee;

    List<Mission> listMission;

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
