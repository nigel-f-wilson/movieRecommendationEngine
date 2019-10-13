/**
 * MovieRunnerSimilarRatings is made to test and run FourthRatings.
 * 
 * 
 * @author Nigel Wilson 
 * @version 13 Oct 2019
 */

import java.util.*;

public class MovieRunnerSimilarRatings {
    int minRaters = 50;
        
    // String moviesFilename = "ratedmovies_short.csv";   
    String moviesFilename = "ratedmoviesfull.csv";
        
    //String ratingsFilename = "ratings_short.csv";   
    String ratingsFilename = "ratings.csv";    
    
    private FourthRatings fr;
    
    // The instructions had me duplicating this for every version of printAverageRatingsBy... so I abstracted it out.
    // Sets up the TR object, including loading its raterList. 
    // Initializes the MovieDatabase.
    // Method specific parameters are kept inside thier relevant scopes. 
    private void setup() {
        fr = new FourthRatings();
        
        // TO initialize MovieDatabase and test
        MovieDatabase.initialize(moviesFilename); 
        System.out.println("MovieDatabase initialized from " + moviesFilename + ".  " + MovieDatabase.size() + " records loaded.");
        
        // TO initialize RaterDatabase and test
        RaterDatabase.initialize(ratingsFilename);
        System.out.println("RaterDatabase initialized from " + ratingsFilename + ".  " + RaterDatabase.size() + " records loaded.");
    }
    
    public void printAverageRatings() {
        setup();
        
        // TO get how many movies had at least one rating.  MovieDatabase can't help here- We need info from tr's instance var raterList.
        ArrayList<Rating> moviesWithRatingsAvgs = fr.getAverageRatings(minRaters);  
        
        // TO test print how many movies got at least specified number of ratings. 
        System.out.println(moviesWithRatingsAvgs.size() + " movies got at least " + minRaters + " rating(s)");
            
        // Sort them by rating ascending, and Print. 
        Collections.sort(moviesWithRatingsAvgs); 
        for (Rating r : moviesWithRatingsAvgs) {
            System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(r.getItem()) + "\"");
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre()  {
        int year = 1990;
        String genre = "Drama";
        
        setup();
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> filteredList = fr.getAverageRatingsByFilter(minRaters, af);
    
        // Count, Sort, and Print. 
        System.out.println(filteredList.size() + " movies met year, genre, and minimum rater criteria.");
        // Collections.sort(filteredList);  
        // for (Rating r : filteredList) {
            // String id = r.getItem();
            // System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"");
            // System.out.println("\tGenres: " + MovieDatabase.getGenres(id));
        // }   
    } 
}