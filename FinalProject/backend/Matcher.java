package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Matcher {
    public static List<User> getMatches(User me, List<User> userList) {
        List<User> matches = new ArrayList<>();
        int sandwichScore = 0; // score to decide if sandwich match is good enough
        final int SCORE_MINIMUM = 5;

        // look at each individual user and decide if they're good for a match
        for (User u : userList) {
            // skip if user is not preferred sex
            if (!me.getLookingFor().equals("both") && !u.getGender().equals(me.getLookingFor()))
                continue;
            // skip if user is outside age range
            if (u.getAge() < me.getAgeRange()[0] || u.getAge() > me.getAgeRange()[1])
                continue;
            // skip if user is too far away
            if (getUserDistance(me, u, "M") > me.getDistRadius())
                continue;
            // skip if user does not have right sandwich preference
            if (u.getFavSandwich().equals(me.getFavSandwich()))
                sandwichScore += 5;

            for (String myIngredient : me.getSandwichIngredients()) {
                for (String userIngredient : u.getSandwichIngredients()) {
                    if (userIngredient.equals(myIngredient)) {
                        sandwichScore += .5;
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
