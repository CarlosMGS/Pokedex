package es.ucm.fdi.pokedex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PokedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        getSupportActionBar().hide();
    }
}
