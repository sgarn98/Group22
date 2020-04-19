package main;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private int age;
    private int[] ageRange;
    private String gender;
    private String location;
    private double latitude, longitude;
    private int distRadius;
    private String favSandwich;
    private String lookingFor;

    public User(String name, int age, int[] ageRange, String gender, String location, int distRadius, String favSandwich, String lookingFor) {
        this.name = name;
        this.age = age;
        this.ageRange = ageRange;
        this.gender = gender;
        this.location = location;
        this.distRadius = distRadius;
        this.favSandwich = favSandwich;
        this.lookingFor = lookingFor;

        setCoordinates();
    }

    private void setCoordinates() {
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(location);

        latitude = coords.get("lat");
        longitude = coords.get("lon");
    }

    // User class is just supposed to be read from so not supplying setters

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int[] getAgeRange() {
        return ageRange;
    }

    public String getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public int getDistRadius() {
        return distRadius;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFavSandwich() {
        return favSandwich;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    @Override
    public String toString() {
        return "user:\n* name: " + name + "\n* age: " + age + "\n* gender: " + gender + "\n* location: " + location + "\n* favorite sandwich: " + favSandwich + "\n* looking for: " + lookingFor;
    }
}
