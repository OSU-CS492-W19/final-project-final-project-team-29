package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.PokemonAPIViewModel;
import com.example.pokemontypechecker.utils.PokemonUtils;

public class PokemonDetailsActivity extends AppCompatActivity {
    private PokemonDBViewModel mPokemonDBViewModel;
    private PokemonAPIViewModel mPokemonAPIViewModel;

    private TextView mPokemonDetailsTV;
    private ImageView mPokemonStarIV;
    private Pokemon mPokemon;



    private boolean mIsFavorite = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_datails);

        mPokemonDetailsTV = findViewById(R.id.tv_pokemon_details);
        mPokemonStarIV = findViewById(R.id.iv_pokemon_favorite);

        mPokemonDBViewModel = ViewModelProviders.of(this).get(PokemonDBViewModel.class);
        mPokemonAPIViewModel = ViewModelProviders.of(this).get(PokemonAPIViewModel.class);

        mPokemon = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PokemonUtils.POKEMON_NAME)) {
            mPokemon = (Pokemon) intent.getSerializableExtra(PokemonUtils.POKEMON_NAME);
            mPokemonDetailsTV.setText(mPokemon.name);
        }

        mPokemonDBViewModel.getPokemonByName(mPokemon.name).observe(this, new Observer<Pokemon>() {
            @Override
            public void onChanged(@Nullable Pokemon pokemon) {
                if (pokemon != null) {
                    mIsFavorite = true;
                    mPokemonStarIV.setImageResource(R.drawable.ic_star_yellow);
                } else {
                    mIsFavorite = false;
                    mPokemonStarIV.setImageResource(R.drawable.ic_star_border_yellow);
                }
            }
        });

        mPokemonStarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mPokemon != null) {
                if (!mIsFavorite) {
                    mPokemonDBViewModel.insertPokemon(mPokemon);
                } else {
                    mPokemonDBViewModel.deletePokemon(mPokemon);
                }
            }
            }
        });
    }
}
