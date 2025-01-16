/**
 * Write a description of FourthRatings here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {

    private double getAverageByID(String id, int minimalRaters) {
        int ratingsCount = 0;
        double ratingsTotal = 0;

        for (Rater rater : RaterDatabase.getRaters()) {
            ArrayList<String> moviesRated = rater.getItemsRated();
            if (moviesRated.contains(id)) {
                ratingsCount += 1;
                ratingsTotal += rater.getRating(id);
            }
        }

        if (ratingsCount < minimalRaters) {
            return -1;
        }

        return ratingsTotal / ratingsCount;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averageRatings = new ArrayList<>();

        for (String movieId : movieIDs) {
            double averageRating = getAverageByID(movieId, minimalRaters);
            if (averageRating >= 0) { // Assuming averageRating will not be 0 for ratingsTotal > minimalRaters
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }

        return averageRatings;
    }

    // Return ratings of movies that have at least minimalRaters ratings and satisfy the filter criteria
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> averageRatings = new ArrayList<>();
        ArrayList<String> movieIdsFiltered = MovieDatabase.filterBy(filterCriteria); // Return movie IDs

        for (String movieId : movieIdsFiltered) {
            double averageRating = getAverageByID(movieId, minimalRaters);
            if (averageRating >= 0) {
                averageRatings.add(new Rating(movieId, averageRating));
            }
        }

        return averageRatings;
    }

    private double dotProduct(Rater me, Rater r) {
        ArrayList<String> moviesRated = me.getItemsRated();
        double result = 0;

        for (String movieRated : moviesRated) {
            if (r.hasRating(movieRated)) {
                // Both me and r have rated the movie
                // Adjust the ratings with central value 5 on a scale of 0 to 10
                result += (me.getRating(movieRated) - 5) * (r.getRating(movieRated) - 5);
            }
        }

        return result;
    }

    // For the given Rater, compute a similarity rating for each other rater in the RaterDatabase
    // Sorted and only include those raters who have positive similarity ratings
    private ArrayList<Rating> getSimilarities(String id) {
        Rater givenRater = RaterDatabase.getRater(id);
        ArrayList<Rating> similarityRatings = new ArrayList<>();

        for (Rater rater : RaterDatabase.getRaters()) {
            if (!rater.equals(givenRater)) {
                double similarityRating = dotProduct(givenRater, rater);
                if (similarityRating > 0) {
                    similarityRatings.add(new Rating(rater.getID(), similarityRating));
                }
            }
        }

        Collections.sort(similarityRatings, Collections.reverseOrder());
        return similarityRatings;
    }

    // Parameters: a rater ID, numSimilarRaters (top numSimilarRaters with positive ratings), minimalRaters
    // Returns a sorted ArrayList of Ratings for movies and their calculated weighted ratings
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> raterSimilarityRatings = getSimilarities(id);
        int effectiveNumSimilarRaters = Math.min(numSimilarRaters, raterSimilarityRatings.size());
        List<Rating> topRaterSimilarityRatings = raterSimilarityRatings.subList(0, effectiveNumSimilarRaters);
        ArrayList<Rating> movieSimilarRatings = new ArrayList<>();

        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            double movieWeightedRatingTotal = 0;
            int ratingsCount = 0;
            // Loop through each similar rater (only the top numSimilarRaters with positive ratings)
            // calculate weightedRating multiplying rater similarity rating by the rating they gave that movie
            for (Rating raterSimilarityRating: topRaterSimilarityRatings) {
                double raterSimilarityRatingValue = raterSimilarityRating.getValue();
                double movieRating = 0;
                Rater similarRater = RaterDatabase.getRater(raterSimilarityRating.getItem());
                if (similarRater.hasRating(movieId) ){
                    ratingsCount += 1;
                    movieRating = similarRater.getRating(movieId);
                    movieWeightedRatingTotal += raterSimilarityRatingValue * movieRating;
                }
            }

            // System.out.println("movieWeightedRatingTotal: " + movieWeightedRatingTotal);
            if (ratingsCount>=minimalRaters){
                Rating movieSimilarRating = new Rating(movieId, movieWeightedRatingTotal / ratingsCount);
                movieSimilarRatings.add(movieSimilarRating);
            }
        }
        Collections.sort(movieSimilarRatings, Collections.reverseOrder());
        return movieSimilarRatings;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> raterSimilarityRatings = getSimilarities(id);
        int effectiveNumSimilarRaters = Math.min(numSimilarRaters, raterSimilarityRatings.size());
        List<Rating> topRaterSimilarityRatings = raterSimilarityRatings.subList(0, effectiveNumSimilarRaters);
        ArrayList<Rating> movieSimilarRatings = new ArrayList<>();

        for (String movieId : MovieDatabase.filterBy(filterCriteria)) {
            double movieWeightedRatingTotal = 0;
            int ratingsCount = 0;
            // Loop through each similar rater (only the top numSimilarRaters with positive ratings)
            // calculate weightedRating multiplying rater similarity rating by the rating they gave that movie
            for (Rating raterSimilarityRating: topRaterSimilarityRatings) {
                double raterSimilarityRatingValue = raterSimilarityRating.getValue();
                double movieRating = 0;
                Rater similarRater = RaterDatabase.getRater(raterSimilarityRating.getItem());
                if (similarRater.hasRating(movieId) ){
                    ratingsCount += 1;
                    movieRating = similarRater.getRating(movieId);
                    movieWeightedRatingTotal += raterSimilarityRatingValue * movieRating;
                }
            }

            // System.out.println("movieWeightedRatingTotal: " + movieWeightedRatingTotal);
            if (ratingsCount>=minimalRaters){
                Rating movieSimilarRating = new Rating(movieId, movieWeightedRatingTotal / ratingsCount);
                movieSimilarRatings.add(movieSimilarRating);
            }
        }
        Collections.sort(movieSimilarRatings, Collections.reverseOrder());
        return movieSimilarRatings;
    }
}

