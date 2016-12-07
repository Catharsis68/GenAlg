package Population;

import Individual.Individual;

import java.util.ArrayList;
import java.util.Collections;

import static Config.Configuration.*;

/**
 * Created by mfrindt on 14.11.16.
 */
public class Population {

    public ArrayList<Individual> individuals = new ArrayList<Individual>();
    public ArrayList<Individual> mutations = new ArrayList<Individual>();

    public double fitness = 0;

    // Constructor
    public Population() {

        this.initialize();

        calcFitness();

        // calc probability
        calcProbability();

        // calc fitness for total population
        this.fitness = calcFitnessTotal();

        // sort list
        sortPopulationByFitness();

        if (individuals.get(0).fitness > BEST_FITNESS) {
            BEST_FITNESS = individuals.get(0).fitness;
        }
    }

    // create list with individuals
    private void initialize() {
        // create population
        for (int i=0; i<POPULATION_SIZE; i++) {
            Individual in = new Individual();
            this.individuals.add(in);
        }
    }

    private void calcFitness() {

        for (Individual in: individuals) {
            in.calcFitness();
        }
    }

    private double calcFitnessTotal() {
        double fitnessOfPopulation = 0;

            for (Individual in: individuals) {
                fitnessOfPopulation += in.fitness;
            }
        return fitnessOfPopulation;
    }

    // calculate probability for each individual
    private void calcProbability() {
        // System.out.println("----------------------------------------- CALC PROBABILITY -----------------------------------------");
        for (Individual individual: individuals) {
            individual.probability = individual.fitness / this.calcFitnessTotal();
            // System.out.println("Probability: " + individual.fitness + "/" +  calculateFitnessForPopulation(list) + " = " + individual.probability);
        }
    }

    private void sortPopulationByFitness() {
        Collections.sort(this.individuals, new PopulationComparator());
    }

    // update a generation
    // 1. calculate fitness of each individual
    // 2. set probability
    // 3. calc fitness of total population
    // 4. sort population
    // sorted population with updated fitness
    public void update() {
        // calculate fitness for each individual

        this.individuals = (ArrayList<Individual>) this.mutations.clone();

        // calc fitness of each Individual
        calcFitness();

        // calc probability
        calcProbability();

        // calc fitness for total population
        this.fitness = calcFitnessTotal();

        // sort list
        sortPopulationByFitness();

//        if (individuals.get(0).fitness > BEST_FITNESS)
        BEST_FITNESS = individuals.get(0).fitness;

        // print total population
//        if (name == "BASIC GENERATION")
//            this.printPopulation();

        this.mutations.clear();
    }

    public void printPopulation() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Generation: " + GENERATION_COUNTER + " - Size: " + this.individuals.size() + " - best fitness:" + BEST_FITNESS);
        System.out.println("Population fitness: " + this.fitness);
        // iterate over all individuals
        for (Individual in: individuals) {
            System.out.println("["+individuals.indexOf(in)+"] " + in.toString());
        }
    }

    public void printPopulationOverview() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Generation: " + GENERATION_COUNTER + " - Size: " + this.individuals.size() + " - BEST_FITNESS:" + BEST_FITNESS + " FINTESS_THRESHOLD: " + FITNESS_THRESHOLD);
        System.out.println("Population fitness: " + this.fitness);
        individuals.get(0).toString();
    }

}
