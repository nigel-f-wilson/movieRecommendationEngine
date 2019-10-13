// AllFilters() initializes an object. No parameter constructor.
// addFilter(Filter f) adds individual filters to this AllFilters() object.
// satisfies() runs each of the added filters and
// Returns the INTERSECTION of their respective satisfying lists.


import java.util.ArrayList;

public class AllFilters implements Filter {
    ArrayList<Filter> filters;
    
    public AllFilters() {
        filters = new ArrayList<Filter>();
    }

    public void addFilter(Filter f) {
        filters.add(f);
    }

    @Override
    public boolean satisfies(String id) {
        for(Filter f : filters) {
            if (! f.satisfies(id)) {
                return false;
            }
        }
        
        return true;
    }

}