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



    public Mission(int id, int day, int startingPeriod, int endingPeriod, Skill skill, Specialty specialty)
    {
        super(id);

        this.day = day;
        this.startingPeriod = startingPeriod;
        this.endingPeriod = endingPeriod;
        this.skill = skill;
        this.specialty = specialty;
    }

    public void display(){
        System.out.println("Mission id : "+id);
        System.out.println("Mission day : "+day);
        System.out.println("Mission startingPeriod : "+startingPeriod);
        System.out.println("Mission endingPeriod : "+endingPeriod);
        System.out.println("Mission skill : "+skill);
        System.out.println("Mission specialty : "+specialty);
    }


}
