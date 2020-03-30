package es.ucm.fdi.pokedex;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class PokemonLoader extends AsyncTaskLoader<PokemonInfo> {


    private PokeApiConn pokemonService;
    private String queryString;

    public PokemonLoader(@NonNull Context context, String queryString) {
        super(context);

        this.queryString = queryString;
        this.pokemonService = new PokeApiConn();
    }

    @Nullable
    @Override
    public PokemonInfo loadInBackground() {

        PokemonInfo pokemon;
        pokemon = LoadData(queryString);

        return pokemon;
    }

    @Override
    protected void onStartLoading(){

        forceLoad();

    }

    protected PokemonInfo LoadData(String queryString){

        PokemonInfo info;
        info = pokemonService.pokemonRetriever(queryString);
        if(info != null){
            return info;

        } else{

            return null;
        }
    }


}
