package es.ucm.fdi.pokedex;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokedActivity extends AppCompatActivity implements PokemonResultsAdapter.OnPokemonListener{



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

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        pokemons = new ArrayList<PokemonInfo>();
        dba = new DatabaseAdapter(this);

        //leemos los pokemon del almacenamiento local
        readPokemon();

        //cargamos los pokemon en el results adapter para modificar la vista
        pokemonRView = findViewById(R.id.pokedRecycler);
        pokeResultsAdapter = new PokemonResultsAdapter(this, pokemons, dba, this);

        pokemonRView.setAdapter(pokeResultsAdapter);
        // Give the RecyclerView a default layout manager.
        pokemonRView.setLayoutManager(new LinearLayoutManager(this));

        CardView cv = (CardView) findViewById(R.id.cardView);

        /*
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent poked = new Intent(PokedActivity.this, FinderActivity.class);

                String name = ((TextView)findViewById(R.id.PokemonName)).getText().toString();

                ((EditText)findViewById(R.id.textPokemon)).setText(name);

                startActivity(poked);
            }
        });
        */

    }

    protected void readPokemon(){
        String[] row;
        PokemonInfo info;
        int id;
        int ima;
        String img;

        for(int i = 0; i < MAX_POKEMON; i++ ){

            id = i+1;
            row = dba.getSinlgeEntry(id);

            info = new PokemonInfo();
            info.setIndex(row[0]);
            info.setName(row[1]);
            //info.setImage(R.drawable.1);
            img = "img" + id;
            Resources res = getResources();
            ima = res.getIdentifier(img, "drawable", getPackageName());
            info.setImage(ima);
            pokemons.add(info);
        }
    }


    @Override
    public void onPokemonClick(int position) {
        PokeApiConn conn = new PokeApiConn();
        position++;
        PokemonInfo pokemonInfo = conn.pokemonRetriever(position+"");

        Intent poked = new Intent(this, PokemonViewActivity.class);

        poked.putExtra("index", pokemonInfo.getIndex());
        poked.putExtra("name", pokemonInfo.getName());
        poked.putExtra("weight", pokemonInfo.getWeight());
        poked.putExtra("height", pokemonInfo.getHeight());

        poked.putStringArrayListExtra("types", (ArrayList<String>) pokemonInfo.getTypes());

        startActivity(poked);

    }
}
