/**
 * MinutesFilter constructor takes two int parameters representing min and max length in minutes.
 * Default constructor takes no parameters and assumes min = 0 and max = 120.
 * satisfies method returns true if a movie’s running time is at least min minutes and no more than max minutes.
 * 
 * 
 * @author Nigel Wilson
 * @version 11 Oct 2019
 */
public class MinutesFilter implements Filter {
    int minRuntime;
    int maxRuntime;
    
    public MinutesFilter(int min, int max) {
        minRuntime = min;
        maxRuntime = max;
    }
    public MinutesFilter() {
        System.out.println("Warning: MinutesFilter used its default constructor. May be missing runtime parameters.");
        minRuntime = 0;
        maxRuntime = 120;
    }
    
    @Override
    public boolean satisfies(String id) {
        // For a given movieID we call that movie's getGenres method "through" the DB. This gets ALL genres as one string.
        int runtime = MovieDatabase.getMinutes(id);
        return (runtime >= minRuntime && runtime <= maxRuntime);
    }

}

