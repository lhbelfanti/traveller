package com.travelling.salesman.problem.crossover.pmx;

import com.travelling.salesman.problem.Constants;
import com.travelling.salesman.problem.Route;

import java.util.ArrayList;

public class Relationships {

    private ArrayList<RelationshipEntry> relationships = new ArrayList<RelationshipEntry>();
    private ArrayList<RelationshipEntry> enqueuedRelationships = new ArrayList<RelationshipEntry>();

    /**
     * Determines the basic relationships using the cities from the
     * crossover range.
     *
     * @param route1
     * @param route2
     * @param rangeStart
     */
    public void determineRelationships(Route route1, Route route2, int rangeStart) {
        // Determine mapping relationship
        String city1, city2;
        for (int x = rangeStart; x < (rangeStart + Constants.PMX_SEGMENTATION_SIZE); x++) {
            city1 = route2.getCities().get(x).getName();
            city2 = route1.getCities().get(x).getName();
            addRelationship(new RelationshipEntry(city1, city2));
            addRelationship(new RelationshipEntry(city2, city1));
        }

        determineDeepRelationships();
    }

    /**
     * Determines the deep relationships, the ones that can be access
     * by transitivity.
     */
    private void determineDeepRelationships() {
        // Determine relationship by transitivity.
        String re1City1, re1City2, re2City1, re2City2;
        RelationshipEntry re1, re2;
        for (int i = 0; i < relationships.size(); i++) {
            re1 = relationships.get(i); // For example "San Francisco" => "Boston"
            re1City1 = re1.getCity1();  // "San Francisco"
            re1City2 = re1.getCity2();  // "Boston"

            if (re1City1.equals(re1City2)) // We don't want a relationship e.g.: "Boston" => "Boston"
                continue;

            for (int j = 0; j < relationships.size(); j++) {
                re2 = relationships.get(j); // For example "Boston" => "Austin"
                re2City1 = re2.getCity1();  // "Boston"
                re2City2 = re2.getCity2();  // "Austin"

                if (re1City1.equals(re2City2)) // We don't want a relationship e.g.: "Boston" => "Boston"
                    continue;

                if (re1City2.equals(re2City1)) {        // In this case we should enqueue
                    enqueueRelationship(re1City1, re2City2);  // "San Francisco" => "Austin"
                    enqueueRelationship(re2City2, re1City1);  // "Austin" => "San Francisco"
                }
            }
        }

        if (addEnqueuedRelationships())
            determineDeepRelationships();
    }

    /**
     * Creates a RelationshipEntry with the city1 and the city2.
     * Adds the relationship the the queue to add them after the
     * iteration, over the same ArrayList where we are adding this
     * relationships, finishes.
     *
     * @param city1
     * @param city2
     */
    private void enqueueRelationship(String city1, String city2) {
        RelationshipEntry relationship = new RelationshipEntry(city1, city2);
        for (int i = 0; i < enqueuedRelationships.size(); i++) {
            if (enqueuedRelationships.get(i).equals(relationship))
                return;
        }

        enqueuedRelationships.add(relationship);
    }

    /**
     * Adds the relationships enqueued to the relationships ArrayList.
     *
     * @return if any relationship was added.
     */
    private boolean addEnqueuedRelationships() {
        boolean relationshipAdded = false;

        for (int i = 0; i < enqueuedRelationships.size(); i++) {
            if (addRelationship(enqueuedRelationships.get(i)))
                relationshipAdded = true;
        }

        enqueuedRelationships.clear();
        return relationshipAdded;
    }

    /**
     * Adds a new relationship to the relationships ArrayList.
     *
     * @param relationship
     * @return if any relationship was added.
     */
    private boolean addRelationship(RelationshipEntry relationship) {
        boolean relationshipAdded = false;
        if (!relationshipAlreadyAdded(relationship)) {
            relationships.add(relationship);
            relationshipAdded = true;
        }

        return relationshipAdded;
    }

    private boolean relationshipAlreadyAdded(RelationshipEntry relationshipEntry) {
        for (int i = 0; i < relationships.size(); i++) {
            if (relationships.get(i).equals(relationshipEntry))
                return true;
        }

        return false;
    }

    /**
     * Gets the relationship for the city. This relationship
     * should be a city that's not already on the Route.
     *
     * @param city
     * @param route
     * @return the relationship city.
     */
    public String getCityRelationshipByName(String city, Route route) {
        RelationshipEntry re;
        for (int i = 0; i < relationships.size(); i++) {
            re = relationships.get(i);
            if (re.getCity1().equals(city)) {
                // Checks if the route already has the city, if not (-1) return that relationship
                if (route.getCityIndex(re.getCity2()) == -1)
                    return re.getCity2();
            }
        }

        return null;
    }
}

