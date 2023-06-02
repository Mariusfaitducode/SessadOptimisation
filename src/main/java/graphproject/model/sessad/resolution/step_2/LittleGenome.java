package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.skill.Skill;

import java.util.List;

public class LittleGenome {

    Centre centre;
    Skill skill;
    int day;
    List<Mission> listMission;
    List<Employee> listEmployee;

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;
    }

    public void testAllPossibility(){

        

    }

}
