package com.example.pokemontypechecker;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokemontypechecker.data.api_models.NameUrlPair;

import java.util.List;


public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>  {
    private List<NameUrlPair> mPokemon;
    private OnPokemonClickListener mPokemonClickListener;

    public interface OnPokemonClickListener {
        void onPokemonClick(NameUrlPair pokemon);
    }

    public PokemonAdapter(OnPokemonClickListener pokemonClickListener) {
        mPokemonClickListener = pokemonClickListener;
    }

    public void updatePokemonResults(List<NameUrlPair> pokemon) {
        mPokemon = pokemon;
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
        holder.bind(mPokemon.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPokemon != null) {
            return mPokemon.size();
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
            NameUrlPair pokemon = mPokemon.get(getAdapterPosition());
            mPokemonClickListener.onPokemonClick(pokemon);
        }

        public void bind(NameUrlPair pokemon) {
            mPokemonNameTV.setText(pokemon.name);
        }
    }
}
