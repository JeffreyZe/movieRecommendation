
/**
 * Write a description of MovieRunnerAverage here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage {

    public void printAverageRatings(int minimalRaters){
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        // SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        //SecondRatings sr = new SecondRatings("data/movies_step2.csv", "data/ratings_step2.csv");
        System.out.println(sr.getMovieSize() + " movie(s) and " + sr.getRaterSize() + " rater(s) are loaded");

        ArrayList<Rating> averageRatings = sr.getAverageRatings(minimalRaters);

        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = sr.getTitle(movieId);

            System.out.println(String.format("%s %s", rating, movieTitle));
        }
    }

    public void getAverageRatingOneMovie(){
        // SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");

        //SecondRatings sr = new SecondRatings("data/movies_step2.csv", "data/ratings_step2.csv");
        String movieTitle = "Vacation";
        String movieId = sr.getID(movieTitle);

        ArrayList<Rating> averageRatings = sr.getAverageRatings(0);
        for (Rating averageRating : averageRatings){
            String id = averageRating.getItem();
            if (id.equals(movieId))
            {
                System.out.println("The average rating for " + movieTitle + " is " + averageRating.getValue());
            }
        }
    }

    public static void main(String[] args){
        System.out.println();
        System.out.println("Starting main function in MovieRunnerAverage");

        MovieRunnerAverage mra = new MovieRunnerAverage();
        //mra.getAverageRatingOneMovie();

        mra.printAverageRatings(12);
    }
}
