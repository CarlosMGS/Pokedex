package es.ucm.fdi.pokedex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

class PokedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        getSupportActionBar().hide();
    }
}