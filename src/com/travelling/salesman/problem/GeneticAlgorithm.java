package com.travelling.salesman.problem;

import com.travelling.salesman.problem.crossover.pmx.PMXCrossover;

import java.util.ArrayList;

/**
 * This class contains the necessaries methods to create a Genetic
 * Algorithm. It has the methods to evolve, mutate and crossover
 * chromosomes and populations.
 *
 * @author Lucas Belfanti
 */
public class GeneticAlgorithm {

    private ArrayList<City> initialRoute = null;

    public GeneticAlgorithm(ArrayList<City> initialRoute) {
        this.initialRoute = initialRoute;
    }

    /**
     * Evolves a population and returns a new generation. It mutates
     * a crossover population.
     *
     * @param population
     * @return Population
     */
    public Population evolve(Population population) {
        return mutatePopulation(crossoverPopulation(population));
    }

    /**
     * Mutates a population by mutating it chromosomes.
     * There is a number of elite chromosomes (NUMB_OF_ELITE_CHROMOSOMES) that won't be
     * modified on the mutation.
     *
     * @param population
     * @return Population
     */
    private Population mutatePopulation(Population population) {

        for (int x = Constants.NUMB_OF_ELITE_ROUTES; x < population.getRoutes().size(); x++) {
            population.getRoutes().set(x, mutateRoute(population.getRoutes().get(x)));
        }

        return population;
    }

    /**
     * Randomly mutates the genes of a chromosome.
     *
     * @param route
     * @return Route
     */
    private Route mutateRoute(Route route) {
        for (int x = 0; x < route.getCities().size(); x++) {
            if (Math.random() < Constants.MUTATION_RATE) {
                int y = (int) ((route.getCities().size()) * Math.random());
                City cityY = route.getCities().get(y);
                City cityX = route.getCities().get(x);
                route.getCities().set(y, cityX);
                route.getCities().set(x, cityY);
            }
        }
        return route;
    }

    /**
     * Crossovers the chromosomes of two populations.
     * There is a number of elite chromosomes (NUMB_OF_ELITE_CHROMOSOMES) that won't be
     * modified on the crossover.
     *
     * @param population
     * @return Population
     */
    private Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getRoutes().size(), this);

         for (int x = 0; x < Constants.NUMB_OF_ELITE_ROUTES; x++) {
            crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x));
        }

        for (int x = Constants.NUMB_OF_ELITE_ROUTES; x < population.getRoutes().size(); x++) {
            Route route1 = selectTournamentPopulation(population).getRoutes().get(0);

            Population route2Population = selectTournamentPopulation(population);
            Route route2 = route2Population.getRoutes().get(
                    (int) ((route2Population.getRoutes().size() - 1) * Math.random()));

            crossoverPopulation.getRoutes().set(x, crossoverRoute(route1, route2));
        }

        return crossoverPopulation;
    }

    /**
     * Creates a population of random chromosomes from a specified population.
     * The number of chromosomes it will contains is defined by TOURNAMENT_SELECTION_SIZE
     * constant. The population is ordered by the fitness the chromosomes, thus the first
     * chromosome can be used on the population crossover.
     *
     * @param population
     * @return Population
     */
    private Population selectTournamentPopulation(Population population) {
        Population tournamentPopulation = new Population(Constants.TOURNAMENT_SELECTION_SIZE, this);

        for (int x = 0; x < Constants.TOURNAMENT_SELECTION_SIZE; x++) {
            tournamentPopulation.getRoutes().set(x,
                    population.getRoutes().get((int) (Math.random() * population.getRoutes().size())));
        }
        tournamentPopulation.sortRoutesByFitness();
        return tournamentPopulation;
    }

    /**
     * Randomly crossovers the genes of two chromosomes.
     * It uses a Partially Matched PMXCrossover (PMXCrossover) algorithm.
     *
     * @param route1
     * @param route2
     * @return Route
     */
    private Route crossoverRoute(Route route1, Route route2) {
        if (route1.equals(route2)) {
            return route1;
        }
        PMXCrossover crossover = new PMXCrossover();
        return crossover.pmx(route1, route2, this);
    }

    public ArrayList<City> getInitialRoute() {
        return initialRoute;
    }
}
