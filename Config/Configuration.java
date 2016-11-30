package Config;

import Individual.Individual;
import Population.Population;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mfrindt on 09.11.16.
 */
public class Configuration {
    public static final int POPULATION_SIZE = 100; /* population 100-500 */
    public static final int NUMBER_OF_SELECTIONS = 10;
    public static final int CROSSOVER = (POPULATION_SIZE - NUMBER_OF_SELECTIONS) / 2; /* sei der Anteil, der in jedem Schritt durch Crossover ersetzt wird */
    public static final double MUTATIONRATE = 90; /* mutationsrate */

    public static final int NUMBER_OF_PERMUTATIONS = 1;
    public static final Individual HYPOTHESE = new Individual();
    public static final int BITSTRING_SIZE = 100;

    public static int GENERATION_COUNTER = 0; /* counter for generations */

    public static double BEST_FITNESS = 0;   /* best individual */
    public static final double FITNESS_THRESHOLD = 10;  /* goal */

    public static Population nextGeneration = new Population("NEXT GENERATION");
}
