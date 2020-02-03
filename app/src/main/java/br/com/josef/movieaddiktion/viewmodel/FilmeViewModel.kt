package br.com.josef.movieaddiktion.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.josef.movieaddiktion.model.data.DatabaseFilmeNowPlaying
import br.com.josef.movieaddiktion.model.pojos.movieid.Filme
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlaying
import br.com.josef.movieaddiktion.model.pojos.nowplaying.FilmeNowPlayingResult
import br.com.josef.movieaddiktion.repository.FilmeIdRepository
import br.com.josef.movieaddiktion.repository.FilmeNowPlayingRepository
import br.com.josef.movieaddiktion.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FilmeViewModel(application: Application) : AndroidViewModel(application) {
    private val listaFilme =
        MutableLiveData<List<FilmeNowPlaying>>()
    private val filme = MutableLiveData<Filme>()
    private val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val repository = FilmeNowPlayingRepository()
    private val idRepository = FilmeIdRepository()
    fun getListaFilme(): LiveData<List<FilmeNowPlaying>> {
        return listaFilme
    }

    fun getFilme(): LiveData<Filme> {
        return filme
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun getFilmesEmCartaz(
        apiKey: String?,
        linguaPais: String?,
        pagina: Int
    ) {
        if (Utils.isNetworkConnected(getApplication())) {
            getAllFilmesNowPlaying(apiKey, linguaPais, pagina)
        } else {
            fromLocal
            Log.i("LOG", "DESCONECTADO!")
        }
    }

    fun getAllFilmesNowPlaying(
        apiKey: String?,
        linguaPais: String?,
        pagina: Int
    ) {
        disposable.add(
            repository.getFilmeNowPlaying(apiKey, linguaPais, pagina)
                .subscribeOn(Schedulers.io())
                .map { filmeNowPlayingResult: FilmeNowPlayingResult ->
                    saveItems(filmeNowPlayingResult)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable1: Disposable? ->
                    loading.setValue(
                        true
                    )
                }
                .doOnTerminate { loading.setValue(false) }
                .subscribe(
                    { filmeResult: FilmeNowPlayingResult ->
                        listaFilme.setValue(
                            filmeResult.results
                        )
                    }
                ) { throwable: Throwable ->
                    Log.i(
                        "LOG",
                        "get From Network erro  " + throwable.message
                    )
                }
        )
    }

    private fun saveItems(filmeNowPlayingResult: FilmeNowPlayingResult): FilmeNowPlayingResult {
        val dao = DatabaseFilmeNowPlaying
            .getDatabase(
                getApplication<Application>()
                    .applicationContext
            )
            .filmeNowPlayingDao()
        dao.deleteAll()
        dao.insert(filmeNowPlayingResult.results)
        return filmeNowPlayingResult
    }

    fun getFilmeId(movie_id: Int, apiKey: String?, linguaPais: String?) {
        disposable.add(
            idRepository.getFilmeId(movie_id, apiKey, linguaPais)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable1: Disposable? ->
                    loading.setValue(
                        true
                    )
                }
                .doOnTerminate { loading.setValue(false) }
                .subscribe(
                    { filme1: Filme ->
                        filme.setValue(
                            filme1
                        )
                    }
                ) { throwable: Throwable ->
                    Log.i(
                        "LOG",
                        "erro " + throwable.message
                    )
                }
        )
    }

    private val fromLocal: Unit
        private get() {
            disposable.add(
                repository.getLocalResults(getApplication<Application>().applicationContext)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { results: List<FilmeNowPlaying> ->
                            listaFilme.setValue(results)
                        }
                    ) { throwable: Throwable ->
                        Log.i(
                            "LOG",
                            "erro buscando OffLine " + throwable.message
                        )
                    }
            )
        }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}