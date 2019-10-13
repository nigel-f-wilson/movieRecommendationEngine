/**
 * MovieDatabase is a pseudo-database that gives the other Classes in this project shared state.
 * This class is an efficient way to get information about movies. It stores movie information 
 * in a HashMap for fast lookup of movie information given a movie ID. The class also allows filtering 
 * movies based on queries. All methods and fields in the class are static. This means you'll be 
 * able to access methods in MovieDatabase without using new to create objects, but by calling methods 
 * like MovieDatabase.getMovie("0120915"). 
 * 
 * Getter methods include getYear, getTitle, getMovie, getPoster, getMinutes, getCountry, getGenres, and getDirector. 
 * Each of these takes a String movie ID as a parameter and returns information about that movie.
 *
 * A size method that returns the number of movies in the database.
 * 
 * @author Nigel Wilson 
 * @version 10 Oct 2019
 */

import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;

public class MovieDatabase  {
    
    // maps a movie ID String to a Movie object 
    private static HashMap<String, Movie> ourMovies;

    
    // See Link:  https://stackoverflow.com/questions/6171265/best-way-to-exit-a-program-when-i-want-an-exception-to-be-thrown
    // This IOException shouldn't be an issue.  I wanted to avoid having to declare that every method that calls the no-param 
    // version of initialize() might throw an IOException.
    public static void initialize(String moviefile) {
        try { 
            if (ourMovies == null) {
                ourMovies = new HashMap<String,Movie>();
                loadMovies(moviefile);
            }
        }
        catch (Exception IOException) {
            System.out.println("Initialization of HashMap ourMovies in movieDatabase failed.");
            System.out.println("An invalid filename of movie records was provided.");
            initialize();
        }
    }

    // private initialize method will load the default movie file ratedmoviesfull.csv if no file has been loaded. 
    // This method is called as a safety check with any of the other public methods to make sure there is movie data in the database.
    // This avoids throwing a java.io.IOException which would be thrown if the one parameter version of initialize() had not 
    // been called first and sucessfully initialized ourMovies. 
    // There is no mechanism to alert the user they are using default movielist files. 
    private static void initialize()   {        
       try { 
           if (ourMovies == null) {
               //  Notify user if default movie files are being used in case they forgot to provide a file but meant to. 
               System.out.println("No filename was provided to initialize the MovieDatabase");
               System.out.println("Initializing the MovieDatabase from \"data/ratedmoviesfull.csv\"");
               ourMovies = new HashMap<String,Movie>();
               loadMovies("ratedmoviesfull.csv");
           }
       }
       catch (Exception IOException) {
           System.out.println("Initialization of HashMap ourMovies in movieDatabase failed.");
           System.out.println("A valid filename of movie records in CSV was not provided.");
           System.out.println("And the default filename \"data/ratedmoviesfull.csv\" failed to load as backup.");
           System.out.println("Caught IOException from FirstRatings.loadMovies() and crashed.");
           System.exit(1);
       }
    }   

    
    // initialize calls this method after ensuring it has not been called before since ourMovies is supposed to be a static map.
    // this cannot be called outside of initialize() since it is private.
    private static void loadMovies(String filename) throws java.io.IOException {
        // This void method operates therough the sideffect that it updates the Databases state:  HashMap<String, Movie> ourMovies;
        String filepath = "data/" + filename;
        FileResource fr = new FileResource(filepath);
        CSVParser parser = fr.getCSVParser();
        
        // convert CSV to HashMap<String, Movie>.
        HashMap<String, Movie> movieMap = new HashMap<String, Movie>();
        for (CSVRecord r : parser.getRecords()) {
            // Movie entry = new Movie(r.get(id), get(title), get(year), get(genre), get(director), get(country), get(poster), get(minutes));
            // ********* this will work but use this as an oportunity to practice with GitHub.  Make this loop more readable. 
            String movieID = r.get(0);
            Movie entry = new Movie(r.get(0), r.get(1), r.get(2), r.get(4), r.get(5), r.get(3), r.get(7), Integer.parseInt(r.get(6)));
            movieMap.put(movieID, entry);
        }
        // Update State
        ourMovies = movieMap;
    }
    
    

    public static boolean containsID(String id)  {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            // you pass a filter object to this filterBy method. 
            // Every filter has a .specifies method that takes a String movieID.
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

}
