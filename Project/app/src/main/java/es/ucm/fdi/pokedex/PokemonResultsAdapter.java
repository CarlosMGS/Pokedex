package es.ucm.fdi.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class PokemonResultsAdapter extends RecyclerView.Adapter<PokemonResultsAdapter.PokemonViewHolder> {

    private PokemonInfo pokemon;
    private LayoutInflater inflater;

    public PokemonResultsAdapter(Context context, PokemonInfo info) {
        pokemon = info;

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
        PokemonInfo current = pokemon;
        holder.name.setText(current.getName());
        holder.index.setText(current.getIndex());
        holder.types.setText(current.getTypes());
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public void setPokemonData(PokemonInfo info) {
        pokemon = info;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView index;
        private TextView types;

        private PokemonResultsAdapter adapter;


        public PokemonViewHolder(@NonNull View view, PokemonResultsAdapter adapter) {

            super(view);


            name = (TextView) view.findViewById(R.id.PokemonName);
            index = (TextView) view.findViewById(R.id.PokemonIndex);
            types = (TextView) view.findViewById(R.id.PokemonType);

            this.adapter = adapter;
        }
    }
}
