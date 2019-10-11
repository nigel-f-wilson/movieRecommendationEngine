/**
 * Rating is a basic POJO data-type used by several classes throughout this project.
 * A Rating object is simply a single key and value { movieID : rating}
 * 
 * In the ThirdRatings class the instance variable myRaters is a list where each Rating
 * represents a single rater's rating of the specified movie and movieIDs can appear more than once.
 * 
 * In MovieRunnerWithFilters a list of type Rating is used to connect each movieID to the 
 * average of all ratings that movie recieved.  This class uses a ThirdRatings Object but internally 
 * it uses the list of Ratings to mean something different than what a rating means in TR. 
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 *
 */


import java.util.*;
// An immutable passive data object (PDO) to represent the rating data
public class Rating  implements Comparable<Rating> {  // alternately:  implements Comparator<Rating>
    private String item;  // item is a movie ID, not a title.
    private double value;

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    // Returns item being rated
    public String getItem () {
        return item;
    }
    
    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getValue () {
        return value;
    }

    // Returns a string of all the rating information
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    public int compareTo(Rating other) {
        if (other.equals(null)) {
            throw new java.lang.NullPointerException();
        }
        
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }
    
    // public int compare(Rating first, Rating second) {
        // if (first.equals(null) || second.equals(null)) {
            // throw new java.lang.NullPointerException();
        // }
        
        // if (first.value < second.value) return 1;
        // if (first.value > second.value) return -1;
        
        // return 0;
    // }
}
