
/**
 * The constructor should have one parameter named genre representing one genre, and the satisfies
 * method should return true if a movie has this genre. 
 * Note that movies may have several genres.
 * Influence on how to parse these multi-genre strings came from FirstRatings.countComedies().
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre) {
        myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        // For a given movieID we call that movie's getGenres method "through" the DB. This gets ALL genres as one string.
        return MovieDatabase.getGenres(id).contains(myGenre);
    }

}

