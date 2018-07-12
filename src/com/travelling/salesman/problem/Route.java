package com.travelling.salesman.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class represents the chromosome.
 *
 * @author Lucas Belfanti
 */
public class Route implements Comparable<Route>{

    private ArrayList<City> cities = new ArrayList<City>();

    private boolean hasFitnessChanged = true;
    private double fitness = 0;

    public Route(GeneticAlgorithm geneticAlgorithm) {
        int initialRouteSize = geneticAlgorithm.getInitialRoute().size();
        for (int i = 0; i < initialRouteSize; i++) {
            this.cities.add(null);
        }
    }

    public Route(ArrayList<City> cities, boolean shuffle) {
        this.cities.addAll(cities);

        if (shuffle)
            Collections.shuffle(this.cities);
    }


    /**
     * Iterates over all the cities of the array, and sums the distance
     * of that city from the previous city.
     *
     * @return double
     */
    public double calculateTotalDistance() {
        int citiesSize = this.cities.size();
        double sum = 0;

        for (int i = 0; i < citiesSize; i++) {
            if (i < citiesSize - 1) {
                sum += this.cities.get(i).distanceToCity(this.cities.get(i + 1));
            }
        }

        //Back to the first city
        sum += this.cities.get(0).distanceToCity(this.cities.get(citiesSize - 1));

        return sum;
    }

    public ArrayList<City> getCities() {
        hasFitnessChanged = true;
        return cities;
    }

    /**
     * Fitness getter. If the fitness has changed it recalculates the
     * total distance (the fitness).
     *
     * @return double
     */
    public double getFitness() {
        if (hasFitnessChanged) {
            fitness = (1 / calculateTotalDistance()) * 10000;
            hasFitnessChanged = false;
        }
        return fitness;
    }

    public int getCityIndex(String cityName) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equals(cityName))
                return i;
        }

        return -1;
    }

    public boolean hasMoreThanOne(String cityName) {
        String c;
        int count = 0;
        for (int x = 0; x < cities.size(); x++) {
            c = getCities().get(x).getName();
            if (c.equals(cityName))
                count++;
        }

        return count > 1;
    }

    @Override
    public String toString() {
        return Arrays.toString(cities.toArray());
    }

    @Override
    public int compareTo(Route route) {
        if (this.getFitness() > route.getFitness())
            return -1;
        else if (this.getFitness() < route.getFitness())
            return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        Route r = (Route)o;
        ArrayList<City> cities = r.getCities();
        ArrayList<City> myCities = this.getCities();
        for (int i = 0; i < cities.size(); i++) {
            if (!cities.get(i).equals(myCities.get(i)))
                return false;
        }

        return true;
    }
}
