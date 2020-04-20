package es.ucm.fdi.pokedex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PokedActivity extends AppCompatActivity {

    private PokemonResultsAdapter pokeResultsAdapter;
    private List<PokemonInfo> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        getSupportActionBar().hide();

        pokemons = new ArrayList<PokemonInfo>();
        //leemos los pokemon del almacenamiento local


        //cargamos los pokemon en el results adapter para modificar la vista
        pokeResultsAdapter = new PokemonResultsAdapter(this, pokemons);
    }
}
