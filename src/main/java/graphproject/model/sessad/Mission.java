package graphproject.model.sessad;

import graphproject.model.Node;
import graphproject.model.sessad.skill.Skill;
import graphproject.model.sessad.skill.Specialty;

// Contient toutes les informations relatives à une mission
public class Mission extends Place {

    Node node;
    Employee employee;

    int day;
    int startingPeriod;
    int endingPeriod;

    Skill skill;
    Specialty specialty;



    public Mission(int id, String name, int day, int startingPeriod, int endingPeriod, Skill skill, Specialty specialty)
    {
        super(id, name);

        this.day = day;
        this.startingPeriod = startingPeriod;
        this.endingPeriod = endingPeriod;
        this.skill = skill;
        this.specialty = specialty;
        this.type = Type.MISSION;
    }

    public void display(){
        System.out.println("Mission id : "+id);
        System.out.println("Mission day : "+day);
        System.out.println("Mission startingPeriod : "+startingPeriod);
        System.out.println("Mission endingPeriod : "+endingPeriod);
        System.out.println("Mission skill : "+skill);
        System.out.println("Mission specialty : "+specialty);
    }

    public int getDay(){return day;}
    public int getTime() {return day * 24 * 60 + startingPeriod;}

    public int getStart(){return startingPeriod;}
    public int getEnd(){return endingPeriod;}

    public Skill getSkill(){return skill;}
    public Specialty getSpecialty(){return specialty;}
    public void setSpecialty(Specialty specialty){this.specialty = specialty;}

    public Employee getEmployee(){return employee;}
    public void setEmployee(Employee employee){this.employee = employee;}

    public Node getNode(){return node;}
    public void setNode(Node node){this.node = node;}


}
