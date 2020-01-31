package br.com.josef.movieaddiktion.model.data.remote;

import br.com.josef.movieaddiktion.model.pojos.searchmovies.SearchResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchAPI {


    @GET("search/movie")
    Observable<SearchResult> getSearchResult(@Query("api_key") String apiKEY,
                                             @Query("language") String linguaPais,
                                             @Query("query") String queryText);
}
