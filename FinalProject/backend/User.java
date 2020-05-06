package main;

import java.util.Arrays;
//import java.util.Map;

public class User {
    private String name;
    private String favSandwich;
    private String[] favIngredients;
    private int age;
    private String gender;
    private String lookingFor;
    private double latitude, longitude;

    public User(String name, String favSandwich, String[] favIngredients, int age, String gender, String lookingFor, double latitude, double longitude) {
        this.name = name;
        this.favSandwich = favSandwich;
        this.favIngredients = favIngredients;
        this.age = age;
        this.gender = gender;
        this.lookingFor = lookingFor;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*private void setCoordinates() {
        Map<String, Double> coords = OpenStreetMapUtils.getInstance().getCoordinates(location);

        latitude = coords.get("lat");
        longitude = coords.get("lon");
    }*/

    // User class is just supposed to be read from so not supplying setters

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
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

    public String[] getFavIngredients() {
        return favIngredients;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    @Override
    public String toString() {
        return "user:\n* name: " + name +
                "\n* age: " + age +
                "\n* gender: " + gender +
                "\n* location: " + latitude + ", " + longitude +
                "\n* favorite sandwich: " + favSandwich +
                "\n* favorite ingredients: " + Arrays.toString(favIngredients) +
                "\n* looking for: " + lookingFor;
    }
}
