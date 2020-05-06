package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract class that does the important backend functions of the program
 *
 * @author will simpson
 */
public abstract class Matcher {
    /**
     * take file input and create a list of users based on the information read from aforementioned file
     *
     * @param file the path of the file to read information from
     *
     * @return a list of all the users interpreted from the input file
     */
    public static List<User> createUsersFromFile(String file) {
        List<User> newUsers = new ArrayList<>();

        // set as null because it can cause issues being uninitialized
        BufferedReader fileReader = null;

        try {
            // initialize the file reader to read from input file
            fileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // if FileNotFoundException was thrown then fileReader would remain null so this would all fail
        if (fileReader != null) {
            try {
                // skip the first line bc it doesn't hold user data
                String line = fileReader.readLine();

                // loop through the remaining lines of text in the file
                while ((line = fileReader.readLine()) != null) {
                    // the entire line is read as one string so it needs to be made into an array dividing each item separated by commas
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
                    // favorite ingredients is a list of items separated by ::
                    String[] favoriteIngredients = items[3].split("::");
                    int age = Integer.parseInt(items[4]);
                    String gender = "";
                    String lookingFor = "";

                    // gender is given as 0, 1, or 2 so it needs to be interpreted as a string for better understanding in the code
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

                    // gender preference is given as 0, 1, or 2 so it needs to be interpreted as a string for better understanding in the code
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

                    // now create a new user from the gathered information and add it to the user list
                    newUsers.add(new User(name, favoriteSandwich, favoriteIngredients, age, gender, lookingFor, latitude, longitude));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return newUsers;
    }

    /**
     * compares the user controlling the application against an input list of users to determine who, if any, is a potential match
     *
     * @param me the user from the perspective of the one using the device
     * @param userList the list of users to compare against
     *
     * @return
     */
    public static List<User> getMatches(User me, List<User> userList) {
        List<User> matches = new ArrayList<>();
        int sandwichScore = 0; // score to decide if sandwich match is good enough
        final int SCORE_MINIMUM = 5; // smallest value to be deemed as a good match

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

            // matching type of sandwich is an instant possible match
            if (u.getFavSandwich().equals(me.getFavSandwich()))
                sandwichScore += 5;

            // for each ingredient in favorites for "me", if there's a match in the user's favorites that adds one point
            for (String myIngredient : me.getFavIngredients()) {
                for (String userIngredient : u.getFavIngredients()) {
                    if (userIngredient.equals(myIngredient)) {
                        sandwichScore += 1;
                        break;
                    }
                }
            }

            // only allow adding a match if the sandwich score threshold is met
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
     */

    /**
     * gets the distance between two users according to their latitude and longitude
     *
     * @param me the user from the perspective of the device
     * @param user the user to compare oneself against
     * @param unit M = miles, K = kilometers, N = nautical miles
     *
     * @return distance from self to other user as double
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
