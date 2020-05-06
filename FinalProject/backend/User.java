package main;

import java.util.Arrays;
//import java.util.Map;

/**
 * each individual user of the app. contains the relevant information for them that's used for displaying their information on the app and for looking for potential matches between any two users
 *
 * @author will simpson
 */
public class User {
    private String name;
    private String favSandwich;
    private String[] favIngredients;
    private int age;
    private String gender;
    private String lookingFor;
    private double latitude, longitude;

    /**
     * create a user from the information supplied to the method
     *
     * @param name user's name
     * @param favSandwich user's favorite type of sandwich
     * @param favIngredients user's favorite ingredients on their favorite sandwich
     * @param age user's age as int
     * @param gender user's age as "Male", "Female", or "Prefer not to say"
     * @param lookingFor the gender user prefers as "Male", "Female", or "Both"
     * @param latitude latitude of user's location
     * @param longitude longitude of user's location
     */
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

    /**
     * create a string of user object and its instance fields
     *
     * @return a string listing the instance fields of the user
     */
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
