
/**
 * Given any movieID this filter passes it.  Use to get unfiltered complete list. 
 * 
 * @author Nigel Wilson 
 * @version 13 Oct 2019
 */
public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
