package es.ucm.fdi.pokedex;

import android.content.res.Resources;
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
    private TextView pokemonIndex, pokemonName, pokemonWeight, pokemonHeight;
    private RecyclerView recyclerType;

    public PokemonViewActivity() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);

        pokemonImage = findViewById(R.id.PokemonImage);
        pokemonIndex = findViewById(R.id.PokemonIndex);
        pokemonName = findViewById(R.id.PokemonName);
        pokemonWeight = findViewById(R.id.PokemonWeight);
        pokemonHeight = findViewById(R.id.PokemonHeight);

        recyclerType = findViewById(R.id.recycler_type);
        recyclerType.setHasFixedSize(true);
        recyclerType.setLayoutManager(new LinearLayoutManager(this));

        PokemonInfo info = new PokemonInfo(getIntent().getExtras().getString("name"),
                getIntent().getExtras().getString("index"),
                getIntent().getExtras().getStringArrayList("types"),
                getIntent().getExtras().getString("weight"),
                getIntent().getExtras().getString("height"),
                -1);

        setPokemonInfo(info);
    }

    private void setPokemonInfo(PokemonInfo info) {

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

        /* lista auxiliar que contiene el único pokemon que buscamos */
        List<PokemonInfo> auxList = new ArrayList<>();
        auxList.add(info);

        PokemonTypeAdapter pokemonTypeAdapter = new PokemonTypeAdapter(this, auxList);
        recyclerType.setAdapter(pokemonTypeAdapter);
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
