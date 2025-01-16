/**
 * Write a description of FirstRatings here.
 * 
 * @author Xuze
 * @version 0.1.0 in 2025.1.12
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.IOException;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename) 
    {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        // Process every record from the CSV file and return an ArrayList of type Movie with all of the movie data
        // filename = csvPath + "ratings_short.csv"; // Path to the CSV file
        CSVFormat format = CSVFormat.DEFAULT
                .withHeader()
                .withIgnoreSurroundingSpaces()    // Ignore leading/trailing spaces
                .withSkipHeaderRecord();          // Skip the first row if it's a header

    
        try (FileReader reader = new FileReader(filename); CSVParser parser = new CSVParser(reader, format)) 
        {
            // Loop through the records
            for (CSVRecord record : parser) 
            {
                // Access by column index
                String id = record.get(0);  // First column (id)
                String title = record.get(1);  // Second column (title)
                String year = record.get(2);  // Third column (year)
                String country = record.get(3);  // Fourth column (country)
                String genres = record.size() > 4 ? record.get(4) : "Unknown Genre";  // Fifth column (genre), default if not present
                String director = record.size() > 5 ? record.get(5) : "Unknown Director";  // Sixth column (director), default if not present
                String minutesString = record.size() > 6 ? record.get(6) : "0";  // Seventh column (minutes), default if not present
                int minutes = Integer.parseInt(minutesString);  // Convert to integer
                String poster = record.size() > 7 ? record.get(7) : "No Poster";  // Eighth column (poster), default if not present
    
                Movie movie = new Movie(id, title, year, genres, director, country, poster, minutes);
                movies.add(movie);
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    
        return movies;
    }

    
    public void testLoadMovies() {
        // String filename = "data/ratedmovies_short.csv";
        String filename = "data/ratedmoviesfull.csv";
        ArrayList<Movie> movies = loadMovies(filename);
        System.out.println("Number of movies: " + movies.size());
        
        /*
        for (Movie movie : movies) {
            String movieName = movie.getTitle();
            System.out.println(movieName);
        }
        */
        
        // Comedy genre movie count
        int comedyCount = 0;
        for (Movie movie : movies) {

            String movieGenre = movie.getGenres();
            if (movieGenre.contains("Comedy"))
            {
                comedyCount += 1;
            }
        }
        System.out.println("Movies include the Comedy Genre: " + comedyCount);

        // Greater than 150 mins movie count
        int longMovieCount = 0;
        for (Movie movie : movies) {

            int movieMinutes = movie.getMinutes();
            if (movieMinutes > 150)
            {
                longMovieCount += 1;
            }
        }
        System.out.println("Movies longer than 150 minutes: " + longMovieCount);
       
        // Maximum number of movies by any director
        Map<String, Integer> directorMoviesCountMap = new HashMap<>();
        for (Movie movie : movies) {
            String directors = movie.getDirector();
            for (String director : directors.split(","))
            {
                director = director.trim();
                directorMoviesCountMap.put(director, directorMoviesCountMap.getOrDefault(director,0)+1);
                
            }
        }
        
        int MaxMoviesCount = 0;
        for (int MoviesCount : directorMoviesCountMap.values())
        {
            if (MoviesCount > MaxMoviesCount){
                MaxMoviesCount = MoviesCount;
            }
        }
        System.out.println("Maximum number of movies by any director: " + MaxMoviesCount);
        
        // who the directors are that directed the maximum number of movies
        System.out.println("Directors who directed the maximun number of movies are: ");
        for (Map.Entry<String, Integer> entry : directorMoviesCountMap.entrySet()){

            if (entry.getValue() == MaxMoviesCount){
                System.out.println(entry.getKey());
            }
        }
    }
    
    public ArrayList<EfficientRater> loadRaters(String filename){
        ArrayList<EfficientRater> raters = new ArrayList<>();
        
        CSVFormat format = CSVFormat.DEFAULT
                .withHeader("rater_id", "movie_id", "rating", "time")
                .withIgnoreSurroundingSpaces()
                .withSkipHeaderRecord();
        
        try (FileReader reader = new FileReader(filename);
             CSVParser parser = new CSVParser(reader, format)) {

            // Loop through the records
            for (CSVRecord record : parser) {
                // Access by column index
                String raterId = record.get("rater_id");
                EfficientRater rater = new EfficientRater(raterId);

                String movieId = record.get("movie_id");
                String ratingString = record.get("rating");
                double rating = Double.parseDouble(ratingString);
                // String time = record.get("time");
                // Rating rating = new Rating (movieId, ratingDouble);
                
                boolean raterExist = false;
                for (EfficientRater r : raters){
                    if (r.getID().equals(raterId))
                    {
                        r.addRating(movieId, rating);
                        raterExist = true;
                        break;
                    }
                }
    
                if (!raterExist) {
                    raters.add(rater);
                    rater.addRating(movieId, rating);
                }
 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return raters;
    }
    
        
    public void testLoadRaters() {
        // String filename = "data/ratings_short.csv";
        String filename = "data/ratings.csv";
        ArrayList<EfficientRater> raters = loadRaters(filename);
        System.out.println("Number of raters: " + raters.size());
        
        /*
        for (Rater rater : raters){
            System.out.println("Rater[" + rater.getID() + "] has " + rater.numRatings() + " ratings:");
            
            ArrayList<String> items = rater.getItemsRated();
            for (String item : items){
                double rating = rater.getRating(item);
                System.out.println("Movie[" + item + "] rated " + rating);
            }
            System.out.println();
        }
        */
       
        // find the number of ratings for a particular rater
        String raterId = "193";
        for (EfficientRater r : raters){
            if (r.getID().equals(raterId)){
                System.out.println("Rater with ID " + raterId + " has " + r.numRatings() + " ratings");
            }
        }
        
        // find the maximum number of ratings by any rater
        int maxRatingsCount = 0;
        for (EfficientRater rater : raters){
            if (rater.numRatings() > maxRatingsCount){
                maxRatingsCount = rater.numRatings();
            }
        }
        System.out.println("Maximum number of ratings by any rater is " + maxRatingsCount);
        
        int maxRatingsRatersCount = 0;
        ArrayList<EfficientRater> maxRatingsRaters = new ArrayList<>();
        for (EfficientRater rater : raters){
            if (rater.numRatings() == maxRatingsCount){
                maxRatingsRatersCount += 1;
                maxRatingsRaters.add(rater);
            }
        }
        System.out.print(maxRatingsRatersCount + " rater(s) have the maximum number of ratings, and they are: ");
        for (EfficientRater rater : maxRatingsRaters)
        {
            System.out.print("Rater[" + rater.getID()+"] ");
        }
        System.out.println();
        

        // find the number of ratings a particular movie has
        int movieRatingsCount = 0;
        String movieId = "1798709";
        for (Rater r : raters){
            ArrayList<String> moviesRated = r.getItemsRated();
            if (moviesRated.contains(movieId)){
                movieRatingsCount += 1;
            }
        }
        System.out.println("Movie[" + movieId +"] has " + movieRatingsCount + " ratings" );
        
        // determine how many different movies have been rated by all these raters  
        int uniqueMoviesCount = 0;
        Set<String> uniqueMovies = new HashSet<String>();
        for (EfficientRater r : raters){
            ArrayList<String> moviesRated = r.getItemsRated();
            for (String movie :moviesRated){
                uniqueMovies.add(movie);
            }
        }
        System.out.println(uniqueMovies.size() + " movie(s) have been rated" );
    }
    
    
    public static void main(String[] args) {
        FirstRatings  fr  = new FirstRatings();
        fr.testLoadMovies();
        fr.testLoadRaters();
    }
}

