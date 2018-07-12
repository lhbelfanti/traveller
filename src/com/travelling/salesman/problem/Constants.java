package com.travelling.salesman.problem;

public class Constants {

    //The target fitness
    public static final double TARGET_FITNESS = 0.5;

    //Population size
    public static final int MAX_GENERATIONS = 20000;

    //Population size
    public static final int POPULATION_SIZE = 16;

    //Number of chromosomes that won't be modified
    public static final int NUMB_OF_ELITE_ROUTES = 1;

    //Number of chromosome that will be crossover
    public static final int TOURNAMENT_SELECTION_SIZE = 2;

    //Mutation rate for the chromosomes
    public static final double MUTATION_RATE = 0.25;

    //PMX Crossover segmentation size. It should be < than the quantity of chromosomes (cities)
    public static int PMX_SEGMENTATION_SIZE = 3;
}
