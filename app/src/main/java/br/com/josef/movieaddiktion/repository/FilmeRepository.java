package br.com.josef.movieaddiktion.repository;

import android.content.Context;

import java.util.List;

import br.com.josef.movieaddiktion.model.data.DatabaseFilme;
import br.com.josef.movieaddiktion.model.data.DatabaseFilmeNowPlaying;
import br.com.josef.movieaddiktion.model.data.FilmeDao;
import br.com.josef.movieaddiktion.model.data.FilmeNowPlayingDao;
import br.com.josef.movieaddiktion.model.data.remote.FilmeRetrofitService;
import br.com.josef.movieaddiktion.model.pojos.movieid.Filme;
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlaying;
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlayingResult;
import br.com.josef.movieaddiktion.model.pojos.searchmovies.SearchResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;


public class FilmeRepository {

    public Observable<Filme> getFilmeId(int movie_id, String apiKey, String linguaPais) {
        return FilmeRetrofitService.getApiService().getFilm(movie_id, apiKey, linguaPais);
    }

    // Pega os dados do banco de dados local
    public Flowable<List<Filme>> getLocalResults(Context context) {
        DatabaseFilme room = DatabaseFilme.getDatabase(context);
        FilmeDao filmeDao = room.filmeDao();
        return filmeDao.getAll();
    }

    public Observable<FilmeNowPlayingResult> getFilmeNowPlaying(String apiKey, String linguaPais, int pagina) {
        return FilmeRetrofitService.getApiService().getAllFilmeNowPlaying(apiKey, linguaPais, pagina);
    }


    public Flowable<List<FilmeNowPlaying>> getLocalResultsNowPlaing(Context context){
        DatabaseFilmeNowPlaying room = DatabaseFilmeNowPlaying.getDatabase(context);
        FilmeNowPlayingDao filmeNowPlayingDao = room.filmeNowPlayingDao();
        return filmeNowPlayingDao.getAll();
    }

    public Observable<SearchResult> getSearchResult(String apiKey, String linguaPais, String queryText) {
        return FilmeRetrofitService.getApiService().getSearchResult(apiKey, linguaPais, queryText);

    }
}
