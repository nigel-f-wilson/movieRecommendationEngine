
/**
 * FourthRatings 
 * use RaterDatabase and MovieDatabase static methods in place of instance variables—
 * so where you have code with myRaters, you need to replace the code with calls to 
 * methods in the RaterDatabase class. 
 * 
 * @author Nigel Wislon 
 * @version 13 Oct 2019
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
    
    // getSimilarities will call this method.
    private int dotProduct(Rater me, Rater r) {
        double dotProduct = 0.0;  //  running total of products ratings of movies we both rated.  
        ArrayList<String> myMoviesRated = me.getItemsRated();
        for (String id : myMoviesRated) {
            if (r.hasRating) {
                double myOneToTen = me.getRating(id);
                double yourOneToTen = r.getRating(id);
                double myFiveToFive = myOneToTen - 5;
                double yourFiveToFive = yourOneToTen - 5;
                double partialProduct = myFiveToFive * yourFiveToFive;
                dotProduct += partialProduct;
            }
        }
        return dotProduct;
    }
    
    private ArrayList<Rating> getSimilarities (String raterID) {
        ArrayList<Rating> similarityScores;
        Rater me = RaterDatabase.getRater(raterID);
        ArrayList<Rater> otherRaters = RaterDatabase.getRaters();
        for (Rater them : otherRaters) {
            if (me.getID() == them.getID()) { continue; }
            double similarity = dotProduct(me, them);
            if (similarity >= 0.0) {
                similarityScores.add(new Rating(them.getID(), similarity));
            }
        }
        return similarityScores.sort().reverse();
    }
     
    public ArrayList<Rating> getSimilarRatings (String raterID, int numSimilarRaters, int minRaters) {
        ArrayList<Rating> moviesWeightedScores = new ArrayList<Rating>();  // Rating <movieID, weightedAvgRating>
       
        // Get list of similarity comparissons.
        ArrayList<Rating> topSimilarityScores = getSimilarities(raterID);  // Rating <raterID, similarityScore>
        topSimilarityScores.sort().subList(0, numSimilarRaters);  // should already be sorted, trim list to include only the n most similar Raters.
        
        // Get list of all movieIDs 
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        
        // For each movie, see if enough of the most similar raters rated it, 
        // if so calculate the weighted average of the scores they gave it based on their similarity scores and the scores they gave that movie., 
        for (String movieID : movieIDs) {
            double countRatingsRecieved = 0.0;
            double netWeightedScore = 0.0;
            for (Rating rating : topSimilarityScores) {    // Rating <raterID, similarityScore>   
                Rater rater = RaterDatabase.getRater(rating.getItem());
                if (rater.hasRating(movieID)) {
                    countRatingsRecieved++;
                    double weightedScoreFromThisRater = (rating.getRating() *  rater.getRating(movieID));
                    netWeightedScore += weightedScoreFromThisRater;
                }    
            }
            if (countRatingsRecieved >= minRaters) {  // else do not add to recommendations list.
                double avgWeightedScore = (netWeightedScore / countRatingsRecieved);
                moviesWeightedScores.add(new Rating(movieID, avgWeightedScore));
            }
        }
        moviesWeightedScores.sort().reverse();
        return moviesWeightedScores;
    }   
}
