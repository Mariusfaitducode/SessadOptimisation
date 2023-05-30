package graphproject.model.sessad;

import graphproject.model.sessad.skill.Skill;
import graphproject.model.sessad.skill.Specialty;

import java.util.ArrayList;
import java.util.List;

import static graphproject.model.sessad.SessadGestion.distMissionCentre;
import static graphproject.model.sessad.SessadGestion.distMissionMission;

public class Employee {

    private final int MAX_HOURS = 420; //7h
    private final int MAX_TIME_SLOTS = 780; //13h

    private final double SPEED = 50f / 60f; //50km/h

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

    public Centre getCentre(){return centre;}
    public int getId(){return id;}

    public List<Mission> getListMission(){return listMission;}

    public void setListMission(List<Mission> listMission){this.listMission = listMission;}


    public List<Mission> getListMission(int day){

        List<Mission> listMissionDay = new ArrayList<>(0);



        for (Mission mission : listMission){
            if (mission.getDay() == day){

                listMissionDay.add(mission);
            }
            //TODO : A vérifier
            else if (mission.getDay() > day){
                break;
            }

        }

        return listMissionDay;
    }

    public void addMission(Mission mission){

        //place la mission au bon endroit dans la liste

        if (listMission.size() == 0){
            listMission.add(mission);
            return;
        }

        for (int i = 0; i < listMission.size(); i++){
            if (listMission.get(i).getDay() > mission.getDay()){
                listMission.add(i, mission);
                return;
            }
        }
    }

    public void display(){
        System.out.println("Employee id : "+id);
        System.out.println("Employee centre : "+centre.getId());
        System.out.println("Employee skill : "+skill);
        System.out.println("Employee specialty : "+specialty);
    }

    public boolean canTakeMission(Mission mission){


        //Récupère la liste de missions du jour avec nouvelle mission
        List<Mission> listMissionDay = insertMission(mission);

        //Si la liste est null, la mission n'est pas valide
        if (listMissionDay == null){
            listMission.remove(mission);
            return false;
        }
        //Si l'employée n'a que une mission, la mission est valide
        else if (listMissionDay.size() == 1){
            //System.out.println("Mission unique valide");

            return true;
        }
        else{

            //Verification taux horaire + plage horaire

            double startDistance = distMissionCentre[listMissionDay.get(0).getId() - 1][centre.getId() - 1];

            double endDistance = distMissionCentre[listMissionDay.get(listMissionDay.size() - 1).getId() - 1][centre.getId() - 1];

            double startHours = startDistance / SPEED;
            double endHours = endDistance / SPEED;

            //Verification plage horaire

            double timeSlots = listMissionDay.get(listMissionDay.size() - 1).getEnd() - listMissionDay.get(0).getStart() + startHours + endHours;

            if (timeSlots > MAX_TIME_SLOTS){
                listMission.remove(mission);
                //System.out.println("Plage horaire trop grande");
                return false;
            }

            //Verification taux horaire

            double totalDayHours = startHours + endHours;

            Mission lastMission = listMissionDay.get(0);

            for (Mission missionEmployee : listMissionDay){

                //Détermination taux horaire

                double distance = distMissionMission[lastMission.getId() - 1][missionEmployee.getId() - 1];
                double roadHours = distance / SPEED;

                totalDayHours += missionEmployee.getEnd() - missionEmployee.getStart() + roadHours;

                lastMission = missionEmployee;
            }

            //System.out.println("Taux horaire : "+totalDayHours);
            //Verification taux horaire
            if (totalDayHours > MAX_HOURS){
                listMission.remove(mission);
                //System.out.println("Taux horaire trop grand");
                //System.out.println("Taux horaire : "+totalDayHours);
                return false;
            }
            //System.out.println("Mission valide");
            return true;
        }
    }


    public List<Mission> insertMission(Mission mission){

        if (skill != mission.getSkill()){

            //System.out.println("Skill différent");
            return null;
        }

        //int[] index = new int[1];

        List<Mission> listMissionDay = getListMission(mission.getDay());

        //int startIndex = listMission.indexOf(listMissionDay.get(0));



        Mission lastMission = null;

        if (listMissionDay.isEmpty()){
            listMissionDay.add(mission);

            addMission(mission);

            //listMission.add(index[0] + 1, mission);
            return listMissionDay;
        }
        else{
            lastMission = listMissionDay.get(0);
        }

        int index = 0;

        for (Mission missionEmployee : listMissionDay){

            //Trouve le bon placement dans la liste
            if (mission.getStart() < missionEmployee.getStart()){

                //Verification placement valide

                //Si la mission est la première de la journée
                if (lastMission == missionEmployee && mission.getEnd() < missionEmployee.getStart()){

                    double nextDistance = distMissionMission[mission.getId() - 1][missionEmployee.getId() - 1];
                    double nextRoadHours = nextDistance / SPEED;

                    if (nextRoadHours < missionEmployee.getStart() - mission.getEnd()){
                        listMissionDay.add(listMissionDay.indexOf(missionEmployee), mission);

                        index = listMission.indexOf(missionEmployee);
                        listMission.add(index, mission);

                        return listMissionDay;
                    }
                    //Si la mission n'est pas plaçable
                    else{
                        //System.out.println("Mission non plaçable");
                        return null;
                    }
                }
                //Si la mission est entre 2 autres missions
                else if (mission.getStart() > lastMission.getEnd() && mission.getEnd() < missionEmployee.getStart()){

                    double previousDistance = distMissionMission[lastMission.getId() - 1][mission.getId() - 1];
                    double nextDistance = distMissionMission[mission.getId() - 1][missionEmployee.getId() - 1];

                    double previousRoadHours = previousDistance / SPEED;
                    double nextRoadHours = nextDistance / SPEED;

                    if (previousRoadHours < mission.getStart() - lastMission.getEnd() && nextRoadHours < missionEmployee.getStart() - mission.getEnd()){
                        listMissionDay.add(listMissionDay.indexOf(missionEmployee), mission);

                        index = listMission.indexOf(missionEmployee);
                        listMission.add(index, mission);

                        return listMissionDay;
                    }
                }
                //Si la mission n'est pas plaçable
                else{
                    //System.out.println("Mission non plaçable");
                    return null;
                }
            }
            //Si la mission est la dernière de la journée + verification placement valide
            else if (missionEmployee == listMissionDay.get(listMissionDay.size() - 1) && mission.getStart() > missionEmployee.getEnd()){

                double previousDistance = distMissionMission[missionEmployee.getId() - 1][mission.getId() - 1];

                double previousRoadHours = previousDistance / SPEED;

                if (previousRoadHours < mission.getStart() - missionEmployee.getEnd()){
                    listMissionDay.add(mission);

                    index = listMission.indexOf(missionEmployee) + 1;
                    listMission.add(index, mission);

                    return listMissionDay;
                }
            }
            lastMission = missionEmployee;
        }

        //System.out.println("Mission non plaçable");
        return null;
    }

}
