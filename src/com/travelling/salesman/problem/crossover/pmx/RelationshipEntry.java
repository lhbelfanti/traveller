package com.travelling.salesman.problem.crossover.pmx;

public class RelationshipEntry {
    private String city1;
    private String city2;

    public RelationshipEntry(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }

    public String getCity1() {
        return city1;
    }

    public String getCity2() {
        return city2;
    }

    @Override
    public boolean equals(Object o) {
        RelationshipEntry r = (RelationshipEntry)o;
        return r.getCity1().equals(this.getCity1()) &&
                r.getCity2().equals(this.getCity2());
    }

    @Override
    public String toString() {
        return this.city1 + " => " + this.city2;
    }
}
