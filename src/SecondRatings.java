
/**
 * Write a description of SecondRatings here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;

    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String movieFile, String ratingFile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(movieFile);
        myRaters = fr.loadRaters(ratingFile);
    }

    public int getMovieSize(){
        return myMovies.size();
    }

    public int getRaterSize(){
        return myRaters.size();
    }

    private double getAverageByID(String id, int minimalRaters){
        int ratingsCount = 0;
        double ratingsTotal = 0;

        for (Rater rater : myRaters){
            ArrayList<String> moviesRated = rater.getItemsRated();
            if (moviesRated.contains(id)){
                ratingsCount += 1;
                ratingsTotal += rater.getRating(id);
            }
        }

        if (ratingsCount < minimalRaters){
            return 0;
        }

        return ratingsTotal/ratingsCount;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters)
    {
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        for (Movie movie : myMovies) {
            String movieId = movie.getID();
            double averageRating = this.getAverageByID(movieId, minimalRaters);
            if (averageRating >0) { // here I assumed that the averageRating will not be 0 for ratingsTotal>minimalRaters
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }

        return averageRatings;
    }

    public String getTitle(String id){
        for (Movie movie : myMovies){
            if (movie.getID().equals(id)){
                return movie.getTitle();
            }
        }
        return "Movie with ID [" + id + "] was not found";
    }

    public String getID(String title){
        for (Movie movie : myMovies){
            if (movie.getTitle().equals(title)){
                return movie.getID();
            }
        }
        return "NO SUCH TITLE.";
    }
}
