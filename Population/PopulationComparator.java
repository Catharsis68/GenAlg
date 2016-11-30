package Population;

import Individual.Individual;

import java.util.Comparator;

/**
 * Created by mfrindt on 10.11.16.
 * Used to sort the ArrayList with fitness as criteria
 */
public class PopulationComparator implements Comparator<Individual> {

    /* @Override
    public int compare(Individual i1, Individual i2) {
        if (i1.fitness < i2.fitness) {
            return 1;
        } else {
            return -1;
        }
    } */


    @Override
    public int compare(Individual i1, Individual i2) {
        if (i1.probability < i2.probability) {
            return 1;
        } else if (i1.probability == i2.probability) {
            return 0;
        } else {
            return -1;
        }
    }

}
