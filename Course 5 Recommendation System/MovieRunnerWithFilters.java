/**
 * This runner serves to test the ThirdRatings class and its implementation using MoviesDatabase. 
 * 
 * MovieRunnerWithFilters applies filters then computes the average rating of movies on the filtered list. 
 * It includes an improoved verion of the printAverageRatings method from the MovieRunnerAverage class.
 * 
 * Dependency:  ThirdRatings
 * 
 * @author Nigel Wilson 
 * @version 11 Oct 2019
 */

import java.util.Collections;
import java.util.ArrayList;

public class MovieRunnerWithFilters {
    int minRaters = 3;
        
    // String moviesFilename = "ratedmovies_short.csv";   
    String moviesFilename = "ratedmoviesfull.csv";
        
    //String ratingsFilename = "ratings_short.csv";   
    String ratingsFilename = "ratings.csv";    
    
    private ThirdRatings tr;
    
    // The instructions had me duplicating this for every version of printAverageRatingsBy... so I abstracted it out.
    // Sets up the TR object, including loading its raterList. 
    // Initializes the MovieDatabase.
    // Method specific parameters are kept inside thier relevant scopes. 
    private void setup() throws java.io.IOException{
        // Create ThirdRatings object instead of a SecondRatings object.
        tr = new ThirdRatings(ratingsFilename);
        
        // TO test creation print the number of raters.
        System.out.println("The number of raters in the " + ratingsFilename + " was " + tr.getRaterSize());
        
        // TO access movie data initialize MovieDatabase from moviesFilename.
        MovieDatabase.initialize(moviesFilename);
        
        // TO test database initialization print the number of movies.
        int movieCount = MovieDatabase.filterBy(new TrueFilter()).size(); // filterBy returns an ArrayList<String>.  
        System.out.println("The number of movies in the " + moviesFilename + " was " + movieCount);
    }
    
    public void printAverageRatings() throws java.io.IOException {
        setup();
        
        // TO get how many movies had at least one rating.  MovieDatabase can't help here- We need info from tr's instance var raterList.
        ArrayList<Rating> moviesWithRatingsAvgs = tr.getAverageRatings(minRaters);  
        
        // TO test print how many movies got at least one rating. 
        System.out.println(moviesWithRatingsAvgs.size() + " movies got at least" + " minRaters " + "rating(s)");
            
        // Sort them by rating ascending, and Print. 
        Collections.sort(moviesWithRatingsAvgs); 
        for (Rating r : moviesWithRatingsAvgs) {
            System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(r.getItem()) + "\"");
        }
        
    }
    
    public void printAverageRatingsByYear() throws java.io.IOException {
        int oldestYearSpecified = 2000;
        
        setup();    
         
        // Print the number of movies found, and for each movie found, print its rating, its year, and its title. For example, if you run the printAverageRatingsByYear method on the files ratings_short.csv and ratedmovies_short.csv with a minimal rater of 1 and the year 2000, you should see        
        Filter yearFilter = new YearAfterFilter(oldestYearSpecified);
        ArrayList <Rating> recentMovieRatings = tr.getAverageRatingsByFilter(minRaters, yearFilter);
        
        int countRatedRecentMovies = recentMovieRatings.size();
        
        // TO test print how many movies got at least one rating. 
        System.out.println(countRatedRecentMovies + " movies got at least " + minRaters + " rating(s) and were made in " + oldestYearSpecified + " or later.");
            
        // Sort them by rating ascending, and Print. 
        Collections.sort(recentMovieRatings);  
        for (Rating r : recentMovieRatings) {
            String id = r.getItem();
            System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\" " + MovieDatabase.getYear(id));
        }        
    }

    
    public void printAverageRatingsByGenre() throws java.io.IOException {
        String genre = "Comedy";
        
        setup();
        
        // Create a GenreFilter and call getAverageRatingsByFilter
        GenreFilter gf = new GenreFilter(genre);
        ArrayList <Rating> movieRatingsByGenre = tr.getAverageRatingsByFilter(minRaters, gf);
            
        // Count, Sort, and Print. 
        System.out.println(movieRatingsByGenre.size() + " movies got at least " + minRaters + " rating(s) and were of genre " + genre + ".");
        Collections.sort(movieRatingsByGenre);  
        for (Rating r : movieRatingsByGenre) {
            String id = r.getItem();
            System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"");
            System.out.println("Genres: " + MovieDatabase.getGenres(id));
        }        
    }
    
    // Print the number of movies found, and for each movie print its rating, its running time, and its title on one line. For example, if you run the printAverageRatingsByMinutes method on the files ratings_short.csv and ratedmovies_short.csv with a minimal rater of 1, minimum minutes of 110, and maximum minutes of 170, then you should see
    public void printAverageRatingsByMinutes() throws java.io.IOException {
        int min = 105;
        int max = 135;
        
        setup();
        MinutesFilter mf = new MinutesFilter(min, max);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, mf);
        
        // Count, Sort, and Print. 
        System.out.println(filteredList.size() + " movies got at least " + minRaters + " rating(s) and were between " + min + " and " + max + " minutes.");
        Collections.sort(filteredList);  
        for (Rating r : filteredList) {
            String id = r.getItem();
            // Next line commented print out since console overflowed and I need to see list size.
            // System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"  Runtime: " + MovieDatabase.getMinutes(id));
        }   
    }
    
    
    public void printAverageRatingsByDirectors () throws java.io.IOException {
        String soughtDirectors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
    
        setup();
        DirectorsFilter df = new DirectorsFilter(soughtDirectors);
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, df);
        
        // Count, Sort, and Print. 
        System.out.println(filteredList.size() + " movies got at least " + minRaters + " rating(s) and had at one of the sought directors.");
        // Collections.sort(filteredList);  
        // for (Rating r : filteredList) {
            // String id = r.getItem();
            // System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"");
            // System.out.println("\tDirectors: " + MovieDatabase.getDirector(id));
        // }   
    }
    
    public void printAverageRatingsByYearAfterAndGenre() throws java.io.IOException {
        int year = 1990;
        String genre = "Drama";
        
        setup();
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, af);
    
        // Count, Sort, and Print. 
        System.out.println(filteredList.size() + " movies met year, genre, and minimum rater criteria.");
        // Collections.sort(filteredList);  
        // for (Rating r : filteredList) {
            // String id = r.getItem();
            // System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"");
            // System.out.println("\tGenres: " + MovieDatabase.getGenres(id));
        // }   
    } 

    
    public void printAverageRatingsByDirectorsAndMinutes() throws java.io.IOException {
        int minRuntime = 90; 
        int maxRuntime = 180;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        
        setup();
        AllFilters af = new AllFilters();
        af.addFilter(new MinutesFilter(minRuntime, maxRuntime));
        af.addFilter(new DirectorsFilter(directors));
        
        ArrayList<Rating> filteredList = tr.getAverageRatingsByFilter(minRaters, af);
    
        // Count, Sort, and Print. 
        System.out.println(filteredList.size() + " movies met runtime, director, and minimum ratings criteria.");
        // Collections.sort(filteredList);  
        // for (Rating r : filteredList) {
            // String id = r.getItem();
            // System.out.println(r.getValue() + " \"" + MovieDatabase.getTitle(id) + "\"");
            // System.out.println("\tDirectors: " + MovieDatabase.getDirector(id));
        // }   
    }
}
