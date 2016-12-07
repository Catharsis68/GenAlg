package Individual;


import java.util.Random;

import static Config.Configuration.*;

/**
 * Created by mfrindt on 03.11.16.
 */
public class Individual implements Comparable<Individual> {

    public int [] bitstring;
    public double fitness, probability;

    public Individual() {
        fitness = 0;
        probability = 0;

        // create bitstring for individual
        bitstring = createBitstring();
    }

    // Create bitstring randomly
    public int[] createBitstring() {
        Random rnd = new Random();
        int[] randomBitstring = new int[100];
        for (int i=0; i<BITSTRING_SIZE; i++) {
            randomBitstring[i] =  rnd.nextInt(2);
        }
        return randomBitstring;
    }

    // Bitvertauschung für ein Individuum
    public Individual mutate() {
        Random rnd = new Random();
        int maximum = BITSTRING_SIZE; // alle 100 Individueen stehen zur Auswahl für crossover
        int minimum = 0;
        int range = maximum - minimum;

        // switch bits
        for (int i=0; i<NUMBER_OF_PERMUTATIONS;i++) {
            int bitstringPos = rnd.nextInt(range) + minimum;

            if (this.bitstring[bitstringPos] == 0) {
                this.bitstring[bitstringPos] = 1;
            } else {
                this.bitstring[bitstringPos] = 0;
            }
        }
        return this;
    }

    public void calcFitness() {
        double temp = 0;
            // iterate over every bitstring character
            for (int i=0; i<this.bitstring.length; i++) {
                if (this.bitstring[i] == HYPOTHESE.bitstring[i])
                    temp++;
            }
            this.fitness = temp;
    }

     @Override
     public String toString() {
        String bs = "";

        for (int i=0; i<bitstring.length; i++) {
            bs += "" + bitstring[i];
        }
        bs += " " + fitness + "%, " + probability;

        return bs;
    }

    @Override
    public int compareTo(Individual i) {
        if (this.fitness < i.fitness) {
            return 1;
        } else if (this.fitness == i.fitness) {
            return 0;
        } else {
            return -1;
        }
    }
}
