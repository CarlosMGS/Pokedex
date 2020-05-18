package es.ucm.fdi.pokedex;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


/**
 * PokemonViewActivity shows some of the details and stats of a specified Pokemon when selected from
 * the Pokedex (PokedActivity) or the Pokemon browser (FinderActivity).
 *
 * @author Carlos Gil, Álvaro Pascual
 */
public class PokemonViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonInfo>{

    public static final String EXTRA_QUERY = "queryString";
    private ImageView pokemonImage;
    private TextView pokemonIndex, pokemonName, pokemonWeight, pokemonHeight, pokemonCaptured;
    private RecyclerView recyclerType;
    private DatabaseAdapter dba;
    private PokemonInfo info;

    public PokemonViewActivity() {}

    /**
     * THe method onCreate() is launched when the Intent is created. It sets the view, prepares the
     * different parts of the view that are going to get modified, establishes connection with the
     * database and obtain the relative info of a Pokemon.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);

        getSupportActionBar().hide();

        pokemonImage = findViewById(R.id.PokemonImage);
        pokemonIndex = findViewById(R.id.PokemonIndex);
        pokemonName = findViewById(R.id.PokemonName);
        pokemonWeight = findViewById(R.id.PokemonWeight);
        pokemonHeight = findViewById(R.id.PokemonHeight);
        pokemonCaptured = findViewById(R.id.PokemonCaptured);

        recyclerType = findViewById(R.id.recycler_type);
        recyclerType.setHasFixedSize(true);
        recyclerType.setLayoutManager(new LinearLayoutManager(this));

        dba = new DatabaseAdapter(this);

        info = new PokemonInfo(getIntent().getExtras().getString("name"),
                getIntent().getExtras().getString("index"),
                getIntent().getExtras().getStringArrayList("types"),
                getIntent().getExtras().getString("weight"),
                getIntent().getExtras().getString("height"),
                -1);


        setPokemonInfo();
    }

    /**
     * The method setPokemonInfo() sets the stats to a certain searched Pokemon in the view.
     */
    private void setPokemonInfo() {

        // image view
        String imageString = "img" + info.getIndex();
        Resources res = getResources();
        int imageInt = res.getIdentifier(imageString, "drawable", getPackageName());
        info.setImage(imageInt);
        pokemonImage.setImageResource(info.getImage());

        pokemonIndex.setText(info.getIndex());
        pokemonName.setText(info.getName());
        pokemonWeight.setText(info.getWeight());
        pokemonHeight.setText(info.getHeight());

        isCaptured();

        PokemonTypeAdapter pokemonTypeAdapter = new PokemonTypeAdapter(this, info.getTypes());
        recyclerType.setAdapter(pokemonTypeAdapter);
        recyclerType.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * The method isCaptured() checks if a Pokemon has been marked as 'captured' or not, and modify
     * its status.
     */
    public void isCaptured(){

        int position = Integer.parseInt(info.getIndex());
        String[] row = dba.getSinlgeEntry(position);

        if(Boolean.getBoolean(row[2])){
            pokemonCaptured.setText("Captured");
            //deshabilitar boton
            ((Button) findViewById(R.id.buttonCapture)).setEnabled(false);

        }else{
            pokemonCaptured.setText("Not captured");
            //habilitar boton
            ((Button) findViewById(R.id.buttonCapture)).setEnabled(true);

        }
    }

    /**
     * The method capturar() sets in the view the status of a Pokemon to 'captured'.
     * @param view
     */
    public void capturar(View view){
        int id = Integer.parseInt(info.getIndex());
        dba.modifySingleEntry(id);

        pokemonCaptured.setText("Captured");
        //deshabilitar boton
        ((Button) findViewById(R.id.buttonCapture)).setEnabled(false);
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
