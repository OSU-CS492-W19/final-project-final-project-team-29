package com.example.pokemontypechecker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private List<String> mPokemonNames;
    private OnPokemonClickListener mPokemonClickListener;

    public interface OnPokemonClickListener {
        void onPokemonClick(String pokemon);
    }

    public PokemonAdapter(OnPokemonClickListener pokemonClickListener) {
        mPokemonClickListener = pokemonClickListener;
    }

    public void updatePokemonNameResults(List<String> pokemonNames) {
        mPokemonNames = pokemonNames;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_name_item, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.bind(mPokemonNames.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPokemonNames != null) {
            return mPokemonNames.size();
        } else {
            return 0;
        }
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mPokemonNameTV;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            mPokemonNameTV = itemView.findViewById(R.id.tv_pokemon_name_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String pokemonName = mPokemonNames.get(getAdapterPosition());
            mPokemonClickListener.onPokemonClick(pokemonName);
        }

        public void bind(String pokemon) {
            mPokemonNameTV.setText(pokemon);
        }
    }
}
