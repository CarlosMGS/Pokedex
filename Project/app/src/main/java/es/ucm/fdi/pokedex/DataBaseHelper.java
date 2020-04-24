package es.ucm.fdi.pokedex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DatabaseAdapter.DATABASE_CREATE);
        }catch(Exception er){
            Log.e("Error","exception");

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + "pokemon");


        onCreate(db);
    }
}
