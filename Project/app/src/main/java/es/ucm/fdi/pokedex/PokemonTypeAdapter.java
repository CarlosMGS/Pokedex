package es.ucm.fdi.pokedex;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlevonyan.views.chip.Chip;

import java.util.List;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeAdapter.PokemonViewHolder> {

    private LayoutInflater inflater;
    private List<PokemonInfo> pokemonList;
    private int typePos;

    public PokemonTypeAdapter(Context context, List<PokemonInfo> pokemonList, int typePos) {
        this.inflater = LayoutInflater.from(context);
        this.pokemonList = pokemonList;
        this.typePos = typePos;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.act_poke_view_chip_item, parent, false);
        return new PokemonViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonTypeAdapter.PokemonViewHolder holder, int position) {
        PokemonInfo current = pokemonList.get(position);

        holder.chip.setChipText(current.getTypes().get(this.typePos));
        holder.chip.changeBackgroundColor(getColorByType(current.getTypes().get(this.typePos)));

    }

    @Override
    public int getItemCount() { return pokemonList.size(); }

    public  class PokemonViewHolder extends RecyclerView.ViewHolder{

        private Chip chip;
        private PokemonTypeAdapter pokemonTypeAdapter;

        public PokemonViewHolder(View itemView, PokemonTypeAdapter pokemonTypeAdapter) {
            super(itemView);

            chip = itemView.findViewById(R.id.chip);
            this.pokemonTypeAdapter = pokemonTypeAdapter;
        }
    }


    /**
     * The method getColorByType() returns the color of an input type.
     * @param type : Pokemon´s type
     * @return
     */
    public int getColorByType(String type) {
        switch(type)
        {
            case "normal":
                return Color.parseColor("#A4A27A");
            case "dragon":
                return Color.parseColor("#743BFB");
            case "psychic":
                return Color.parseColor("#F15B85");
            case "electric":
                return Color.parseColor("#E9CA3C");
            case "ground":
                return Color.parseColor("#D9BF6C");
            case "grass":
                return Color.parseColor("#81C85B");
            case "poison":
                return Color.parseColor("#A441A3");
            case "steel":
                return Color.parseColor("#BAB7D2");
            case "fairy":
                return Color.parseColor("#DDA2DF");
            case "fire":
                return Color.parseColor("#F48130");
            case "fight":
                return Color.parseColor("#BE3027");
            case "bug":
                return Color.parseColor("#A8B822");
            case "ghost":
                return Color.parseColor("#705693");
            case "dark":
                return Color.parseColor("#745945");
            case "ice":
                return Color.parseColor("#9BD8D8");
            case "water":
                return Color.parseColor("#658FF1");
            default:
                return Color.parseColor("#658FA0");
        }
    }

}
