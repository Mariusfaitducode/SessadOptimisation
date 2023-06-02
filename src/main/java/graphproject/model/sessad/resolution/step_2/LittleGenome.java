package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.skill.Skill;

import java.util.List;

public class LittleGenome {

    private Centre centre;
    private Skill skill;
    private int day;
    private List<Mission> listMission;
    private List<Employee> listEmployee;

    public LittleGenome(Centre centre, Skill skill, int day){
        this.centre = centre;
        this.skill = skill;
        this.day = day;
    }

    public Centre getCentre(){
        return this.centre;
    }

    public Skill getSkill(){
        return this.skill;
    }

    public int getDay(){
        return this.day;
    }

    public List<Mission> getListMission(){
        return this.listMission;
    }

    public List<Employee> getListEmployee(){
        return this.listEmployee;
    }

    public void addMission(Mission mission){
        this.listMission.add(mission);
    }

    public void testAllPossibility(){

        

    }

}
