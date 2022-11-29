package Actions.WorkWithCatalog;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.io.IOException;

public class GettingRecomendations {
    public GettingRecomendations(){}

    public MovieResultsPage getRecomendations(int id) throws IOException {
        Tmdb tmdb = new Tmdb("40f486fd0f4155c326c4ff8bd162d9e6");
        MoviesService moviesService = tmdb.moviesService();

        return moviesService.recommendations(id,1,"ru").execute().body();
    }
}
