package com.cse214.theo.sevenflags;
import java.util.HashMap;

/**
 * Random generator randomly selects a ride.
 * For extra credit, I also implemented the method that selects a ride based on the probability array passed in must give the probability for each ride.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class RandomGenerator {


    /**
     * Randomly selects a ride.
     *
     * @return  Randomly selected ride.
     *
     */
    public static Ride selectRide(Ride[] rides) {

        return rides[(int)(Math.random()*4)];

    }

    /**
     * Selects a ride based on the probabilities passed through the array.
     *
     * @return  Selected ride based on specific probability.
     *
     */
    public static Ride selectRide(Ride[] rides, double[] probabilities) throws IllegalArgumentException {
        double check=0;
        for(int i=0;i<probabilities.length;i++){
            check+=probabilities[i];
        }
        if(check!=1){
            throw new IllegalArgumentException("The probabilities should add up to be 1");
        }

        double probability = Math.random();

        HashMap<Double, Ride> hmap = new HashMap<Double, Ride>();

        for(int i=0;i<rides.length;i++)
            hmap.put(probabilities[i], rides[i]);


        for(int i=0;i<rides.length;i++) {

            for(int j=i;j<rides.length;j++) {

                if(probabilities[i]>probabilities[j]) {

                    double temp = probabilities[j];
                    probabilities[j]=probabilities[i];
                    probabilities[i]=temp;

                }

            }

        }

        for(int i=0;i<probabilities.length;i++) {
            if(probability<=probabilities[i]) {
                probability=probabilities[i];
                break;
            }
        }


        return hmap.get(probability);




    }


}