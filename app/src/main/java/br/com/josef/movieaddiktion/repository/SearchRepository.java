package br.com.josef.movieaddiktion.repository;

import br.com.josef.movieaddiktion.model.data.remote.SearchRetrofitService;
import br.com.josef.movieaddiktion.model.pojos.searchmovies.SearchResult;
import io.reactivex.Observable;

public class SearchRepository {

    public Observable<SearchResult> getSearchResult(String apiKey, String linguaPais, String queryText) {
        return SearchRetrofitService.getSearch().getSearchResult(apiKey, linguaPais, queryText);

    }
}
