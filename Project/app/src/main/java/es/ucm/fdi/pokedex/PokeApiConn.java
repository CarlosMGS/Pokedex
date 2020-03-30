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

public class PokeApiConn {

    private static final String DEBUG_TAG = PokeApiConn.class.getSimpleName();
    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    public String createURL(String queryString){

        String queryParam = "";

        // Build up the query URI, limiting results to 5 printed books.
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(queryParam, queryString)
                .build();

        return builtURI.toString();
    }

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
