package Config;

import Individual.Individual;

/**
 * Created by mfrindt on 09.11.16.
 */
public class Configuration {
    public static final int POPULATION_SIZE = 500; /* population 100-500 */
    public static final int NUMBER_OF_SELECTIONS = 10; // in Prozent
    public static final int CROSSOVER = (POPULATION_SIZE - NUMBER_OF_SELECTIONS) / 2; /* sei der Anteil, der in jedem Schritt durch Crossover ersetzt wird */
    public static final double MUTATIONRATE = 30; /* mutationsrate in Prozent */

    public static final int NUMBER_OF_PERMUTATIONS = 1;
    public static final Individual HYPOTHESE = new Individual();
    public static final int BITSTRING_SIZE = 100;

    public static int GENERATION_COUNTER = 0; /* counter for generations */

    public static double BEST_FITNESS = 0;   /* best individual */
    public static final double FITNESS_THRESHOLD = 100;  /* goal */
}
