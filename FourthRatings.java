
/**
 * FourthRatings 
 * use RaterDatabase and MovieDatabase static methods in place of instance variables—
 * so where you have code with myRaters, you need to replace the code with calls to 
 * methods in the RaterDatabase class. 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {
    public FourthRatings() {
        // default constructor
        // This used to initialize state but now the state has been abstracted out to MovieDatabase and RaterDatabase
        // MovieDatabase and RaterDatabase each need their initialize() method to be called first in order to work with anything but the default filenames. 
        // These calls are handled within the setup() method of the testing class MovierRunnerSimilarAverages.
    }
    
    public ArrayList<Rating> getAverageRatings (int minRaters) {
        // Initialize the list of ratings that will be returned.
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        // Get from database complete list of movieIDs. 
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movieIDs) {
            double avg = getAverageRatingByMovieID(id, minRaters);    
            if (avg != 0.0) {
                avgRatings.add(new Rating(id, avg));
            }
        }
        return avgRatings;
    } 
    
    public double getAverageRatingByMovieID (String movieID, int minimalRaters) {
        // For each rater on raterList see if rating made for current movieID. 
        // If so increment ratings counter and add this rating to running total. 
        double runningTotalRating = 0.0;
        double countRatingsMade = 0.0;
        for (Rater r : RaterDatabase.getRaters()) {
            if (r.hasRating(movieID)) {
                runningTotalRating += r.getRating(movieID);
                countRatingsMade += 1.0;
            }
        }
        if (countRatingsMade >= minimalRaters) {
            double mean = (runningTotalRating / countRatingsMade);
            return mean;
        } else {
            return 0.0;
        }
    }
    
    
    // least minimalRaters ratings and satisfies the filter criteria. This method will need to create 
    // the ArrayList of type String of movie IDs from the MovieDatabase using the filterBy method before calculating those averages.
    public ArrayList<Rating> getAverageRatingsByFilter (int minimalRaters, Filter filterCriteria) {
        // Initialize the list of ratings that will be returned.
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        
        // Get from database list of movieIDs that satisfy filter param. 
        ArrayList<String> moviesThatSatisfy = MovieDatabase.filterBy(filterCriteria);
        for (String id : moviesThatSatisfy) {
            double avg = getAverageRatingByMovieID(id, minimalRaters);    
            if (avg != 0.0) {
                avgRatings.add(new Rating(id, avg));
            }
        }
        return avgRatings;
    } 
}