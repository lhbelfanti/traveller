package com.travelling.salesman.problem.crossover.pmx;

import com.travelling.salesman.problem.City;
import com.travelling.salesman.problem.Constants;
import com.travelling.salesman.problem.GeneticAlgorithm;
import com.travelling.salesman.problem.Route;

public class PMXCrossover {



    /**
     * Partially Matched Crossover (PMX) algorithm to make the crossover.
     *
     * @return Route (the new crossover chromosome)
     */
    public Route pmx(Route route1, Route route2, GeneticAlgorithm geneticAlgorithm) {
        Route crossoverRoute = new Route(geneticAlgorithm);

        int cities = route1.getCities().size();
        int rangeStart = (int) (cities * Math.random());
        if (rangeStart > (cities - 1) - Constants.PMX_SEGMENTATION_SIZE)
            rangeStart = (cities - 1) - Constants.PMX_SEGMENTATION_SIZE;

        for (int x = 0; x < cities; x++) {
            crossoverRoute.getCities().set(x, route1.getCities().get(x));
        }

        // Swap the PMX_SEGMENTATION_SIZE with the route2.
        // Example: route1 = crossoverRoute = ['San Francisco', 'Denver', 'Boston', 'Houston', 'New York']
        // Swap with route2 = ['Houston', 'New York', 'Denver', 'Boston', 'San Francisco']
        // For example if PMX_SEGMENTATION_SIZE = 2 and rangeStart = 0
        // crossoverRoute = ['Houston', 'New York', 'Boston', 'Houston', 'New York']
        // route2 =     ['San Francisco', 'Denver', 'Denver', 'Boston', 'San Francisco']
        for (int x = rangeStart; x < (rangeStart + Constants.PMX_SEGMENTATION_SIZE); x++) {
            crossoverRoute.getCities().set(x, route2.getCities().get(x));
            route2.getCities().set(x, route1.getCities().get(x));
        }

        Relationships relationships = new Relationships();
        relationships.determineRelationships(crossoverRoute, route2, rangeStart);

        // Complete the new chromosome with the relationships made
        String city, relationship;
        City temp;
        int index;
        for (int x = 0; x < cities; x++) {
            if (x >= rangeStart && x < (rangeStart + Constants.PMX_SEGMENTATION_SIZE))
                continue; // Skipping the chromosomes already crossover

            // Get the relationship
            city = crossoverRoute.getCities().get(x).getName();
            relationship = relationships.getCityRelationshipByName(city, crossoverRoute);

            // We can assume that the element doesn't have a relationship and we can swap
            // it with the element of the same position of the other route.
            // For example they are the same element.
            if (relationship == null) {

                // We don't want to swap a city if it isn't duplicated.
                if (!crossoverRoute.hasMoreThanOne(city))
                    continue;

                /*
                // Swap the route2 city with the crossoverRoute city at index of the first
                // available city.
                //index = getFirstAvailableCity(route2, crossoverRoute, relationships, rangeStart);
                if (index == -1) // If there isn't an available city it will swap with the
                    index = x;   // city at the index x.

                temp = route2.getCities().get(index);
                route2.getCities().set(index, crossoverRoute.getCities().get(x));
                */

                temp = route2.getCities().get(x);

                route2.getCities().set(x, crossoverRoute.getCities().get(x));
                crossoverRoute.getCities().set(x, temp);
            }
            else {
                // Find the index of the city of the relationship and get it
                index = route2.getCityIndex(relationship);
                temp = route2.getCities().get(index);

                // Swap the route2 city with the crossoverRoute city at index x
                route2.getCities().set(index, crossoverRoute.getCities().get(x));
                crossoverRoute.getCities().set(x, temp);
            }
        }

        pmxTest(cities, crossoverRoute);

        return crossoverRoute;
    }

    /*
    private int getFirstAvailableCity(Route route2, Route crossoverRoute,
                                      Relationships relationships, int rangeStart) {
        String city, relationship;
        int index = -1;

        for (int i = 0; i < route2.getCities().size(); i++) {
            if (i >= rangeStart && i < (rangeStart + Constants.PMX_SEGMENTATION_SIZE))
                continue; // Skipping the chromosomes already crossover

            city = route2.getCities().get(i).getName();
            if (crossoverRoute.getCityIndex(city) > -1) //The city already exists
                continue;

            relationship = relationships.getCityRelationshipByName(city, route2);

            if (relationship == null) { //If the city doesn't have a relationship
                index = route2.getCityIndex(city);
                break;
            }
        }

        return index;
    }*/

    /**
     * Test algorithm integrity
     * @param cities
     * @param crossoverRoute
     */
    private void pmxTest(int cities, Route crossoverRoute) {
        City c, c2;
        int count;
        for (int x = 0; x < cities; x++) {
            c = crossoverRoute.getCities().get(x);
            count = 0;
            for (int y = 0; y < cities; y++) {
                c2 = crossoverRoute.getCities().get(y);
                if (c.equals(c2))
                    count++;
            }

            if (count >= 2)
                System.out.println("Error!!!!!");
        }
    }
}
