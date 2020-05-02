package es.ucm.fdi.pokedex;

import android.os.Bundle;
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

public class PokemonViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonInfo>{

    public static final String EXTRA_QUERY = "queryString";
    private ImageView pokemonImage;
    private TextView pokemonName, pokemonWeight, pokemonHeight;
    private RecyclerView recyclerType;

    public PokemonViewActivity() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);


        PokemonInfo info = new PokemonInfo();

        pokemonImage = (ImageView) findViewById(R.id.PokemonImage);
        pokemonName = (TextView) findViewById(R.id.PokemonName);
        pokemonWeight = (TextView) findViewById(R.id.PokemonWeight);
        pokemonHeight = (TextView) findViewById(R.id.PokemonHeight);

        recyclerType = (RecyclerView) findViewById(R.id.recycler_type);
        recyclerType.setHasFixedSize(true);
        recyclerType.setLayoutManager(new LinearLayoutManager(this));

        setPokemonInfo(info);
    }

    private void setPokemonInfo(PokemonInfo info) {

        // image view

        pokemonName.setText(info.getName());
        pokemonWeight.setText("Weight " + info.getWeight());
        pokemonHeight.setText("Height " + info.getHeight());

        /* lista auxiliar que contiene el único pokemon que buscamos */
        List<PokemonInfo> auxList = new ArrayList<>();
        auxList.add(info);

        PokemonTypeAdapter pokemonTypeAdapter = new PokemonTypeAdapter(this, auxList);
        recyclerType.setAdapter(pokemonTypeAdapter);
    }

    @NonNull
    @Override
    public Loader<PokemonInfo> onCreateLoader(int id, @Nullable Bundle args) {

        return new PokemonLoader(this, args.getString(EXTRA_QUERY));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<PokemonInfo> loader, PokemonInfo data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<PokemonInfo> loader) {

    }
}
