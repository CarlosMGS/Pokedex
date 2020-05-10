package es.ucm.fdi.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * That's the main activity of the application. Here they are implemented the methods to access the
 * other app's screens, FinderActivity and PokedActivity.
 *
 * @author Carlos Gil, Álvaro Pascual
 */
public class MainActivity extends AppCompatActivity {

    private DatabaseAdapter dba;

    /**
     * The onCreate method checks if there's an active connection to allow the Finder button and
     * for the first access the database intializing.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
                dba.initialize();
            }
        }


    }

    /**
     * Method to go to Pokedex View
     * @param view
     */
    public void goToPokedex(View view){
        Intent poked = new Intent(this, PokedActivity.class);

        startActivity(poked);
    }

    /**
     * Method to go to Finder View
     * @param view
     */
    public void goToFinder(View view){
        Intent finder = new Intent(this, FinderActivity.class);

        startActivity(finder);
    }

    /*
    public void goToFavs(View view){
        Intent favs = new Intent(this, FavsActivity.class);

        startActivity(favs);
    }*/

    /**
     * This method checks if the SQLite DataBase is configured.
     * @return if the database is intialized and configured returns true, if not returns false
     */
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

        if(count >1 ){
            return true;
        }else{
            return false;
        }
    }




}
