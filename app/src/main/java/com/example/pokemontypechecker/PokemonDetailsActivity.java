package com.example.pokemontypechecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pokemontypechecker.utils.PokemonUtils;

public class PokemonDetailsActivity extends AppCompatActivity {
    private TextView mPokemonDetailsTV;
    private String mPokemonName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_datails);

        mPokemonDetailsTV = findViewById(R.id.tv_pokemon_details);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PokemonUtils.POKEMON_NAME)) {
            mPokemonName = intent.getStringExtra(PokemonUtils.POKEMON_NAME);
            mPokemonDetailsTV.setText("Details for " + mPokemonName);
        }
    }
}
