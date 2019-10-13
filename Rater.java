/**
 * Rater is an interface that defines a custom data type representing an individual 
 * who has submitted one or more movie Ratings.
 * 
 * @author Nigel Wilson 
 * @version 10 Oct 19
 */
import java.util.ArrayList;

public interface Rater {
    // In an interface we don't specify how the state needs to be implemented but some state will be required to fulfill these methods.
    
    public void addRating(String item, double rating);

    public boolean hasRating(String item);

    public String getID();

    public double getRating(String item);

    public int numRatings();

    public ArrayList<String> getItemsRated();
}
