
/**
 * Write a description of ThirdRatings here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingFile) {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingFile);
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
            return -1;
        }

        return ratingsTotal/ratingsCount;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters)
    {
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        for (String movieId : movieIDs) {
            double averageRating = getAverageByID(movieId, minimalRaters);
            if (averageRating >=0) { // here I assumed that the averageRating will not be 0 for ratingsTotal>minimalRaters
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }

        return averageRatings;
    }

    //return Ratings of movies that have at least minimalRaters ratings and satisfies the filter criteria
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        ArrayList<String> movieIdsFiltered = MovieDatabase.filterBy(filterCriteria); // return movie ids
        for (String movieId : movieIdsFiltered){
            double averageRating = getAverageByID(movieId, minimalRaters);
            if (averageRating >=0){
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }
        return averageRatings;
    }
}
