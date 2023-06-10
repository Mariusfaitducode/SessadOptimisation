package graphproject.model.sessad.resolution.genetic;

import graphproject.model.sessad.Centre;
import graphproject.model.sessad.Employee;
import graphproject.model.sessad.Mission;

import java.util.ArrayList;
import java.util.List;

public class Genome{
    int[] genome;
    int fitness;
    int specialtyMatch;
    double costFitness;

    public Genome(int sizeGenome){
        this.genome = new int[sizeGenome];
        this.fitness = 0;
    }

    public Genome(Genome genome){
        this.genome = genome.genome;
        this.fitness = genome.fitness;
        this.costFitness = genome.costFitness;
    }

    public int getSpecialtyMatch(){return this.specialtyMatch;}
    public int getFitness(){return fitness;}

    public double getCostFitness(){return costFitness;}
    public int[] getGenome(){return genome;}

    public void setGene(int index, int gene){
        this.genome[index] = gene;
    }

    public int getGene(int index){
        return this.genome[index];
    }

    public int getSizeGenome(){
        return this.genome.length;
    }

    // Fonction utilisée dans l'initialisation de la population pour vérifier et donc tenter d'assigner la mission à un employé et indirectement à un centre
    public void addChromosome(Mission mission, Centre centre){

        // Pour chaque employé du centre, on vérifie s'il peut prendre la mission avec canTakeMission et s'il peut, on lui assigne
        for (Employee employee : centre.getListEmployee()){

            if (employee.canTakeMission(mission)){

                genome[mission.getId() - 1] = employee.getId();

                mission.setEmployee(employee);
                employee.addMission(mission);

                break;
            }
            else{
                //Si la mission ne peut pas être associer à aucun employé du centre, alors on ne l'affecte pas au centre, ce qui correspond à mettre 0
                genome[mission.getId() - 1] = 0;
            }

        }
    }

    // Détermine la fitness d'un génome
    // C'est simplement le nombre total de mission affecté
    // Cependant elle retourne 0 si le génome n'est pas valide
    public void determineFitness(List<Mission> listMission, List<Employee> listEmployee){

        this.fitness = 0;
        for (int i = 0; i < genome.length; i++){

            Mission mission = listMission.get(i);

            if (genome[i] != 0){

                Employee employee = listEmployee.get(genome[i] - 1);

                if (employee.canTakeMission(mission)){

                    mission.setEmployee(employee);
                    employee.addMission(mission);

                    this.fitness++;
                }
                else {
                    this.fitness = 0;
                    break;
                }
            }
        }
    }

    //Détermine le nombre de mission avec une bonne spécialité
    public void determineSpecialtyMatch(List<Mission> listMission, List<Employee> listEmployee){
        this.specialtyMatch = 0;

        clearInstance(listMission, listEmployee);
        instantiateGenome(listMission, listEmployee);

        for (Employee employee : listEmployee){
            this.specialtyMatch += employee.nbrMissionWithGoodSpecialty();
        }
    }

    // Détermine la fitness d'un génome sans vérifier si le génome est valide
    public void deternimeFitnessWithoutChecking() {
        this.fitness = 0;
        for (int j : genome) {
            if (j != 0) {
                this.fitness++;
            }
        }
    }

    // Détermine le coût d'un génome
    public void determineCostFitness(List<Mission> listMission, List<Employee> listEmployee){
        this.costFitness = 0;

        clearInstance(listMission, listEmployee);
        instantiateGenome(listMission, listEmployee);

        for (Employee employee : listEmployee){
            this.costFitness += employee.findAllDayCost();
        }
    }

    // Affiche le génome
    public void displayGenome(){
        System.out.println("Genome : ");
        for (int j : genome) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    //Permet d'instancier les missions et les employés en fonction du génome
    public void instantiateGenome(List<Mission> listMission, List<Employee> listEmployee){

        clearInstance(listMission, listEmployee);

        for (int i = 0; i < genome.length; i++){

            Mission mission = listMission.get(i);

            if (genome[i] != 0){

                Employee employee = listEmployee.get(genome[i] - 1);

                mission.setEmployee(employee);
                employee.addMission(mission);
            }
        }
    }

    //Permet de réinitialiser les instances des missions et des employés
    public static void clearInstance(List<Mission> listMission, List<Employee> listEmployee){
        for (Mission mission : listMission){
            mission.setEmployee(null);
        }
        for (Employee employee : listEmployee){
            employee.setListMission(new ArrayList<>(0));
        }
    }

    //Fonction de mutation d'un génome
    //On échange deux gènes aléatoirement
    public void mutation() {
        int index1 = (int)(Math.random() * genome.length);
        int index2 = (int)(Math.random() * genome.length);

        while (index1 == index2){
            index2 = (int)(Math.random() * genome.length);
        }

        int temp = genome[index1];
        genome[index1] = genome[index2];
        genome[index2] = temp;
    }


    public void evaluate(List<Mission> listMission, List<Employee> listEmployee){

        clearInstance(listMission, listEmployee);
        determineFitness(listMission, listEmployee);

    }

    //Fonction d'évaluation du cout d'un génome en vérifiant que le génome a le maximum d'affectation possible
    public void evaluateCost(List<Mission> listMission, List<Employee> listEmployee, double bestFitness, double maxCost){

        clearInstance(listMission, listEmployee);
        determineFitness(listMission, listEmployee);

        if (fitness < bestFitness){
            fitness = 0;
        }

        determineCostFitness(listMission, listEmployee);

    }

    //Fonction permettant de dire si 2 génomes sont identiques
    public boolean getSimilarity(Genome genome2) {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] != genome2.getGene(i)) {
                return false;
            }
        }
        return true;
    }

    // Permet de convertir le génome d'employés en un génome de centres
    public void convertEmployeeToCentre(List<Employee> listEmployee){
        for (int i = 0; i < genome.length; i++){
            if (genome[i] != 0){
                int employeeId = genome[i];
                for (Employee employee : listEmployee){
                    if (employee.getId() == employeeId){
                        genome[i] = employee.getCentre().getId();
                        break;
                    }
                }
            }
        }
    }

    // Permet de cloner un génome
    public Genome clone() {
        Genome newGenome = new Genome(genome.length);
        for (int i = 0 ; i < genome.length ; i++) {
            newGenome.setGene(i, genome[i]);
        }
        return newGenome;
    }
}

