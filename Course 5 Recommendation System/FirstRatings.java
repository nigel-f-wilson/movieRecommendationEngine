
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.nio.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    ArrayList<Movie> movieList;
    Map<String,Integer> directorMovieCountMap;
    
    public ArrayList<Movie> loadMovies (String filename) throws java.io.IOException {
        // filename is a CSV file of movie information.
        String path = "data/" + filename;
        FileResource fr = new FileResource(path);
        System.out.println("FileResource created from data/" + filename);
        
        // get CSV Parser.
        boolean hasHeaderRow = true;
        CSVParser parser = fr.getCSVParser(hasHeaderRow);
        if (hasHeaderRow == true) {
            printHeaderRowInfo(parser);
        }
        
        // convert CSV to ArrayList<Movie>.
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        for (CSVRecord r : parser.getRecords()) {
            // Movie entry = new Movie(r.get(id), get(title), get(year), get(genre), get(director), get(country), get(poster), get(minutes));
            Movie entry = new Movie(r.get(0), r.get(1), r.get(2), r.get(4), r.get(5), r.get(3), r.get(7), Integer.parseInt(r.get(6)));
            movieList.add(entry);
        }
        return movieList;
    }
    private void printHeaderRowInfo (CSVParser parser) {
        Map<String, Integer> headerMap = parser.getHeaderMap();
        System.out.println("The headers for this CSV Record are: ");
        int columnIndex = 0;
        for (String heading : headerMap.keySet()) {
            System.out.print(columnIndex + ") " + heading + ", ");
            columnIndex++;
        }
        System.out.println(); // just to space out the console for easier reading.
    }
    
    // @ Test
    public void autoTester () throws java.io.IOException {
        // String filename = "ratedmovies_short.csv";  // size should be 5.
        // String filename = "ratedmovies.csv";       // size should be 3143.
        String filename = "ratedmoviesfull.csv";
        
        boolean loadMoviesWorked = testLoadMovies(filename);
        if (loadMoviesWorked = false) {
            throw new java.io.IOException("method testLoadMovies failed due to bad filename parameter.");
        }
        
        int comedies = countComedies();
        int countRuntimeOver150min = countByRuntime(150);
        makeDirectorMovieCountMap();
        int maxCount = maxCountByOneDirector();
        ArrayList<String> mostProlificDirectors = getDirectorsByMovieCount (maxCount);
        
        System.out.println("The number of movies that were Comedy: " + comedies);
        System.out.println("The number of movies that were longer than 150 minutes: " + countRuntimeOver150min);
        System.out.println("The most prolific director(s) made movies: " + maxCount + " movies.");
        System.out.println("They were: " + Arrays.toString(mostProlificDirectors.toArray()));
    }
    // Read file into ArrayList, print size and each entry if list is short.
    private boolean testLoadMovies (String filename) throws java.io.IOException {
        boolean loadMoviesWorked = false;
            // since loadMovies save movies to a state variable ArrayList<Movie> this method does not return an ArrayList<Movie>
            // it is a Test. A simple call to loadMovies that returns true if it worked. 
            // This is my first attempt at refactoring something in light of what I read about TDD.
        movieList = loadMovies(filename);
        int size = movieList.size();
        System.out.println("\r\nThe number of movies loadMovies just read in: " + size);
        if (size <= 10) {
            System.out.println("They were: ");
            for (Movie m : movieList) {
                System.out.println(m.toString());
            }
        } else {
            System.out.println("There were more than 10 so they are not being printed out.");
        }
        return loadMoviesWorked = true;
        
        
    
        // catch (java.io.IOException) {
            // System.out.println("java.io.IOException loadMovies could not interpret String parameter filename.");
            // // System.exit() ??  // What do i do since i don't know yet how to solicit a new entry for the parameter? 
            // return loadMoviesWorked = false;
        // } 
    }
    
    // How many movies include the genre "Comedy"?
    private int countComedies() {
        String stringToCount = "Comedy";
        int count = 0;   
        for (Movie m : movieList) {
            if (m.getGenres().contains(stringToCount)) {
                count++;
            }
        }    
        return count;
    }
    
    // How many movies are greater than 150 minutes long?
    private int countByRuntime(int minMinutes) {
        int count = 0;   
        for (Movie m : movieList) {
            if (m.getMinutes() > minMinutes) {
                count++;
            }
        }
        return count;
    }
    
    // What is the greatest number of movies by any one director?
    private void makeDirectorMovieCountMap () {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        for (Movie m : movieList) {
            // director - one String of one or more directors of the movie separated by commas.
            String[] directorArray = m.getDirector().split(",");
            // make Map of directors and their movie counts.
            for (int k = 0; k < directorArray.length; k++) {
                String currentDirector = directorArray[k].trim();
                if (!map.containsKey(currentDirector)) {   // would have used method putIfAbsent but I have to take action in either case so it was easier to structure as if-else.
                    map.put(currentDirector,1);
                } else {
                    map.put(currentDirector, (map.get(currentDirector)+1));
                }
            }
        }
        directorMovieCountMap = map;
    } 
    
    // makes use of state variable Map<String,Integer> directorMovieCountMap.
    private ArrayList<String> getDirectorsByMovieCount (int count) {
        ArrayList<String> list = new ArrayList<String>();  // "most prolific director not found.";  
        for (Map.Entry<String,Integer> e : directorMovieCountMap.entrySet()) {
            if (e.getValue() == count) {
                list.add(e.getKey());
            }
        }  
        if (list.size() == 0) {
            System.out.println("getDirectorsByMovieCount failed. List length zero.");
        }       
        return list;
    }
    
    private int maxCountByOneDirector () {
        int maxCountByOneDirector = 0;
        for (Map.Entry<String,Integer> e : directorMovieCountMap.entrySet()) {
            if (e.getValue() > maxCountByOneDirector) {
                maxCountByOneDirector = e.getValue();
                // mostProlificDirector = e.getKey();
            }
        }  
        return maxCountByOneDirector;
    }
    
    
    
    public ArrayList<Rater> loadRaters (String filename) throws java.io.IOException {
        // filename is a CSV file of movie Rater and Rating information.
        String path = "data/" + filename;
        FileResource fr = new FileResource(path);
        System.out.println("FileResource created from \"data/" + filename + "\"");
        
        // get CSV Parser.
        boolean hasHeaderRow = true;
        CSVParser parser = fr.getCSVParser(hasHeaderRow);
        if (hasHeaderRow == true) {
            printHeaderRowInfo(parser);
        }
        
        // Make ArrayList of Raters, populate it, and return it.
        ArrayList<Rater> ratersList = new ArrayList<Rater>();
        for (CSVRecord r : parser.getRecords()) {
            String raterID = r.get(0).trim();    // get ID of rater who made this rating.
            ifRaterIsNotOnListYetAddThem(ratersList, raterID);
            // rater.addRating(rating.getItem(), rating.getValue());
            for (Rater x : ratersList) {
                if (x.getID().equals(raterID)) {
                    // x.addRating(r.get("movie_id"), r.get("rating")); 
                    x.addRating(r.get(1), Double.parseDouble(r.get(2)));
                }
            } 
        }
        return ratersList;
    }
    private void ifRaterIsNotOnListYetAddThem(ArrayList<Rater> ratersList, String raterID) {
        if (!raterIsOnList(ratersList, raterID)) {
            Rater newRater = new Rater(raterID);
            ratersList.add(newRater);
        }
    }
    private boolean raterIsOnList(ArrayList<Rater> ratersList, String raterID) {
        boolean ifRaterIsOnList = false;
        for (Rater rater : ratersList) {
            if (rater.getID().equals(raterID)) {
                ifRaterIsOnList = true;
                break;
            }
        }
        return ifRaterIsOnList;
    }
    private Rater getRaterByID(ArrayList<Rater> ratersList, String raterID) {
        for (Rater rater : ratersList) {
            if (rater.getID().equals(raterID)) {
                return rater;
            }
        }
        System.out.println("Possible Error: getRaterByID called on nonexistant raterID.");
        return new Rater(raterID);
    }
    
    public void testLoadRaters () throws java.io.IOException {
        // String filename = "ratings_short.csv";  // There should be 5 raters.
        String filename = "ratings.csv";     // There should be 1048 raters.
        
        // Read file of ratings into ArrayList of Raters.
        // If the Rater ID has been seen before add this Rating to their list. 
        // Else add new entry to ArrayList of Raters, with this rating as the first entry in the new Rater's list of Ratings. 
        ArrayList<Rater> ratersList = loadRaters(filename);
        int size = ratersList.size();
        System.out.println("\r\nThe number of unique Raters in the Rating list loadRaters just read in: " + size);
        if (size <= 10) {  // print list of raters if list is short.
            System.out.println("They were: ");
            for (Rater rater : ratersList) {
                System.out.println("Rater " + rater.getID() + " submitted " + rater.numRatings() + " ratings.  They were: ");
                for (String ratedItemID : rater.getItemsRated()) {  // rater.getItemsRated() returns an ArrayList<String>. The string is the Item rated. Must separately access the rating given to that Item.
                    System.out.println("\tItem #" + ratedItemID + " recieved a rating of: " + rater.getRating(ratedItemID));
                }
            }
        }
        
        // How many ratings were submitted by Rater with specified ID?
        // String specifiedRaterID = "2";
        String specifiedRaterID = "193";
        int countRatingsBySpecifiedRater = getRaterByID(ratersList, specifiedRaterID).numRatings();
        // int countRatingsBySpecifiedRater = ratersList.get(specifiedRaterID).numRatings();  
        // This was broken because it get from ArrayList gets that index, not the Rater that has that RaterID.
        System.out.println("The rater with ID " + specifiedRaterID + " submitted ratings for " + countRatingsBySpecifiedRater + " movies.");
        
        
        // What is the greatest number of Ratings submitted by any one Rater?
        int maxNumberOfRatings = 0;
        for (Rater r : ratersList) {
            if (r.numRatings() > maxNumberOfRatings) {
                maxNumberOfRatings = r.numRatings();
            }
        }
        System.out.println("The most ratings submitted by any one Rater was: " + maxNumberOfRatings);
        
        
        // How many raters submitted that many Ratings?  What were those Rater's IDs?
        int countOfMostProlificRaters = 0;
        StringBuilder mostProlificRaters = new StringBuilder();
        for (Rater r : ratersList) {
            if (r.numRatings() == maxNumberOfRatings) {
                countOfMostProlificRaters++;
                mostProlificRaters.append(r.getID() + ", "); 
            }
        }
        System.out.println(countOfMostProlificRaters + " Raters tied for most prolific. They were " + mostProlificRaters.toString());
            
        // How many ratings were submitted for a specified Movie?
        String specifiedMovieID = "1798709";
        int countOfRatings = 0;
        ArrayList<Rater> ratersWhoRatedSpecifiedMovie = new ArrayList<Rater>();
        StringBuffer IDsOfRatersWhoRatedSpecifiedMovie = new StringBuffer();
        for (Rater r : ratersList) {
            if (r.hasRating(specifiedMovieID)) {
                countOfRatings++;
                ratersWhoRatedSpecifiedMovie.add(r);
                IDsOfRatersWhoRatedSpecifiedMovie.append(" " + r.getID() + ", ");
            }
        }
        System.out.println("The movie with ID " + specifiedMovieID + " recieved " + countOfRatings + " ratings.");
        System.out.println("The raters who rated movie with ID " + specifiedMovieID + " were " + IDsOfRatersWhoRatedSpecifiedMovie);
        
        
        
        
        // How many distinct movies were rated by every rater who rated the above SpecifiedMovie?
        ArrayList<String> listOfMoviesRatedByALL = new ArrayList<String>();
        
        Rater firstRater = ratersWhoRatedSpecifiedMovie.get(0);
        ArrayList<String> workingList = firstRater.getItemsRated();
        for (Rater rater : ratersWhoRatedSpecifiedMovie) {
            ArrayList<String> moviesRatedByCURRENT = rater.getItemsRated();
            workingList = intersection(moviesRatedByCURRENT, workingList);
        }
        listOfMoviesRatedByALL = workingList;
        
        int countOfOtherMoviesRatedByTheseRaters = listOfMoviesRatedByALL.size() - 1;
        System.out.println("The raters who rated movie with ID " + specifiedMovieID + " ALL also submitted ratings for " + countOfOtherMoviesRatedByTheseRaters + " other movies.");
        System.out.println("Those movies they all rated were: " + listOfMoviesRatedByALL.toString());  
        
        // How many distinct movies were rated?
        ArrayList<String> moviesRatedList = new ArrayList<String>();
        for (Rater r : ratersList) {
            ArrayList<String> currentList = r.getItemsRated();
            for (String s : currentList) {
                if (!moviesRatedList.contains(s)) {
                    moviesRatedList.add(s);
                }
            }
        }
        System.out.println("The number of unique movies rated was " + moviesRatedList.size());
    }
    
    private ArrayList<String> intersection(List<String> list1, List<String> list2) {
        ArrayList<String> list = new ArrayList<String>();
        for (String s : list1) {
            if(list2.contains(s)) {
                list.add(s);
            }
        }
        return list;
    }
}
