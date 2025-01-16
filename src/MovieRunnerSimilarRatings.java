
/**
 * Write a description of MovieRunnerSimilarRatings here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerSimilarRatings {

    public void printAverageRatings(int minimalRaters) {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        ArrayList<Rating> averageRatings = fr.getAverageRatings(minimalRaters);
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings) {
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);

            System.out.println(String.format("%s %s", rating, movieTitle));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int minimalRaters, int year, String genre) {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        YearAfterFilter yf = new YearAfterFilter(year);
        GenreFilter gf = new GenreFilter(genre);
        AllFilters af = new AllFilters();
        af.addFilter(yf);
        af.addFilter(gf);

        ArrayList<Rating> averageRatings = fr.getAverageRatingsByFilter(minimalRaters, af);
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings) {
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s %s", rating, movieYear, movieTitle));
            System.out.println(String.format("    %s", movieGenres));

        }
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");

    }

    public void printSimilarRatings() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        //RaterDatabase.initialize("ratings_short.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");
        System.out.println("Recommended movies based on your likings: ");

        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatings("71", 20, 5);
        for (Rating movieSimilarRating : movieSimilarRatings) {
            double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            System.out.println(String.format("%s %s", movieTitle, rating));

        }
    }

    public void printSimilarRatingsByGenre() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");
        System.out.println("Recommended movies based on your likings: ");

        GenreFilter gf = new GenreFilter("Mystery");

        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatingsByFilter("964", 20, 5, gf);
        for (Rating movieSimilarRating : movieSimilarRatings) {
            double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s", movieTitle, rating));
            System.out.println(String.format("    %s", movieGenres));

        }
    }

    public void printSimilarRatingsByDirector() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");
        System.out.println("Recommended movies based on your likings: ");

        DirectorsFilter df = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");

        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatingsByFilter("120", 10, 2, df);
        for (Rating movieSimilarRating : movieSimilarRatings) {
            double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s", movieTitle, rating));
        }
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");
        System.out.println("Recommended movies based on your likings: ");

        GenreFilter gf = new GenreFilter("Drama");
        MinutesFilter mf = new MinutesFilter(80, 160);

        AllFilters af = new AllFilters();
        af.addFilter(gf);
        af.addFilter(mf);

        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatingsByFilter("168", 10, 3, af);
        for (Rating movieSimilarRating : movieSimilarRatings) {
            double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s", movieTitle, rating));
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        System.out.println(RaterDatabase.size() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");
        System.out.println("Recommended movies based on your likings: ");

        YearAfterFilter yf = new YearAfterFilter(1975);
        MinutesFilter mf = new MinutesFilter(70, 200);

        AllFilters af = new AllFilters();
        af.addFilter(yf);
        af.addFilter(mf);

        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatingsByFilter("314", 10, 5, af);
        for (Rating movieSimilarRating : movieSimilarRatings) {
            double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s", movieTitle, rating));
        }
    }
}
