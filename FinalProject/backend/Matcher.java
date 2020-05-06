package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Matcher {
    public static List<User> createUsersFromFile(String file) {
        List<User> newUsers = new ArrayList<>();

        BufferedReader fileReader = null;

        try {
            fileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileReader != null) {
            try {
                // skip the first line bc it doesn't hold user data
                String line = fileReader.readLine();

                while ((line = fileReader.readLine()) != null) {
                    String[] items = line.split(",");

                    /*
                     * each item is:
                     * first_name
                     * last_name
                     * favorite_sandwich
                     * favorite_ingredients (separated by ::)
                     * user_age
                     * user_gender (0 = male, 1 = female, 2 = prefer not to say)
                     * preferred_genders (0 = male, 1 = female, 2 = both)
                     * latitude
                     * longitude
                     */

                    String name = items[0] + " " + items[1];
                    String favoriteSandwich = items[2];
                    String[] favoriteIngredients = items[3].split("::");
                    int age = Integer.parseInt(items[4]);
                    String gender = "";
                    String lookingFor = "";

                    switch (items[5]) {
                        case "0":
                            gender = "Male";
                            break;
                        case "1":
                            gender = "Female";
                            break;
                        case "2":
                            gender = "Prefer not to say";
                            break;
                    }

                    switch (items[6]) {
                        case "0":
                            lookingFor = "Male";
                            break;
                        case "1":
                            lookingFor = "Female";
                            break;
                        case "2":
                            lookingFor = "Both";
                            break;
                    }

                    double latitude = Double.parseDouble(items[7]);
                    double longitude = Double.parseDouble(items[8]);

                    newUsers.add(new User(name, favoriteSandwich, favoriteIngredients, age, gender, lookingFor, latitude, longitude));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return newUsers;
    }

    public static List<User> getMatches(User me, List<User> userList) {
        List<User> matches = new ArrayList<>();
        int sandwichScore = 0; // score to decide if sandwich match is good enough
        final int SCORE_MINIMUM = 5;

        // look at each individual user and decide if they're good for a match
        for (User u : userList) {
            // skip if user is not preferred sex
            if (!me.getLookingFor().equals("Both") && (!u.getGender().equals(me.getLookingFor()) && !u.getGender().equals("Prefer not to say")))
                continue;
            // INACTIVE
            // skip if user is outside age range
            /*if (u.getAge() < me.getAgeRange()[0] || u.getAge() > me.getAgeRange()[1])
                continue;*/
            // INACTIVE
            // skip if user is too far away
            /*if (getUserDistance(me, u, "M") > me.getDistRadius())
                continue;*/
            // skip if user does not have right sandwich preference
            if (u.getFavSandwich().equals(me.getFavSandwich()))
                sandwichScore += 5;

            for (String myIngredient : me.getFavIngredients()) {
                for (String userIngredient : u.getFavIngredients()) {
                    if (userIngredient.equals(myIngredient)) {
                        sandwichScore += 1;
                        break;
                    }
                }
            }

            if (sandwichScore >= SCORE_MINIMUM)
                matches.add(u);
        }

        return matches;
    }

    /*
     * algorithm credit to GeoDataSource.com
     *
     * Worldwide cities and other features databases with latitude longitude are
     * available at https://www.geodatasource.com
     *
     * For inquiries please contact sales@geodatasource.com
     *
     * GeoDataSource.com (C) All Rights Reserved 2019
     *
     * -----------
     *
     * M = miles, K = kilometers, N = nautical miles
     */
    public static double getUserDistance(User me, User user, String unit) {
        if ((me.getLatitude() == user.getLatitude()) && (me.getLongitude() == user.getLongitude())) {
            return 0;
        } else {
            double theta = me.getLongitude() - user.getLongitude();
            double dist = Math.sin(Math.toRadians(me.getLatitude())) * Math.sin(Math.toRadians(user.getLatitude())) + Math.cos(Math.toRadians(me.getLatitude())) * Math.cos(Math.toRadians(user.getLatitude())) * Math.cos(Math.toRadians(theta));
            dist = Math.toDegrees(Math.acos(dist)) * 60 * 1.1515;

            if (unit.equals("K"))
                dist *= 1.609344;
            else if (unit.equals("N"))
                dist *= 0.8684;


            return (dist);
        }
    }
}
