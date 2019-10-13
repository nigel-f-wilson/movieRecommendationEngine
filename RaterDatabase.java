/** RaterDatabase 
 * Despite temptation Keep all methods and fields here STATIC so that RaterDatabase can be queried without instantiation.
 * 
 * state: 
 *   HashMap named ourRaters that maps a raterID String to a Rater object that includes all the movie ratings made by this rater.
 * Initialization:  
 *   initialize(String filenameOfRatingData)
 *   default initialize method initializes the HashMap ourRaters if it does not exist.
 *   addRatings(String parameter filename)  
 *   addRaterRating(String raterID, String movieID, double rating) Notice that the method addRatings calls this method.
 * Query:
 *   getRater(String id) . This method returns a Rater that has this ID.
 *   getRaters() returns an ArrayList of all Raters from the database.
 *   size() returns the number of raters in the database.
 * 
 * @author Nigel Wilson 
 * @version 13 Oct 2019
 * 
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String,Rater> ourRaters;
     
    private static void initialize() {
        // this method is only called from addRatings 
        if (ourRaters == null) {
            ourRaters = new HashMap<String,Rater>();
        }
    }

    public static void initialize(String filename) {
        if (ourRaters == null) {
            ourRaters= new HashMap<String,Rater>();
            addRatings("data/" + filename);
        }
    }   
    
    public static void addRatings(String filename) {
        initialize(); 
        FileResource fr = new FileResource(filename);
        CSVParser csvp = fr.getCSVParser();
        for(CSVRecord rec : csvp) {
                String id = rec.get("rater_id");
                String item = rec.get("movie_id");
                String rating = rec.get("rating");
                addRaterRating(id,item,Double.parseDouble(rating));
        } 
    }
    
    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize(); 
        Rater rater =  null;
        if (ourRaters.containsKey(raterID)) { // if this raterID already exists select that rater
            rater = ourRaters.get(raterID); 
        } 
        else {                                  // else make a new rater
            rater = new EfficientRater(raterID);
            ourRaters.put(raterID,rater);
        }
        rater.addRating(movieID,rating);  
    } 
             
    public static Rater getRater(String id) {
        initialize();
        return ourRaters.get(id);
    }
    
    public static ArrayList<Rater> getRaters() {
        initialize();
        ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());  
        // ourRaters.values() returns a Collection. 
        // ArrayList Implements Collection, and has a constructor that takes a Collection parameter.
        return list;
    }
 
    public static int size() {
        return ourRaters.size();
    }
    
    
        
}
