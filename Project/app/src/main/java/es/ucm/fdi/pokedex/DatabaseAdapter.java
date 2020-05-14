package es.ucm.fdi.pokedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class implements the methods to query, insert and update the SQLite Database
 */
public class DatabaseAdapter {

    static final String DATABASE_NAME = "database.db";
    private static final String DEBUG_TAG = DatabaseAdapter.class.getSimpleName();
    String ok="OK";
    static final int DATABASE_VERSION = 1;
    public  static String getPassword="";

    static final String DATABASE_CREATE = "create table pokemon( id integer primary key , name  text,  captured boolean ); ";

    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;


    public  DatabaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This method open a connection with the database with write permission
     * @return The DatabaseAdapter instance
     * @throws SQLException
     */
    public  DatabaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();        return this;
    }


    /**
     * This method close the connection to de database
     */
    public void close()
    {
        db.close();
    }


    /**
     * This method returns an instance of the SQLiteDatabase
     * @return SQLite Database
     */
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }


    /**
     * This method insert a single row into the table Pokemon of the database
     * @param id column id
     * @param name column name
     * @param captured column captured
     * @return ok if everything was ok
     */
    public String insertEntry(int id,String name, Boolean captured)
    {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("id", id);
            newValues.put("name", name);
            newValues.put("captured", captured);

            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result=db.insert("pokemon", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Pokemon Info Saved", Toast.LENGTH_LONG).show();
        }catch(Exception ex) {
            System.out.println("Exceptions " +ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }


    /**
     * This method retrieves a single row of the table Pokemon of the database
     * @param id column id
     * @return the full row
     */
    public String[] getSinlgeEntry(int id)
    {
        db=dbHelper.getReadableDatabase();

        String[] row = new String[3];

        Cursor cursor=db.query("pokemon", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursor.getCount()<1){
            row[0] = "NOT EXIST";
            return row;
        }

        cursor.moveToFirst();

        row[0]= cursor.getString(cursor.getColumnIndex("id"));
        row[1]= cursor.getString(cursor.getColumnIndex("name"));
        row[2]= cursor.getString(cursor.getColumnIndex("captured"));

        return row;
    }

    /**
     * This method update a single row of the table Pokemon of the database
     * @param id column id
     */
    public void modifySingleEntry(int id){
        db = dbHelper.getWritableDatabase();

        String modifyquery = "UPDATE pokemon\n" +
                "SET captured = true\n" +
                "WHERE id = "+id+";";
        try{
            db.execSQL(modifyquery);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * This method intializes the database for the first app access
     */
    public void initialize(){
        String response = getPokemonList();

        try{
            JSONObject json = new JSONObject(response);
            JSONArray types = (JSONArray) json.get("results");

            int id;
            String name;
            String insert;

            for(int i = 0; i<types.length(); i++){

                id = i+1;
                JSONObject result = types.getJSONObject(i);
                name = result.getString("name");

               insert = insertEntry(id, name, false);


            }



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method queries the api to get the full pokemon list
     * @return The JSON string
     */
    public String getPokemonList()
    {


        Uri builtURI = Uri.parse("https://pokeapi.co/api/v2/pokemon/").buildUpon()
                .appendQueryParameter("limit", "807")
                .build();
        String url = builtURI.toString();

        try {

            InputStream response = null;
            HttpURLConnection conn = null;

            try {
                URL conn_url = new URL(url);
                conn = (HttpURLConnection) conn_url.openConnection();
                conn.connect();

                int code = conn.getResponseCode();

                if(code == 200){
                    Log.d(DEBUG_TAG, "The code response is: " + code);
                    response = conn.getInputStream();

                    String content = inputToString(response);

                    Log.d(DEBUG_TAG, "The string is: " + content);

                    return content;

                } else{

                    return null;

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

                // Close the connection
            } catch(Exception e){
                e.printStackTrace();
            }
            finally {
                conn.disconnect();
                if (response != null) {
                    response.close();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return "Error";
    }

    /**
     * This method transforms the InputStream to String
     * @param response The Stream from the API
     * @return String with JSON format
     * @throws IOException
     */
    private String inputToString(InputStream response) throws IOException {

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(response,"UTF-8"));
        String line = "";
        String result = "";

        while ((line = reader.readLine()) != null){

            result += line;

        }
        response.close();
        return result;
    }


}
