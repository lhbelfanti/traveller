package com.travelling.salesman.problem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents an array of chromosomes (possible solutions).
 *
 * @author Lucas Belfanti
 */
public class Population {

    private ArrayList<Route> routes = new ArrayList<Route>(Constants.POPULATION_SIZE);

    public Population(int populationSize, GeneticAlgorithm geneticAlgorithm) {
        for (int i = 0; i < populationSize; i++) {
            routes.add(new Route(geneticAlgorithm.getInitialRoute(), true));
        }
    }

    public Population(int populationSize, ArrayList<City> cities) {
        for (int i = 0; i < populationSize; i++) {
            routes.add(new Route(cities, false));
        }
    }

    public void sortRoutesByFitness() {
        Collections.sort(routes);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }


    // ---- Just for log purpose ----
    public Population(ArrayList<Route> routes, int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            this.routes.add(new Route(routes.get(i).getCities(), false));
        }
    }

    public static Population copy(int populationSize, ArrayList<Route> routes) {
        return new Population(routes, populationSize);
    }
}
