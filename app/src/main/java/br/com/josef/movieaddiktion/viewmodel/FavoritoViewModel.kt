package br.com.josef.movieaddiktion.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.josef.movieaddiktion.model.data.DatabaseFilme
import br.com.josef.movieaddiktion.model.pojos.movieid.Filme
import br.com.josef.movieaddiktion.repository.FilmeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritoViewModel(application: Application) : AndroidViewModel(application) {
    private val listaFilme =
        MutableLiveData<List<Filme>>()
    private val filme = MutableLiveData<Filme?>()
    private val filmeCont = MutableLiveData<Int>()
    private val filmeBoolean = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val repository =
        FilmeRepository()
    private val filmeDao =
        DatabaseFilme.getDatabase(getApplication()).filmeDao()

    fun getListaFilme(): LiveData<List<Filme>> {
        return listaFilme
    }

    fun getFilme(): LiveData<Filme?> {
        return filme
    }

    fun getFilmeCont(): LiveData<Int> {
        return filmeCont
    }

    fun getFilmeBoolean(): LiveData<Boolean> {
        return filmeBoolean
    }

    fun insereFilme(filmeobj: Filme?) {
        Thread(Runnable {
            if (filmeobj != null) {
                filmeDao.insert(filmeobj)
            }
        }).start()
        filme.value = filmeobj
        filmeBoolean.value = true
    }

    fun deletaFilme(filme: Filme?) {
        Thread(Runnable {
            if (filme != null) {
                filmeDao.deleteFavorite(filme)
            }
        }).start()
        this.filme.value = filme
        filmeBoolean.value = false
    }

    fun buscaFavoritos() {
        disposable.add(
            filmeDao.all
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { filmes: List<Filme> ->
                        listaFilme.setValue(
                            filmes
                        )
                    }
                ) { throwable: Throwable ->
                    Log.i(
                        "LOG",
                        "Falha na lista de favoritos" + throwable.message
                    )
                }
        )
    }

    fun contaFilme() {
        disposable.add(
            filmeDao.contFilme
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { integer: Int ->
                        filmeCont.setValue(
                            integer
                        )
                    }
                ) { throwable: Throwable ->
                    Log.i(
                        "LOG",
                        "Falha Contagem Filme" + throwable.message
                    )
                }
        )
    }

    fun checaFilme(idFilme: Long?) {
        disposable.add(
            filmeDao.getChecaFilme(idFilme!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { filmeBoo: Boolean ->
                        filmeBoolean.setValue(
                            filmeBoo
                        )
                    }
                ) { throwable: Throwable ->
                    Log.i(
                        "LOG",
                        "Falha Checa Filme" + throwable.message
                    )
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}