package es.ucm.fdi.pokedex;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class connects with the API to get the PokemonInfo
 */
public class PokeApiConn {

    private static final String DEBUG_TAG = PokeApiConn.class.getSimpleName();
    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    /**
     * This method creates the URL to query the API
     * @param queryString The Pokemon name
     * @return The URL
     */
    public String createURL(String queryString){

        String queryParam = "";
        queryString = queryString.toLowerCase();


        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendPath(queryString)
                .build();

        return builtURI.toString();
    }

    /**
     * This method transform the Pokemon JSON into a PokemonInfo object
     * @param queryString Pokemon name
     * @return A PokemonInfo Object
     */
    public PokemonInfo pokemonRetriever(String queryString){

        String url = this.createURL(queryString);
        PokemonInfo pokemon = null;

        try {

            String info = this.connect(url);
            pokemon = PokemonInfo.fromJsonResponse(info);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return pokemon;
    }

    /**
     * This method connects to the API to get the InputStream
     * @param url The API URL with the Pokemon name
     * @return Pokemon JSON String
     * @throws IOException
     */
    private String connect(String url) throws IOException {

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
        } finally {
            conn.disconnect();
            if (response != null) {
                response.close();
            }
        }

        return "Error";

    }

    /**
     * This method transforms the InputStream to a JSON String
     * @param response The InputStream from the connection
     * @return The JSON String
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
