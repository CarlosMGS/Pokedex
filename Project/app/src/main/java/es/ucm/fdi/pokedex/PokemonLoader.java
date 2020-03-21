package es.ucm.fdi.pokedex;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class PokemonLoader extends AsyncTaskLoader<PokemonInfo> {


    public PokemonLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public PokemonInfo loadInBackground() {

        PokemonInfo pokemon = PokemonInfo.fromJsonResponse("");
        return null;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }


}
