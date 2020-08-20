package br.com.josef.movieaddiktion.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.josef.movieaddiktion.R;
import br.com.josef.movieaddiktion.model.pojos.searchmovies.Search;
import br.com.josef.movieaddiktion.viewmodel.SearchViewModel;
import br.com.josef.movieaddiktion.view.adapter.SearchAdapter;
import br.com.josef.movieaddiktion.view.interfaces.OnClickSearch;

import static br.com.josef.movieaddiktion.view.fragment.HomeFragment.MOVIE_ID_KEY;

public class SearchFragment extends Fragment implements OnClickSearch {

    private RecyclerView recyclerView;
    private List<Search> searchList = new ArrayList<>();
    private SearchViewModel viewModel;
    private ProgressBar progressBar;
    private SearchView searchView;
    private SearchAdapter adapter;
    private String nomeFilme;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initViews(view);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        viewModel.getAllSerchResult(nomeFilme);
        viewModel.getListaSearch().observe(this, searches -> {
            adapter.atualizaLista(searches);
        });

        viewModel.getLoading().observe(this, (Boolean loading) -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
        searchView.setOnClickListener(v -> searchView.setIconified(false));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nomeFilme = query;
                adapter.clear();
                viewModel.getAllSerchResult(nomeFilme);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 15) {
                    nomeFilme = newText;
                    adapter.clear();
                    viewModel.getAllSerchResult(nomeFilme);
                }
                return false;
            }
        });


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


    public void initViews(View view) {
        adapter = new SearchAdapter(this, searchList);
        recyclerView = view.findViewById(R.id.recyclerview_search);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        progressBar = view.findViewById(R.id.progressBarSearch);
        searchView = view.findViewById(R.id.searchView);


    }

    @Override
    public void onClick(Search search) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID_KEY, String.valueOf(search.getId()));
        Fragment resultadoFilmeFragment = new ResultadoFilmeFragment();
        resultadoFilmeFragment.setArguments(bundle);
        replaceFragment(resultadoFilmeFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerPrincipal, fragment);
        transaction.commit();
    }


}
