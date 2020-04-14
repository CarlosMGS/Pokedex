package es.ucm.fdi.pokedex;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    private PokemonResultsAdapter pokeResultsAdapter;
    private String pokeText;

    private TextView name;
    private TextView index;
    private TextView types;

    private CardView card;

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
        pokeResultsAdapter= new PokemonResultsAdapter(this, info);

        pokemonRView.setAdapter(pokeResultsAdapter);
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

    public void updatePokemonResults(PokemonInfo info){

        /*
        pokeResultsAdapter.setPokemonData(info);
        pokeResultsAdapter.notifyDataSetChanged();
        */

        name = (TextView) findViewById(R.id.pokename);
        index = (TextView) findViewById(R.id.pokeid);
        types = (TextView) findViewById(R.id.poketypes);
        card = (CardView) findViewById(R.id.cardView);

        card.setCardBackgroundColor(Color.rgb(255,255,255));

        name.setText(info.getName());
        index.setText("Nº "+info.getIndex());
        types.setText(info.getTypes());

    }


    @NonNull
    @Override
    public Loader<PokemonInfo> onCreateLoader(int id, @Nullable Bundle args) {

        ((TextView)findViewById(R.id.textState)).setText("Loading...");

        return new PokemonLoader(this, args.getString(EXTRA_QUERY));

    }

    @Override
    public void onLoadFinished(@NonNull Loader<PokemonInfo> loader, PokemonInfo data) {



        if (data!= null) {

            //actualiza los datos de la recyclerview
            this.updatePokemonResults(data);
            ((TextView)findViewById(R.id.textState)).setText("Results");
        }
        else {

            //no se encuentran casos
            updatePokemonResults(new PokemonInfo());
            ((TextView)findViewById(R.id.textState)).setText("No Results Found");
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonInfo> loader) {

        loader.reset();
        pokeResultsAdapter.notifyDataSetChanged();
    }
}
