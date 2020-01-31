package br.com.josef.movieaddiktion.model.data.remote;

import br.com.josef.movieaddiktion.model.pojos.movieid.Filme;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
@Query para campos chamadas nominais
@Path para campos diretos. Ex: @Path("language") String lingua
 */
public interface FilmeIdAPI {


    @GET("movie/{movie_id}")
    Observable<Filme> getFilm(@Path("movie_id") int movieId,
                              @Query("api_key") String apiKey,
                              @Query("language") String linguaPais);





 }
