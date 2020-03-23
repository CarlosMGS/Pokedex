package es.ucm.fdi.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        //if the app is offline, the finder gets disabled
        if(!isConnected){
            ((Button) findViewById(R.id.ButtonFinder)).setEnabled(false);
        }


    }

    public void goToPokedex(View view){
        Intent poked = new Intent(this, PokedActivity.class);

        startActivity(poked);
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
