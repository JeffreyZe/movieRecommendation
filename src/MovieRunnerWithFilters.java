
/**
 * Write a description of MovieRunnerWithFilters here.
 *
 * @author Jeffrey
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerWithFilters {

    public void printAverageRatings(int minimalRaters){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //MovieDatabase.initialize("data/ratedmoviesfull.csv");
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");


        ArrayList<Rating> averageRatings = tr.getAverageRatings(minimalRaters);
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);

            System.out.println(String.format("%s %s", rating, movieTitle));
        }
    }


    public void printAverageRatingsByYear(int minimalRaters, int year){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        YearAfterFilter yf = new YearAfterFilter(year);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, yf);
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            int movieYear = MovieDatabase.getYear(movieId);
            String movieTitle = MovieDatabase.getTitle(movieId);

            System.out.println(String.format("%s %s %s", rating, movieYear, movieTitle));
        }
    }

    public void printAverageRatingsByGenre(int minimalRaters, String genre){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        GenreFilter gf = new GenreFilter(genre);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, gf);
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);

            System.out.println(String.format("%s %s", rating, movieTitle));
            System.out.println(String.format("    %s", movieGenres));

        }
    }

    public void printAverageRatingsByMinutes(int minimalRaters, int minMinutes, int maxMinutes){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        MinutesFilter mf = new MinutesFilter(minMinutes, maxMinutes);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, mf);
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int minutes = MovieDatabase.getMinutes(movieId);

            System.out.println(String.format("%s Time: %s %s", rating, minutes, movieTitle));
        }

        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");

    }

    public void printAverageRatingsByDirectors(int minimalRaters, String directors){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");

        DirectorsFilter df = new DirectorsFilter(directors);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, df);
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            System.out.println(String.format("%s %s", rating, movieTitle));
            System.out.println(String.format("    %s", movieDirectors));

        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int minimalRaters,  int year, String genre){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");



        YearAfterFilter yf = new YearAfterFilter(year);
        GenreFilter gf = new GenreFilter(genre);
        AllFilters af = new AllFilters();
        af.addFilter(yf);
        af.addFilter(gf);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, af);
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
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

    public void printAverageRatingsByDirectorsAndMinutes(int minimalRaters, int minMinutes, int maxMinutes, String directors){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println(tr.getRaterSize() + " rater(s) are loaded");
        System.out.println(MovieDatabase.size() + " movies(s) are in the database");



        MinutesFilter mf = new MinutesFilter(minMinutes, maxMinutes);
        DirectorsFilter df = new DirectorsFilter(directors);

        AllFilters af = new AllFilters();
        af.addFilter(mf);
        af.addFilter(df);

        ArrayList<Rating> averageRatings = tr.getAverageRatingsByFilter(minimalRaters, af);
        Collections.sort(averageRatings);

        for (Rating averageRating : averageRatings){
            double rating = averageRating.getValue();
            String movieId = averageRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            int movieYear = MovieDatabase.getYear(movieId);
            String movieGenres = MovieDatabase.getGenres(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);
            int minutes = MovieDatabase.getMinutes(movieId);


            System.out.println(String.format("%s Time: %s %s", rating, minutes, movieTitle));
            System.out.println(String.format("    %s", movieDirectors));

        }
        System.out.println("Found " + averageRatings.size() + " movie(s) based on filter");

    }


}
