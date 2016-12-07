import Individual.Individual;
import Population.Population;
import com.sun.org.apache.bcel.internal.generic.POP;

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

//            population.printPopulation();
            population.printPopulationOverview();

        } while (GENERATION_COUNTER < 5 || BEST_FITNESS < FITNESS_THRESHOLD);


        System.out.println("evolution done BEST_FITNESS " + BEST_FITNESS + " FITNESS_THRESHOLD " + FITNESS_THRESHOLD);
        population.printPopulationOverview();
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
            population.mutations.add(population.individuals.get(i));
//            /* DEBUG */ System.out.println("TOP 10 " + nextGeneration.individuals.get(i).toString());
        }
    }

    // 2. Step CROSSOVER - alte generation wird crossover gemacht
    public void crossover(Population population) {
        Random rnd = new Random();
        int maximum = population.individuals.size(); // alle Individueen stehen zur Auswahl für crossover
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
            int crossoverPos = rnd.nextInt(BITSTRING_SIZE) + 0;
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
        }
    }

    // 3. Step MUTATE -> next generation only
    public void mutate(Population population) {
    // Wähle m Kandidaten aus Ps mit gleichverteilter
    // Wahrscheinlichkeit und kippe jeweils ein zufällig
    // gewähltes Bit
        Random rnd = new Random();
        int maximum = population.mutations.size();
        int minimum = 2;
        int range = maximum - minimum;

        // alle Individuen mutieren
        for (int k=0; k<MUTATIONRATE; k++) {

            int randomIndividualOfNextGeneration = rnd.nextInt(range) + minimum;
            Individual mutation = population.mutations.remove(randomIndividualOfNextGeneration);

            population.mutations.add(population.mutations.size(), mutation.mutate());
        }
    }
}
