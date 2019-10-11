
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;


public class ThirdRatings {
    private ArrayList<Rater> raterList;  
    
    // As this class compares to SecondRatings:
    // Because Movies will now be stored in the MovieDatabase
    // 1) the instance variable myMovies is removed, 
    // 2) The constructor will no longer have a moviefile parameter,
    // 3) Remove the methods getMovieSize, getID, and getTitle.
    //    these will now be handled by querying MovieDatabase.
    // 4) Modify getAverageRatings to get movies from the MovieDatabase and store them in an ArrayList of movie IDs. 
    //    Call MovieDatabase with a TrueFilter to get every movie.
    // 
    // 
    // 
    // TODO Remove dependency on firstRatings entirely by adding RaterDatabase.
    
    public ThirdRatings() throws java.io.IOException {
        // default constructor
        this("ratings.csv");
    }
    public ThirdRatings (String ratingsfile) throws java.io.IOException {
        // this("ratedmoviesfull.csv", "ratings.csv");
        FirstRatings fr = new FirstRatings();
        raterList = fr.loadRaters(ratingsfile);
    }
    
    
    public int getRaterSize () {
        return raterList.size();
    }
    
    
    
    public ArrayList<Rating> getAverageRatings (int minimalRaters) {
        // Initialize the list of ratings that will be returned.
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        // Get from database complete list of movieIDs. 
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movieIDs) {
            double avg = getAverageRatingByMovieID(id, minimalRaters);    
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
        for (Rater r : raterList) {
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
    
    
    
    public int getRatingCount (String movieID) {
        int count= 0;
        for (Rater r : raterList) {
            if (r.hasRating(movieID)) {
                count++;
            }
        }
        return count;
    }
    
    
}

