package es.ucm.fdi.pokedex;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PokemonInfo>{

    ImageView pokemonImage;
    TextView pokemonName, pokemonWeight, pokemonHeight;
    RecyclerView recyclerType;


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
