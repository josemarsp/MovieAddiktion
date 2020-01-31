package br.com.josef.movieaddiktion.view.interfaces;

import br.com.josef.movieaddiktion.model.pojos.movieid.Filme;

public interface OnClickFavoritos {
        void onClickFavoritos(Filme filme);
        void removeClickFavoritos(Filme filme);
}
