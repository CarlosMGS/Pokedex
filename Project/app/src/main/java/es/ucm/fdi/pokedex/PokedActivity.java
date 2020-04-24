package es.ucm.fdi.pokedex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokedActivity extends AppCompatActivity {

    private PokemonResultsAdapter pokeResultsAdapter;
    private final int MAX_POKEMON = 807;
    private List<PokemonInfo> pokemons;
    private DatabaseAdapter dba;
    private RecyclerView pokemonRView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        getSupportActionBar().hide();

        pokemons = new ArrayList<PokemonInfo>();
        dba = new DatabaseAdapter(this);

        //leemos los pokemon del almacenamiento local
        readPokemon();

        //cargamos los pokemon en el results adapter para modificar la vista
        pokemonRView = findViewById(R.id.pokedRecycler);
        pokeResultsAdapter = new PokemonResultsAdapter(this, pokemons);

        pokemonRView.setAdapter(pokeResultsAdapter);
        // Give the RecyclerView a default layout manager.
        pokemonRView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void readPokemon(){
        String[] row;
        PokemonInfo info;

        for(int i = 0; i < MAX_POKEMON; i++ ){
            row = dba.getSinlgeEntry(i+1);

            info = new PokemonInfo();
            info.setIndex(row[0]);
            info.setName(row[1]);

            pokemons.add(info);
        }
    }
}
