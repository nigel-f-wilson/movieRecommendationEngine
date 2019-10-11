
/**
 * Rater is an interface that defines a custom data type representing an individual 
 * who has submitted one or more movie Ratings.
 * 
 * @author Nigel Wilson 
 * @version 10 Oct 19
 */
import java.util.ArrayList;

public interface Rater {
    public void addRating(String item, double rating);

    public boolean hasRating(String item);

    public String getID();

    public double getRating(String item);

    public int numRatings();

    public ArrayList<String> getItemsRated();
}
