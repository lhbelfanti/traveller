package com.travelling.salesman.problem;

/**
 * This class represent one of the genes of the chromosome.
 *
 * @author Lucas Belfanti
 */
public class City {

    private static final double EARTH_EQUATORIAL_RADIUS = 6378.1370D;

    private String name;
    private double latitude;
    private double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * Calculates the distance between two cities and transforms it
     * to kilometers using the Haversine formula.
     *
     * @param city
     * @return double
     */
    public double distanceToCity(City city) {
        double startLat = this.getLatitude();
        double startLon = this.getLongitude();
        double endLat = city.getLatitude();
        double endLon = city.getLongitude();

        double dLat = city.getLatitude() - this.getLatitude();
        double dLon = city.getLongitude() - this.getLongitude();
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                    Math.cos(Math.toRadians(this.getLatitude())) *
                    Math.cos(Math.toRadians(city.getLatitude())) *
                    Math.pow(Math.sin(dLon / 2), 2);

        //Must ensure that a does not exceed 1 due to a floating point error
        if (a > 1) a = 1;

        double angle = 2 * Math.atan2(Math.sqrt(1 - a), Math.sqrt(a));
        return angle * EARTH_EQUATORIAL_RADIUS; // Distance in km
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        City c = (City)o;
        return c.getName().equals(this.getName()) &&
                c.getLatitude() == this.getLatitude() &&
                c.getLongitude() == this.getLongitude();
    }
}
