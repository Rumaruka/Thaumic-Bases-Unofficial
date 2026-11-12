package com.rumaruka.thaumicbases.api.dummycore_remove.utils;

import java.util.Random;

public class MathUtils {
    public static boolean arrayContains(Object[] array, Object searched) {
        for(Object element : array) {
            if(element.equals(searched))
                return true;
        }
        return false;
    }

    /**
     * Checks if the given array contains a given int
     * @param array - the array to search through
     * @param searched - the int we are searching for
     * @return true if the array already contains the given int, false otherwise
     */
    public static boolean arrayContains(int[] array, int searched) {
        for (int element : array) {
            if(element == searched)
                return true;
        }
        return false;
    }
    public static double randomDouble(Random rand) {
        return rand.nextDouble()-rand.nextDouble();
    }

}
