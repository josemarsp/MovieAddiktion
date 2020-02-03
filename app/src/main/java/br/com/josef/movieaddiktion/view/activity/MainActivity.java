package br.com.josef.movieaddiktion.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.josef.movieaddiktion.R;
import br.com.josef.movieaddiktion.view.fragment.HomeFragment;
import br.com.josef.movieaddiktion.view.fragment.ListaDeFavoritosFragment;
import br.com.josef.movieaddiktion.view.fragment.ContagemFragment;
import br.com.josef.movieaddiktion.view.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportActionBar();

        replaceFragment(new HomeFragment());


        navView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.navigation_home) {
                replaceFragment(new HomeFragment());

            } else if (id == R.id.navigation_minha_lista) {
                replaceFragment(new ListaDeFavoritosFragment());

            } else if (id == R.id.navigation_search) {
                replaceFragment(new SearchFragment());

            } else if (id == R.id.navigation_perfil) {
                replaceFragment(new ContagemFragment());
            }
            return true;

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sair) {

            Toast.makeText(getApplicationContext(), "App Encerrado! Até a próxima!!!", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerPrincipal, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}