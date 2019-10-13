/**
 * DirectorsFilter constructor has one parameter, a list of directors separated by commas (Example: "Charles Chaplin,Michael Mann,Spike Jonze", 
 * satisfies return true if a movie has at least one of these directors as one of its directors. 
 * Note that each movie may have several directors.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class DirectorsFilter implements Filter {
    private String[] filtersDirectorList;
    
    public DirectorsFilter(String directors) {
        filtersDirectorList = directors.split(",");  // Since .split goes straight to a StringArray never having a String to .trim.
        // for (String s : filtersDirectorList) {
            // System.out.println(s);
        // }
    }
    
    @Override
    public boolean satisfies(String id) {
        String movieDirectors = MovieDatabase.getDirector(id);
        // for (String s : movieDirectors) {
            // System.out.println(s);
        // }
        
        
        for (String director : filtersDirectorList) {
            if (movieDirectors.contains(director.trim())) {
                return true;
            }
        }
        return false;
    }

}
