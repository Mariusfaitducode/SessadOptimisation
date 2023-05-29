package graphproject.model.sessad;

import graphproject.model.sessad.skill.Skill;
import graphproject.model.sessad.skill.Specialty;

public class Mission extends Place {

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
    public void setDay(int day){this.day = day;}

    public int getStart(){return startingPeriod;}
    public void setStart(int startingPeriod){this.startingPeriod = startingPeriod;}

    public int getEnd(){return endingPeriod;}
    public void setEnd(int endingPeriod){this.endingPeriod = endingPeriod;}

    public Skill getSkill(){return skill;}
    public void setSkill(Skill skill){this.skill = skill;}

    public Specialty getSpecialty(){return specialty;}
    public void setSpecialty(Specialty specialty){this.specialty = specialty;}

    public Employee getEmployee(){return employee;}
    public void setEmployee(Employee employee){this.employee = employee;}


}
