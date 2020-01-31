package br.com.josef.movieaddiktion.model.data.remote;


import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlayingResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FilmeNowPlayingAPI {

    @GET("movie/now_playing")
    Observable<FilmeNowPlayingResult> getAllFilmeNowPlaying(@Query("api_key") String apiKEY,
                                                            @Query("language") String linguaPais,
                                                            @Query("page") int pagina);


}
