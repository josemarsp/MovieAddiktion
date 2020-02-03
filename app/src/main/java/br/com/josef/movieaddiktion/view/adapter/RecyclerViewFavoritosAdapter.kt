package br.com.josef.movieaddiktion.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.josef.movieaddiktion.R
import br.com.josef.movieaddiktion.model.pojos.movieid.Filme
import br.com.josef.movieaddiktion.view.interfaces.OnClickFavoritos
import com.squareup.picasso.Picasso

class RecyclerViewFavoritosAdapter(
    private val listener: OnClickFavoritos,
    private var listaFavoritos: MutableList<Filme>
) : RecyclerView.Adapter<RecyclerViewFavoritosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_do_item_da_lista_favoritos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val favoritos = listaFavoritos[position]
        holder.onBind(favoritos)
        holder.itemView.setOnClickListener { view: View? ->
            listener.onClickFavoritos(
                favoritos
            )
        }
        holder.iconeLixeira.setOnClickListener { view: View? ->
            listener.removeClickFavoritos(
                favoritos
            )
        }
    }

    override fun getItemCount(): Int {
        return listaFavoritos.size
    }

    fun atualizaLista(novaLista: MutableList<Filme>) {
        if (listaFavoritos.isEmpty()) {
            listaFavoritos = novaLista
        } else {
            listaFavoritos.addAll(novaLista)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var capaDoFilme: ImageView
        var notaDoFilme: TextView
        var nomeDoFilme: TextView
        var descricaoDoFilme: TextView
        var iconeLixeira: ImageView
        fun onBind(filme: Filme) {
            notaDoFilme.text = filme.voteAverage.toString()
            nomeDoFilme.text = filme.title
            descricaoDoFilme.text = filme.overview
            Picasso.get().load("https://image.tmdb.org/t/p/w200/" + filme.posterPath)
                .into(capaDoFilme)
        }

        init {
            capaDoFilme = itemView.findViewById(R.id.img_capa_favoritos_id)
            nomeDoFilme = itemView.findViewById(R.id.textView_titulo_favoritos_id)
            notaDoFilme = itemView.findViewById(R.id.textView_rating_nota_favoritos_id)
            descricaoDoFilme = itemView.findViewById(R.id.textView_descricao_favoritos_id)
            iconeLixeira = itemView.findViewById(R.id.iconeLixeira)
        }
    }

}