
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> movieList;
    private ArrayList<Rater> raterList;
    
    public SecondRatings() throws java.io.IOException {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    public SecondRatings (String moviefile, String ratingsfile) throws java.io.IOException {
        // this("ratedmoviesfull.csv", "ratings.csv");
        FirstRatings fr = new FirstRatings();
        movieList = fr.loadMovies(moviefile);
        raterList = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize () {
        return movieList.size();
    }
    public int getRaterSize () {
        return raterList.size();
    }
    
    
    
    public ArrayList<Rating> getAverageRatings (int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for (Movie m : movieList) {
            String id = m.getID();
            // Is a ZERO star rating possible?  Does avg rating 0.0 necesarily imply that more than min number of ratings was recieved. 
            double avg = getAverageRatingByMovieID(id, minimalRaters);    
            if (avg != 0.0) {
                Rating rating = new Rating(id, avg);
                avgRatings.add(rating);
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
    
    public String getTitle (String movieID) {
        String title = "No title found. movieID not on movieList.";
        for (Movie m : movieList) {
            if (m.getID() == movieID) {
                title = m.getTitle();
            } 
        }
        return title;
    }
    
    
    // Note that the movie title must be spelled exactly as it appears in the movie data files.
    public String getID (String title) {
        String id = "No ID found. title not on movieList.";
        for (Movie m : movieList) {
            if (m.getTitle().equals(title)) {
                id = m.getID();
            } 
        }
        if (id == "No ID found. title not on movieList.") {
            System.out.println("No ID found. title not on movieList.");
        }
        return id;
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

