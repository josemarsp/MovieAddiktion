package br.com.josef.movieaddiktion.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import br.com.josef.movieaddiktion.R;
import br.com.josef.movieaddiktion.viewmodel.FavoritoViewModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContagemFragment extends Fragment {


    private TextView contadorFavoritos;
    private FavoritoViewModel viewModel;


    public ContagemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contagem, container, false);
        initviews(view);
        mostrandoContagemLista();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                HomeFragment frag = new HomeFragment();

                tr.replace(R.id.containerPrincipal, frag);
                tr.commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        return view;
    }

    private void mostrandoContagemLista() {

        viewModel.contaFilme();
        viewModel.getFilmeCont().observe(this, integer -> {
                    contadorFavoritos.setText(integer.toString());
                }
        );
    }

    private void initviews(View view) {
        contadorFavoritos = view.findViewById(R.id.deloreanText);
        viewModel = ViewModelProviders.of(this).get(FavoritoViewModel.class);

    }


}