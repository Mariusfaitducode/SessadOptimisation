package graphproject.model.sessad;

import graphproject.model.sessad.skill.Skill;
import graphproject.model.sessad.skill.Specialty;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private int id;

    //Centre affecté
    private Centre centre;

    //Compétences
    private Skill skill;
    private Specialty specialty;

    //Missions affecté

    private List<Mission> listMission;

    public Employee(int id, Centre centre, Skill skill, Specialty specialty){
        this.id = id;
        this.centre = centre;
        this.skill = skill;
        this.specialty = specialty;

        this.listMission = new ArrayList<>(0);
        //skill = Skill.LSF;
    }

    public int getId(){return id;}

    public void display(){
        System.out.println("Employee id : "+id);
        System.out.println("Employee centre : "+centre.getId());
        System.out.println("Employee skill : "+skill);
        System.out.println("Employee specialty : "+specialty);
    }


}
