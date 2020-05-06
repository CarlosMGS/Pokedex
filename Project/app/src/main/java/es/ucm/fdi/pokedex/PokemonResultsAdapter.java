package es.ucm.fdi.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private DatabaseAdapter dba;
    private OnPokemonListener mOnPokemonListener;

    public PokemonResultsAdapter(Context context, List<PokemonInfo> info, DatabaseAdapter dba,
                                 OnPokemonListener onPokemonListener) {
        pokemonList = info;

        inflater = LayoutInflater.from(context);

        this.dba = dba;
        this.mOnPokemonListener = onPokemonListener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.pokemon_preview, parent, false);
        return new PokemonViewHolder(rootView, this, mOnPokemonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonInfo current = pokemonList.get(position);
        holder.name.setText(current.getName());
        holder.index.setText(current.getIndex());
        holder.image.setImageResource(current.getImage());

        String[] row = dba.getSinlgeEntry(Integer.parseInt(current.getIndex()));

        if (Boolean.valueOf(row[2])) {
            holder.imageball.setImageResource(R.drawable.pokeball);
        }


    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void setPokemonData(List<PokemonInfo> info) {
        pokemonList = info;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView index;

        private ImageView image;
        private ImageView imageball;

        private OnPokemonListener pokemonListener;

        private PokemonResultsAdapter adapter;


        public PokemonViewHolder(@NonNull View view, PokemonResultsAdapter adapter, OnPokemonListener pokemonListener) {

            super(view);


            name = (TextView) view.findViewById(R.id.PokemonName);
            index = (TextView) view.findViewById(R.id.PokemonIndex);
            image = (ImageView) view.findViewById(R.id.PokemonImage);
            imageball = (ImageView) view.findViewById(R.id.imageball);

            this.adapter = adapter;

            this.pokemonListener = pokemonListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("TAG", "onClick: " + getAdapterPosition());
            pokemonListener.onPokemonClick(getAdapterPosition());
        }
    }

    public interface OnPokemonListener {
        void onPokemonClick(int position);

    }

}
