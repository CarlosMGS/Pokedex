package es.ucm.fdi.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void goToPokedex(View view){
        Intent pokedex = new Intent(this, PokedexActivity.class);

        startActivity(pokedex);
    }

    public void goToFinder(View view){
        Intent finder = new Intent(this, FinderActivity.class);

        startActivity(finder);
    }

    public void goToFavs(View view){
        Intent favs = new Intent(this, FavsActivity.class);

        startActivity(favs);
    }


}
