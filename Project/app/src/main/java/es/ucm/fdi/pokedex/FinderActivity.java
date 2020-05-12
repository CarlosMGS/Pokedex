package es.ucm.fdi.pokedex;

import android.content.Intent;
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

/**
 * FinderActivity is one of the possible functions to be selected at the MainActivity. It allows
 * the user to search a Pokemon by typing it's name, and then giving the option to click and show
 * its details.
 * @author Carlos Gil, Álvaro Pascual
 */
public class FinderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonInfo>{

    public static final int POK_LOADER_ID = 100;
    public static final String EXTRA_QUERY = "queryString";
    private RecyclerView pokemonRView;
    private PokemonResultsAdapter pokeResultsAdapter;
    private String pokeText;

    private TextView name;
    private TextView index;
    private TextView types;

    private CardView card;

    private PokemonInfo pokemonInfo;

    /**
     * The method onCreat() is launched when the Intent is created. It sets the view and launches
     * the Loader.
     * @param savedInstanceState
     */
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

        pokemonInfo = new PokemonInfo();

        // esto crea una lista que solo tendrá un pokemon
        List<PokemonInfo> list = new ArrayList<>();
        list.add(pokemonInfo);

        //pokemonRView = findViewById(R.id.pokemonView);
        //pokeResultsAdapter= new PokemonResultsAdapter(this, list); // en lugar de pasar un solo poke, se pasa una lista de un solo poke

        //pokemonRView.setAdapter(pokeResultsAdapter);
        // Give the RecyclerView a default layout manager.
        //pokemonRView.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * The method SearchPokemon() collects the Pokemon's name the user types and creates the query
     * with the info that is going to be searched.
     * @param view
     */
    public void searchPokemon(View view){

        /* pokemon text*/
        pokeText= ((EditText)findViewById(R.id.textPokemon)).getText().toString();

        String queryString = pokeText;

        ((TextView) findViewById(R.id.textState)).setText("Loading...");

        Bundle queryBundle = new Bundle();
        queryBundle.putString(this.EXTRA_QUERY, queryString);
        LoaderManager.getInstance(this).restartLoader(POK_LOADER_ID, queryBundle, this);
    }

    /**
     * The method updatePokemonResults() updates the carview that contains the info of the Pokemon
     * the user typed.
     * @param info
     */
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

        if (info.getTypes().size() == 1){
            types.setText(info.getTypes().get(0));
        }
        else{
            String typesList = info.getTypes().get(0) + " - " + info.getTypes().get(1);
            types.setText(typesList);
        }
    }

    /**
     * The method onCreateLoader() is launched when the Loader is called. It sets a text to show the
     * user that the browse is taking place, and passes the query to the PokemonLoader class.
     * @param id
     * @param args
     * @return a Loader with the Pokemon`s info that was found.
     */
    @NonNull
    @Override
    public Loader<PokemonInfo> onCreateLoader(int id, @Nullable Bundle args) {

        ((TextView)findViewById(R.id.textState)).setText("Loading...");

        return new PokemonLoader(this, args.getString(EXTRA_QUERY));

    }

    /**
     * The method onLoadFinished() updates the previous text to show what is the result of the
     * search after all the data is loaded.
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<PokemonInfo> loader, PokemonInfo data) {

        if (data.getIndex()!= null) {

            //actualiza los datos de la recyclerview
            this.updatePokemonResults(data);
            pokemonInfo = data;
            ((TextView)findViewById(R.id.textState)).setText("Results");
        }
        else {

            //no se encuentran casos
            /*updatePokemonResults(new PokemonInfo());*/
            ((TextView)findViewById(R.id.textState)).setText("No Results Found");
        }

    }

    /**
     * The method onLoadReset() allows to reset the Loader and notifies that changes have been made.
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<PokemonInfo> loader) {

        loader.reset();
        pokeResultsAdapter.notifyDataSetChanged();
    }

    /**
     * The method loadPokemonView() creates a new PokemonViewActivity Intent and passes all the info
     * of the Pokemon the user typed.
     * @param view
     */
    public void loadPokemonView(View view){

        Intent poked = new Intent(this, PokemonViewActivity.class);
        poked.putExtra("index", pokemonInfo.getIndex());
        poked.putExtra("name", pokemonInfo.getName());
        poked.putExtra("weight", pokemonInfo.getWeight());
        poked.putExtra("height", pokemonInfo.getHeight());
        poked.putStringArrayListExtra("types", (ArrayList<String>) pokemonInfo.getTypes());
        startActivity(poked);
    }
}
