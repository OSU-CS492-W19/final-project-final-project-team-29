package com.example.pokemontypechecker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.util.List;

public class FavoritePokemonAdapter extends RecyclerView.Adapter<FavoritePokemonAdapter.FavoritePokemonViewHolder> {
    private List<Pokemon> mPokemon;
    OnFavPokeClickListener mFavClickListener;

    public interface OnFavPokeClickListener {
        void onFavClick(Pokemon pokemon);
    }

    FavoritePokemonAdapter(OnFavPokeClickListener typeClickListener) {
        mFavClickListener = typeClickListener;
    }

    public void updateSearchResults(List<Pokemon> types) {
        mPokemon = types;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mPokemon != null) {
            return mPokemon.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public FavoritePokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_pokemon_item, parent, false);
        return new FavoritePokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePokemonViewHolder holder, int position) {
        holder.bind(mPokemon.get(position));
    }

    class FavoritePokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mPokemonTypeTV;
        private ImageView mPokemonSpriteIV;

        public FavoritePokemonViewHolder(View itemView) {
            super(itemView);
            mPokemonTypeTV = itemView.findViewById(R.id.tv_fav_poke_item);
            itemView.setOnClickListener(this);
            mPokemonSpriteIV = itemView.findViewById(R.id.iv_pokemon_name_item);

        }

        @Override
        public void onClick(View v) {
            Pokemon type = mPokemon.get(getAdapterPosition());
            mFavClickListener.onFavClick(type);
        }

        public void bind(Pokemon poke) {
            mPokemonTypeTV.setText(poke.name);
            String pokemonId = PokeAPIUtils.parseForPokemonIdFromUrl(poke);
//            String spriteUrl = getString(R.string.sprite_look_up, pokemonId);
            String spriteUrl = String.format("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png", pokemonId);

            Glide.with(mPokemonSpriteIV.getContext()).load(spriteUrl).into(mPokemonSpriteIV);

        }
    }
}

