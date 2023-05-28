package graphproject.model.sessad;

import java.util.List;

public class Centre extends Place {

    //private int id;
    private String name;
    //private int x;
    //private int y;

    List<Employee> listEmployee;

    List<Mission> listMission;

    public Centre(int id, String name) {

        super(id);
        this.name = name;

    }

    public void display(){
        System.out.println("Centre id : "+id);
        System.out.println("Centre name : "+name);
        System.out.println("Centre x : "+x);
        System.out.println("Centre y : "+y);
    }
}
