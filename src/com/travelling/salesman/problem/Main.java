package com.travelling.salesman.problem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  This is a program that tries to solve the Travelling Salesman Problem
 *  using Genetic Algorithms.
 *
 * @author Lucas Belfanti
 */
public class Main {

    public ArrayList<City> initialRoute = new ArrayList<City>(Arrays.asList(
            new City("Boston", 42.3601, -71.0589),
            new City("Houston", 29.7604, -95.3698),
            new City("Austin", 30.2672, -97.7431),
            new City("San Francisco", 37.7749, -122.4194),
            new City("Denver", 39.7392, -104.9903),
            new City("Los Angeles", 34.0522, -118.2437),
            new City("Chicago", 41.8781, -87.6298),
            new City("New York", 40.7128, -74.0059),
            new City("Dallas", 32.7767, -92.7970),
            new City("Seattle", 47.6062, -122.3321),
            new City("Sydney", -33.8675, 155.2070),
            new City("Tokyo", 35.6895, 139.6917),
            new City("Cape Town", -33.9249, 18.4241)
    ));

    public static void main(String[] args) {
        Main main = new Main();
        Population population = new Population(Constants.POPULATION_SIZE, main.initialRoute);
        population.sortRoutesByFitness();
        main.printPopulation(population);

        long startTime, endTime, totalTime;
        startTime = System.currentTimeMillis();

        Population mostAccuratePopulation = population;
        boolean notFound = false;

        // The Genetic Algorithm is created
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(main.initialRoute);
        int generationNumber = 0;
        // While the fitness isn't the searched fitness (0.5) it creates a new generation by
        // evolving the population created
        while (population.getRoutes().get(0).getFitness() < Constants.TARGET_FITNESS) {
            generationNumber++;
            population = geneticAlgorithm.evolve(population);
            population.sortRoutesByFitness();
            System.out.println("--- Generation #" + generationNumber + " ---");
            main.printPopulation(population);

            if (population.getRoutes().get(0).getFitness() >
                    mostAccuratePopulation.getRoutes().get(0).getFitness()) {
                mostAccuratePopulation = Population.copy(Constants.POPULATION_SIZE, population.getRoutes());
            }

            if (generationNumber == Constants.MAX_GENERATIONS) {
                notFound = true;
                break;
            }
        }

        endTime = System.currentTimeMillis();
        totalTime = (endTime - startTime) / 1000;

        if (notFound) {
            main.printPopulation(mostAccuratePopulation);
            System.out.println("--- ^^^ MOST ACCURATE SOLUTION FOUND ^^^ in #" + generationNumber +
                    " Generations and " + totalTime + " seconds ---" );
        }
        else {
            System.out.println("--- ^^^ SOLUTION FOUND ^^^ in #" + generationNumber +
                    " Generations and " + totalTime + " seconds ---" );
        }
    }

    public void printPopulation(Population population) {
        Route route;
        for (int x = 0; x < population.getRoutes().size(); x++) {
            route = population.getRoutes().get(x);
            System.out.println(
                    Arrays.toString(route.getCities().toArray()) + "|  " +
                    String.format("%.4f", route.getFitness()) + "  |  " +
                    String.format("%.2f", route.calculateTotalDistance())
            );
        }

        System.out.println("");
    }
}
