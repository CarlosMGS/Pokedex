package es.ucm.fdi.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private DatabaseAdapter dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        dba = new DatabaseAdapter(getApplicationContext());


        //if the app is offline, the finder gets disabled
        if(!isConnected){
            ((Button) findViewById(R.id.ButtonFinder)).setEnabled(false);
            if(!isdbConfigured()){
                ((Button) findViewById(R.id.ButtonPokedex)).setEnabled(false);
            }
        }else{
            if(!isdbConfigured()){
                //dba.initialize();
            }
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

    /*
    public void goToFavs(View view){
        Intent favs = new Intent(this, FavsActivity.class);

        startActivity(favs);
    }*/

    public boolean isdbConfigured(){
        dba.open();
        SQLiteDatabase db = dba.getDatabaseInstance();
        final Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM pokemon;", null);
        int count = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        if(count != 0){
            return true;
        }else{
            return false;
        }
    }




}
