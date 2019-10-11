
/**
 * EfficientRater refactors the original class PlainRater so that it will opperate faster using a
 * HashMap to look up Ratings rather than iterating over every element in a list of Ratings. 
 * 
 * @author Nigel Wilson 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String,Rating> myRatings;  
    // - Change the ArrayList of type Rating private variable to a HashMap<String,Rating>. 
    // The key in the HashMap is a movie ID, and its value is a rating associated with this movie.
    // Explanation of changes like this should be done in GitHub rather than in in-file comments.

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    
    // Change addRating to instead add a new Rating to the HashMap with the 
    // value associated with the movie ID String "item" as the key in the HashMap.
    
    public void addRating(String item, double rating) {
        Rating r = new Rating(item,rating);
        // myRatings.add(r);
        myRatings.put(item, r);
    }

    // The method hasRating should now be much shorter; it no longer needs a loop.
    public boolean hasRating(String movieID) {
        return myRatings.containsKey(movieID);
    }
    
    public String getID() {
        return myID;
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
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        
        return list;
    }
}
