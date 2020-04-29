package es.ucm.fdi.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PokemonResultsAdapter extends RecyclerView.Adapter<PokemonResultsAdapter.PokemonViewHolder> {

    private List<PokemonInfo> pokemonList;
    private LayoutInflater inflater;

    public PokemonResultsAdapter(Context context, List<PokemonInfo> info) {
        pokemonList = info;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.pokemon_preview, parent, false);
        return new PokemonViewHolder(rootView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonInfo current = pokemonList.get(position);
        holder.name.setText(current.getName());
        holder.index.setText(current.getIndex());
        holder.image.setImageResource(current.getImage());
    }
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void setPokemonData(List<PokemonInfo> info) {
        pokemonList = info;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView index;

        private ImageView image;

        private PokemonResultsAdapter adapter;


        public PokemonViewHolder(@NonNull View view, PokemonResultsAdapter adapter) {

            super(view);


            name = (TextView) view.findViewById(R.id.PokemonName);
            index = (TextView) view.findViewById(R.id.PokemonIndex);
            image = (ImageView) view.findViewById(R.id.PokemonImage);

            this.adapter = adapter;
        }
    }
}
