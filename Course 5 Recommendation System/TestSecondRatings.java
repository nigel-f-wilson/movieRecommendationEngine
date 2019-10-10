
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class TestSecondRatings { 
    /*
     * TEST FILENAME PARAMETERS
     */ 
    String moviesFilename = "ratedmovies_short.csv";  // size should be 5.
    // String moviesFilename = "ratedmovies.csv";       // size should be 3143.
    // String moviesFilename = "ratedmoviesfull.csv";
    
    String ratingsFilename = "ratings_short.csv";  // There should be 5 raters.
    // String ratingsFilename = "ratings.csv";     // There should be 1048 raters.
    
    int minimalRatings = 3;
    
    String specifiedTitle = "The Godfather";
    
    SecondRatings sr;
    
    public void initializeSecondRatingsObject () throws java.io.IOException {
        sr = new SecondRatings(moviesFilename, ratingsFilename);
        
        int movieListSize = sr.getMovieSize();
        int raterListSize = sr.getRaterSize();
        
        System.out.println(moviesFilename + " contains " + movieListSize + " Movie entries.");
        System.out.println(ratingsFilename + " contains " + raterListSize + " Rater entries.");
        
        
    } 
    
    public void printMoviesSortedByRating () {
        
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRatings);
        // getAverageRatings only includes on the list of avgRatings movies that had at least the minimalRatings number.
        Collections.sort(ratings);
        System.out.println("Printing list of Average Ratings of Movies that recieved at least " + minimalRatings + " ratings.");
        for (Rating r : ratings) {
            System.out.println(r.getValue() + "\t" + sr.getTitle(r.getItem()));
        }
    }
    
    private void getAverageRatingByTitle () {
        String movieID = sr.getID(specifiedTitle);
        System.out.println("movieID set to " + movieID);
        double avgRating = sr.getAverageRatingByMovieID (movieID, minimalRatings);
        int ratingCount = sr.getRatingCount(movieID);
        System.out.println("The movie \"" + specifiedTitle + "\" recieved " + ratingCount + " ratings, averaging " + avgRating);
    }
    
    public void runTests() throws java.io.IOException{
        initializeSecondRatingsObject();
        printMoviesSortedByRating ();
        getAverageRatingByTitle();
    }
    
    
    public static void Main (String args[]) {
        
    }
}




// In the MovieRunnerAverage class, write the void method getAverageRatingOneMovie,
// which has no parameters. This method should first create a SecondRatings object,
// reading in data from the movie and ratings data files. Then this method should print out
// the average ratings for a specific movie title, such as the movie “The Godfather”. If the
// moviefileis set to the file named ratedmovies_short.csv,and the ratingsfileis set to
// the file ratings_short.csv, then the average for the movie “The Godfather” would be
// 9.0.