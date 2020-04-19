package main;

import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        BufferedReader userFile = null;
        BufferedReader fromConsole = null;
        User me = null;
        List<User> users = new ArrayList<>();

        String name;
        int age;
        int[] ageRange = new int[2];
        String gender;
        String location;
        int distRadius;
        String favSandwich;
        String lookingFor;

        // set up buffered readers and make sure they're viable
        try {
            userFile = new BufferedReader(new FileReader("user-test.csv"));
            fromConsole = new BufferedReader(new InputStreamReader(System.in));
        } catch (FileNotFoundException e) {
            System.out.println("file not found: " + e.getMessage());
        }

        // only execute if try statement works, i don't want to bury this whole program in a try
        if (userFile != null) {
            try {
                // get name
                System.out.print("enter name: ");
                name = fromConsole.readLine();

                // get age
                System.out.print("enter age: ");
                age = Integer.parseInt(fromConsole.readLine());

                // get gender, only allow male or female right now
                gender = "";

                while (gender.isEmpty()) {
                    System.out.print("gender [male / female]: ");
                    gender = fromConsole.readLine();

                    if (!gender.equals("male") && !gender.equals("female"))
                        gender = "";
                }

                // location
                System.out.print("enter location: ");
                location = fromConsole.readLine();

                // desired radius to find people within
                System.out.print("enter distance range for potential matches (mi): ");
                distRadius = Integer.parseInt(fromConsole.readLine());

                // sandwich preference
                System.out.print("enter favorite sandwich: ");
                favSandwich = fromConsole.readLine();

                // preferred gender; can only be male, female or both
                lookingFor = "";
                while (lookingFor.isEmpty()) {
                    System.out.print("looking for [male / female / both]: ");
                    lookingFor = fromConsole.readLine();

                    if (!lookingFor.equals("male") && !lookingFor.equals("female") && !lookingFor.equals("both"))
                        lookingFor = "";
                }

                // get age range, look for low and high
                System.out.print("enter low for age range: ");
                ageRange[0] = Integer.parseInt(fromConsole.readLine());
                System.out.print("enter high for age range: ");
                ageRange[1] = Integer.parseInt(fromConsole.readLine());

                // close the console reader
                fromConsole.close();

                // create "me" profile
                me = new User(name, age, ageRange, gender, location, distRadius, favSandwich, lookingFor);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }

            // create other users from the .csv file
            try {
                // first line of user-test.csv is headers for information for each item so it needs to be skipped
                String line = userFile.readLine();

                while ((line = userFile.readLine()) != null) {
                    // items are: name,
                    String[] items = line.split(",");

                    items[5] = items[5].replaceAll(";", ",");

                    // age range needs to be substringed because of {}
                    users.add(new User(
                            items[0],
                            Integer.parseInt(items[1]),
                            new int[] {
                                    Integer.parseInt(items[2].substring(1)),
                                    Integer.parseInt(items[3].substring(0, items[3].length() - 1))
                            },
                            items[4],
                            items[5],
                            Integer.parseInt(items[6]),
                            items[7],
                            items[8]
                    ));
                }
            } catch (IOException e) {
                System.out.println("io error: " + e.getMessage());
            }

            try {
                List<User> matches = findMatches(me, users);

                System.out.println("------------------------------\npotential matches:");
                for (User u : matches) {
                    System.out.println("distance from user: " + getUserDistance(me, u, "M") + " mi");
                    System.out.println(u.toString() + "\n");
                }
            } catch (NullPointerException e) {
                System.out.println("null pointer exception: " + e.getMessage());
            }

        }
    }

    private static List<User> findMatches(User me, List<User> users) {
        List<User> matches = new ArrayList<>();

        // look at each individual user and decide if they're good for a match
        for (User user : users) {
            // skip if user is not preferred sex or "me" is not user's preferred sex
            if ((!me.getLookingFor().equals("both") && !user.getGender().equals(me.getLookingFor())) || (!user.getLookingFor().equals("both") && !me.getGender().equals(user.getLookingFor())))
                continue;
            // skip if user is outside age range
            if (user.getAge() < me.getAgeRange()[0] || user.getAge() > me.getAgeRange()[1])
                continue;
            // skip if user does not have right sandwich preference
            if (!user.getFavSandwich().equals(me.getFavSandwich()))
                continue;

            // skip if user is too far away or if "me" is too far away from user
            if (getUserDistance(me, user, "M") > me.getDistRadius() || getUserDistance(me, user, "M") > user.getDistRadius())
                continue;

            matches.add(user);
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
    private static double getUserDistance(User me, User user, String unit) {
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
