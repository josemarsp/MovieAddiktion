package br.com.josef.movieaddiktion.repository;


import android.content.Context;

import java.util.List;

import br.com.josef.movieaddiktion.model.data.DatabaseFilmeNowPlaying;
import br.com.josef.movieaddiktion.model.data.FilmeNowPlayingDao;
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlaying;
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlayingResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import static br.com.josef.movieaddiktion.model.data.remote.FilmeNowPlayingRetrofitService.getApiService;


public class FilmeNowPlayingRepository {

    public Observable<FilmeNowPlayingResult> getFilmeNowPlaying(String apiKey, String linguaPais, int pagina) {
        return getApiService().getAllFilmeNowPlaying(apiKey, linguaPais, pagina);
    }


    public Flowable<List<FilmeNowPlaying>> getLocalResults(Context context){
        DatabaseFilmeNowPlaying room = DatabaseFilmeNowPlaying.getDatabase(context);
        FilmeNowPlayingDao filmeNowPlayingDao = room.filmeNowPlayingDao();
        return filmeNowPlayingDao.getAll();
    }



}
