import java.util.ArrayList;
import java.util.Collections;

public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate() {
        int outputLimit = 10;

        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(new YearAfterFilter(1997));
        Collections.shuffle(filteredMovieIds);

        return new ArrayList<>(filteredMovieIds.subList(0, Math.min(outputLimit, filteredMovieIds.size())));
    }

    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> movieSimilarRatings = fr.getSimilarRatings(webRaterID, 100, 2);

        if (movieSimilarRatings.isEmpty()) {
            System.out.println("Sorry. I found no movie for your taste.");
            return;
        }

        int outputLimit = 20;
        ArrayList<Rating> shortenedMovieSimilarRatings = new ArrayList<>(
                movieSimilarRatings.subList(0, Math.min(outputLimit, movieSimilarRatings.size()))
        );

        StringBuilder html = new StringBuilder();
        html.append("<table border='1'>");
        html.append("<tr><th>Movies</th><th>Director</th><th>Poster</th></tr>");

        for (Rating movieSimilarRating : shortenedMovieSimilarRatings) {
            // double rating = movieSimilarRating.getValue();
            String movieId = movieSimilarRating.getItem();
            String movieTitle = MovieDatabase.getTitle(movieId);
            String moviePoster = MovieDatabase.getPoster(movieId);
            String movieDirectors = MovieDatabase.getDirector(movieId);

            html.append("<tr>")
                .append("<td>").append(movieTitle).append("</td>")
                .append("<td>").append(movieDirectors).append("</td>")
                .append("<td><img src='").append(moviePoster).append("' alt='").append(movieTitle).append("' width='100'></td>")
                .append("</tr>");
        }
        html.append("</table>");

        System.out.println(html);
    }

}
