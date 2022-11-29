package Actions;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class GettingPremiers {
    public GettingPremiers(){}

    public  Response<MovieResultsPage> getPremiers(int pageCount) throws IOException {
        Tmdb tmdb = new Tmdb("40f486fd0f4155c326c4ff8bd162d9e6");
        MoviesService moviesService = tmdb.moviesService();
        Response<MovieResultsPage> response = moviesService.nowPlaying(pageCount,"ru","RU").execute();
       return response;
    }
}
