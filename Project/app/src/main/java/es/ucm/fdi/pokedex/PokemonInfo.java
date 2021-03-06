package es.ucm.fdi.pokedex;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * PokemonInfo has the details and stats of a Pokemon. From its name to its image.
 * @author Carlos Gil, Álvaro Pascual
 */
class PokemonInfo {

    private String name;
    private String index;
    private List<String> types;
    private String height;
    private String weight;
    private int image;

    /**
     * Empty constructor.
     */
    public PokemonInfo() {}

    /**
     * Parametrized constructor.
     * @param name
     * @param index
     * @param types
     * @param height
     * @param weight
     * @param image
     */
    public PokemonInfo(String name, String index, List<String> types, String height, String weight, int image) {
        this.name = name;
        this.index = index;
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.image = image;
    }

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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

    /**
     * The method fromJsonResponse() analyses the casted String of the JSON obtained from the API with
     * all the Pokemon info, and extracts it by splitting the JSON in all its different parts.
     * @param input
     * @return a PokemonInfo object.
     */
    public static PokemonInfo fromJsonResponse(String input){
        PokemonInfo pokemon = new PokemonInfo();

        try{
            JSONObject json = new JSONObject(input);

            JSONArray forms = (JSONArray) json.get("forms");
            pokemon.setName(forms.getJSONObject(0).getString("name"));

            //JSONArray indices = (JSONArray) json.get("game_indices");
            pokemon.setIndex(json.get("id").toString());

            ArrayList<String> typesList = new ArrayList<>();
            JSONArray types = (JSONArray) json.get("types");
            for(int i = 0; i<types.length(); i++){

                JSONObject type = types.getJSONObject(i);
                JSONObject ty = type.getJSONObject("type");
                typesList.add(ty.getString("name"));

            }
            pokemon.setTypes(typesList);

            //load height and weight
            pokemon.setHeight(json.get("height").toString());
            pokemon.setWeight(json.get("weight").toString());

            //load sprite
            JSONArray sprites = (JSONArray) json.get("sprites");
            String frontDefaultSprite = sprites.getString(Integer.parseInt("front_default")); //esto se usa?

        }catch(Exception e){
            e.printStackTrace();
        }

        return pokemon;
    }





}
