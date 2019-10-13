/**
 * EfficientRater refactors the original class PlainRater so that it will opperate faster using a
 * HashMap to look up Ratings rather than iterating over every element in a list of Ratings. 
 * 
 * @author Nigel Wilson 
 * @version 13 Oct 2019
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String raterID;
    private HashMap<String,Rating> myRatings;  // The key is the movieID of the associated Rating
    
    public EfficientRater(String id) {
        raterID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String movieID, double rating) {
       Rating r = new Rating(movieID, rating);
       myRatings.put(movieID, r);
    }

    public boolean hasRating(String movieID) {
        return myRatings.containsKey(movieID);
    }
    
    public String getID() {
        return raterID;
    }

    public double getRating(String movieID) { 
        if (this.hasRating(movieID)) {
            Rating r = myRatings.get(movieID);
            return r.getValue();
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> myRatedMovieList = new ArrayList<String>();
        for (String movieID : myRatings.keySet()) {
            myRatedMovieList.add(movieID);
        }
        return myRatedMovieList;
    }
}
