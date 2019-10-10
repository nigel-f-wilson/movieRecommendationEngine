
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Collections;
import java.util.ArrayList;


public class MovieRunnerAverage {
    public void printAverageRatings() throws java.io.IOException {
        /*
         * TEST FILENAME PARAMETERS
         */ 
        //String moviesFilename = "ratedmovies_short.csv";  // size should be 5.
        // String moviesFilename = "ratedmovies.csv";       // size should be 3143.
        String moviesFilename = "ratedmoviesfull.csv";
        
        // String ratingsFilename = "ratings_short.csv";  // There should be 5 raters.
        String ratingsFilename = "ratings.csv";     // There should be 1048 raters.
        
        
        SecondRatings sr = new SecondRatings(moviesFilename, ratingsFilename);
        System.out.println("The number of movies in the " + moviesFilename + " was " + sr.getMovieSize());
        System.out.println("The number of movies in the " + ratingsFilename + " was " + sr.getRaterSize());
        
        /*
         * TEST SPECIFY MINIMUM NUMBER OF RATERS
         */ 
        int minRaters = 12;
        
        // Returns an unsorted ArrayList, already filtering out any movie with too few ratings.
        ArrayList<Rating> list = sr.getAverageRatings(minRaters);
        Collections.sort(list);
        // Collections.reverse(list);
        // Since the Rating class implements the Comparable interface (has method compareTo()) we can here use the AL.sort method.
        for (Rating r : list) {
            System.out.println("\"" + sr.getTitle(r.getItem()) + "\" had avg rating: " + r.getValue());
        
        }
        
        // Using the files ratedmoviesfull.csv and ratings.csv, what is the rating of the movie “Moneyball”?
        String title = "Moneyball";
        String id = sr.getID(title);
        double avg = sr.getAverageRatingByMovieID(id, minRaters);
        System.out.println(title + " got an avg rating of " + avg + "\r\n");
        
        title = "Vacation";
        id = sr.getID(title);
        avg = sr.getAverageRatingByMovieID(id, minRaters);
        System.out.println(title + " got an avg rating of " + avg + "\r\n");
        
        title = "The Maze Runner";
        id = sr.getID(title);
        avg = sr.getAverageRatingByMovieID(id, minRaters);
        System.out.println(title + " got an avg rating of " + avg + "\r\n");
        
    }

}
