package es.ucm.fdi.pokedex;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

class PokemonInfo {

    private String name;
    private String index;
    private String types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public static PokemonInfo fromJsonResponse(String input){
        PokemonInfo pokemon = new PokemonInfo();


        try{
            JSONObject json = new JSONObject(input);

            JSONArray forms = (JSONArray) json.get("forms");
            pokemon.setName(forms.getJSONObject(0).getString("name"));

            //JSONArray indices = (JSONArray) json.get("game_indices");
            pokemon.setIndex(json.get("id").toString());

            String types_s = "";
            JSONArray types = (JSONArray) json.get("types");
            for(int i = 0; i<types.length(); i++){

                JSONObject type = types.getJSONObject(i);
                JSONObject ty = type.getJSONObject("type");
                types_s += ty.getString("name") + " ";

            }
            pokemon.setTypes(types_s);

        }catch(Exception e){
            e.printStackTrace();
        }


        return pokemon;
    }





}
