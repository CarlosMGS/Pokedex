package es.ucm.fdi.pokedex;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

class PokemonInfo {

    private String name;
    private String index;
    private String types;
    private String height;
    private String weight;
    private String image;

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

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

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

            //load height and weight
            pokemon.setHeight(json.get("height").toString());
            pokemon.setWeight(json.get("weight").toString());

            //load sprite
            JSONArray sprites = (JSONArray) json.get("sprites");
            String frontDefaultSprite = sprites.getString(Integer.parseInt("front_default"));

        }catch(Exception e){
            e.printStackTrace();
        }


        return pokemon;
    }





}
