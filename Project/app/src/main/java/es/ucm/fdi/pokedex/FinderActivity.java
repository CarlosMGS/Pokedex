package es.ucm.fdi.pokedex;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.pokedex.R;

public class FinderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonInfo>{

    private static final int POK_LOADER_ID = 100;
    private static final String EXTRA_QUERY = "queryString";
    private RecyclerView pokemonRView;
    private PokemonResultsAdapter pokera;
    private String pokeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);

        //hide the action bar to show a full screen app
        getSupportActionBar().hide();

        /* Loader initialization*/
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(POK_LOADER_ID) != null){
            loaderManager.initLoader(POK_LOADER_ID, null, this);
        }

        PokemonInfo info = new PokemonInfo();

        pokemonRView = findViewById(R.id.pokemonView);
        pokera= new PokemonResultsAdapter(this, info);

        pokemonRView.setAdapter(pokera);
        // Give the RecyclerView a default layout manager.
        pokemonRView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void searchPokemon(View view){

        /* pokemon text*/
        pokeText= ((EditText)findViewById(R.id.textPokemon)).getText().toString();

        String queryString = pokeText;



        ((TextView) findViewById(R.id.textState)).setText("Loading...");


        Bundle queryBundle = new Bundle();
        queryBundle.putString(this.EXTRA_QUERY, queryString);
        LoaderManager.getInstance(this).restartLoader(POK_LOADER_ID, queryBundle, this);

    }

    public void updateBooksResultList(PokemonInfo info){

        pokera.setPokemonData(info);
        pokera.notifyDataSetChanged();

    }


    @NonNull
    @Override
    public Loader<PokemonInfo> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<PokemonInfo> loader, PokemonInfo data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonInfo> loader) {

    }
}
