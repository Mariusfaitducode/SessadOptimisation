package graphproject.model.sessad.resolution.step_2;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;
import graphproject.model.sessad.resolution.genetic.Genome;
import graphproject.model.sessad.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

    Genome genome;
    List<LittleGenome> listLittleGenome;

    double bestCost;

    int bestSpecialtyMatch;
    int totOperation;

    // Instancie la configuration du meilleur génome de la population
    // On découpe le génome en plusieurs petits génomes contenant 1 centre, 1 skill et 1 jour
    // Puis on ajoute les missions et les employés correspondants
    public Configuration(Genome genome, List<Mission> listMission, List<Employee> listEmployee, List<Centre> listCentre){

        this.genome = new Genome(genome.getGenome().length);
        this.listLittleGenome = new ArrayList<>(0);
        this.totOperation = 0;

        // Creation des LittleGenome, il y en a : n centres X 2 skills X 5 days
        initializeLittleGenome(listCentre);

        Genome.clearInstance(listMission, listEmployee);
        genome.instantiateGenome(listMission, listEmployee);

        // On ajoute les employees aux LittleGenome
        addEmployeeToLittleGenome(listEmployee);

        // On split le genome en LittleGenome
        splitGenomeIntoLittleGenome(genome, listMission, listEmployee, listCentre);

    }

    public double getBestCost(){return bestCost;}
    public int getBestSpecialtyMatch(){return bestSpecialtyMatch;}
    public Genome getGenome(){return genome;}

    public int getTotOperation(){
        return this.totOperation;
    }

    // Recréer le génome à partir des LittleGenome
    public void adaptGenome(Genome miniGenome, LittleGenome littleGenome){

        for (int i = 0; i < miniGenome.getGenome().length; i++){

            Mission mission = littleGenome.getListMission().get(i);
            Employee employee = littleGenome.getListEmployee().get(miniGenome.getGene(i));

            this.genome.setGene(mission.getId()-1, employee.getId());
        }
    }

    // Créer tous les différents LittleGenome
    private void initializeLittleGenome(List<Centre> listCentre){

        for (Centre centre : listCentre) {

            for (Skill skill : Skill.values()) {

                for (int day = 1; day <= 5; day++) {

                    LittleGenome littleGenome = new LittleGenome(centre, skill, day);

                    listLittleGenome.add(littleGenome);
                }
            }
        }
    }

    // Ajoute les missions au bon LittleGenome
    private void splitGenomeIntoLittleGenome(Genome genome, List<Mission> listMission, List<Employee> listEmployee, List<Centre> listCentre){
        for (int i = 0; i < genome.getSizeGenome(); i++){

            if (genome.getGene(i) != 0){

                Mission mission = listMission.get(i);

                Centre centre = mission.getEmployee().getCentre();
                Skill skill = mission.getSkill();
                int day = mission.getDay();

                LittleGenome littleGenome = getLittleGenome(centre, skill, day);
                littleGenome.addMission(mission);
            }
        }
    }

    // Ajoute les employés au bon LittleGenome
    private void addEmployeeToLittleGenome(List<Employee> listEmployee){
        for (LittleGenome littleGenome : listLittleGenome){
            for (Employee employee : listEmployee) {
                if (employee.getCentre().equals(littleGenome.getCentre()) && employee.getSkill().equals(littleGenome.getSkill())){
                    littleGenome.addEmployee(employee);
                }
            }
        }
    }

    // Récupère le LittleGenome correspondant au centre, skill et jour
    public LittleGenome getLittleGenome(Centre centre, Skill skill, int day){
        for (LittleGenome littleGenome : listLittleGenome){
            if (littleGenome.getCentre().equals(centre) && littleGenome.getSkill().equals(skill) && littleGenome.getDay() == day){
                return littleGenome;
            }
        }
        return null;
    }

    // FOnction non utilisé qui permettait de chercher toutes combinaisons d'un génome
    // pour minimiser le coût total
    // On a abandonné cette méthode car elle était trop longue à executer pour des Little Génome de taille 12 ou plus
    public void brutForceStep2() {
        double totalCost = 0;
        int totalSpecialtyMatch = 0;

        //Resolution du génome
        for (LittleGenome littleGenome : listLittleGenome){
            if (!littleGenome.getListMission().isEmpty()){

                List<List<Integer>> combinations = littleGenome.generateCombinations();
                Genome bestGenome = littleGenome.evaluateAllCombinations(combinations);

                for (int i = 0; i < bestGenome.getGenome().length; i++){

                    Mission mission = littleGenome.getListMission().get(i);
                    Employee employee = littleGenome.getListEmployee().get(bestGenome.getGene(i) - 1);

                    this.genome.setGene(mission.getId() - 1, employee.getId());
                }

//                adaptGenome(bestGenome, littleGenome);
                totalCost += littleGenome.getBestCost();
                totalSpecialtyMatch += littleGenome.getBestSpecialtyMatch();

            }
        }

        this.bestCost = totalCost;
        this.bestSpecialtyMatch = totalSpecialtyMatch;
    }

    // Fonction qui cherche la meilleure combinaison pour chaque LittleGenome
    // En termes de permutation d'itinéraires des employés
    public void brutForceStep3(List<Mission> listMission, List<Employee> listEmployee) {
        for (LittleGenome littleGenome : listLittleGenome) {

            if (!littleGenome.getListMission().isEmpty() && !littleGenome.getListEmployee().isEmpty()) {

                List<int[]> permutations = littleGenome.generateCombinationsStep3();
                Genome bestGenome = littleGenome.evaluateAllCombinationsStep3(permutations);
                adaptGenome(bestGenome, littleGenome);
                totOperation += littleGenome.getOperation();

                System.out.println("-----------------------------");
                System.out.println("Centre : " + littleGenome.getCentre().getId() + " Skill : " + littleGenome.getSkill() + " Day : " + littleGenome.getDay());
                System.out.println("Final Specialty match : " + littleGenome.getBestSpecialtyMatch());

            }
        }

        this.genome.determineSpecialtyMatch(listMission, listEmployee);
    }
}
