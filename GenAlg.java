import Individual.Individual;
import Population.Population;

import java.util.*;
import static Config.Configuration.*;
/**
 * Created by mfrindt on 03.11.16.
 */

/**
 * Schreibt einen genetischen Algorithmus, der seinen Genstrang optimiert, bis
 er einem zuvor zufällig generierten Optimums-Bitstring gleicht. Fitness ist
 der f = BitZahl - HammingDistanz (oder einfach Anzahl der identischen Bits).
 * */

public class GenAlg {

    Population population = new Population("BASIC GENERATION");


    public GenAlg() {

        nextGeneration.individuals.clear();
        run();
    }

    // 1. anfangsbevölkerung
    // 2. fitness bestimmen (die besten auswählen)
    // 3. erstellen einer neuen bevölkerung
    // -> kreuzung (mutter + vater)
    // -> mutation entwickeln (auf next gen)
    // Ziel: Optimum wurde gefunden. Oder: ANzahl der Generationen

    public void run() {

        do {

            evolve(population);

            population.printPopulation();

        } while (GENERATION_COUNTER < 5);
    }

    private void evolve(Population population) {

        // select
        select(population);

        crossover(population);

        // mutate
        mutate(population);

        // update
        population.update();

        GENERATION_COUNTER++;
    }


    // 1. Step SELECT rating - best ones get into next generation - protected!
    public void select(Population population) {
//        System.out.println("---------------------------------------------------------- SELECTION -----------------------------------------------------------");
//        System.out.println("<< "+ population.name + " >> Size:" + population.individuals.size() + " Generation:" + GENERATION_COUNTER + " Selections:" + NUMBER_OF_SELECTIONS + " Best:"+BEST_FITNESS + " Current:" + population.individuals.get(0).fitness );

        // die besten x Individuen werden in die nächste Generation übernommen
        for (int i=0; i<NUMBER_OF_SELECTIONS; i++) {
            nextGeneration.individuals.add(population.individuals.get(i));
//            /* DEBUG */ System.out.println("TOP 10 " + nextGeneration.individuals.get(i).toString());
        }
    }

    // 2. Step CROSSOVER - alte generation wird crossover gemacht
    public void crossover(Population population) {
        Random rnd = new Random();
        int maximum = population.individuals.size(); // alle 100 Individueen stehen zur Auswahl für crossover
        int minimum = 0;
        int range = maximum - minimum;

//        /* DEBUG */System.out.println("CROSSOVER max: " + maximum + " min: " + minimum + " range: "+range);

        for (int i = 0; i < CROSSOVER; i++) {
            int fatherIndex = rnd.nextInt(range) + minimum;
            int motherIndex = rnd.nextInt(range) + minimum;
            while (fatherIndex == motherIndex) {
                motherIndex = rnd.nextInt(range) + minimum;
            }
//            System.out.println("mother["+motherIndex+"] father["+fatherIndex+"]");

            Individual father = population.individuals.get(fatherIndex);
            Individual mother = population.individuals.get(motherIndex);

            // crossover beim  Individuum zwischen bitstring pos: 20-80
            minimum = 20;
            maximum = 80;
            range = maximum - minimum;

            int crossoverPos = rnd.nextInt(range) + minimum;
//             System.out.println("crossover pos: " + crossoverPos);

            // create new individual
            Individual child1 = new Individual();
            Individual child2 = new Individual();

            // 0 to crossoverPos
            for (int j=0; j<crossoverPos; j++) {
                // FATHER /
                child1.bitstring[j] = father.bitstring[j];
                // MOTHER /
                child2.bitstring[j] = mother.bitstring[j];
            }

            // crossoverPos to population end
            for (int k=crossoverPos; k<BITSTRING_SIZE; k++) {
                // FATHER / MOTHER
                child1.bitstring[k] = mother.bitstring[k];
                // MOTHER / FATHER
                child2.bitstring[k] = father.bitstring[k];
            }

            population.mutations.add(child1); // werden im nächsten schritt mutiert
            population.mutations.add(child2); // werden im nächsten schritt mutiert
            population.update();
        }
    }

    // 3. Step MUTATE -> next generation only
    public void mutate(Population population) {
    // Wähle m Kandidaten aus Ps mit gleichverteilter
    // Wahrscheinlichkeit und kippe jeweils ein zufällig
    // gewähltes Bit
        Random rnd = new Random();
        int maximum = population.mutations.size();
        int minimum = NUMBER_OF_SELECTIONS;
        int range = maximum - minimum;

        for (int k=0; k<MUTATIONRATE; k++) {

            int randomIndividualOfNextGeneration = rnd.nextInt(range) + minimum;

            System.out.println("mutate index: " + randomIndividualOfNextGeneration);
            Individual mutation = population.mutations.get(randomIndividualOfNextGeneration);

            mutation.mutate();

            nextGeneration.individuals.add(mutation);
        }
        population.individuals = (ArrayList<Individual>) nextGeneration.individuals.clone();
        population.update();
        population.mutations.clear();
        nextGeneration.individuals.clear();
    }
}
