package es.ucm.fdi.pokedex;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonResultsAdapter extends RecyclerView.Adapter<PokemonResultsAdapter.PokemonViewHolder> {


    public PokemonResultsAdapter(FinderActivity finderActivity, PokemonInfo info) {

    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setPokemonData(PokemonInfo info) {
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
