
/**
 * Write a description of MinutesFilter here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes){
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }

    @Override
    public boolean satisfies(String id) {
        int minutes = MovieDatabase.getMinutes(id);
        return  minutes >= minMinutes && minutes <= maxMinutes ;
    }

}
