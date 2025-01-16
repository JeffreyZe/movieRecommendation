
/**
 * Write a description of DirectorsFilter here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */
import java.util.*;
import java.lang.*;

public class DirectorsFilter implements Filter {
    private String directors;

    public DirectorsFilter(String directors){
        this.directors = directors;
    }

    @Override
    public boolean satisfies(String id) {
        int minutes = MovieDatabase.getMinutes(id);

        ArrayList<String> directorsList = new ArrayList<>(Arrays.asList(directors.split(",")));
        for (String director : directorsList){
            if (MovieDatabase.getDirector(id).contains(director.trim())){
                return true;
            }
        }

        return false;
    }

}

