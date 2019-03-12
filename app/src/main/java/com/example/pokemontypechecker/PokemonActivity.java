package com.example.pokemontypechecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClickListener {

    private RecyclerView mPokemonNameListRV;
    private TextView mPokemonHeaderTV;

    private PokemonAdapter mPokemonAdapter;

    private String mPokemonType;

    // TODO: will remove this, temporary list of pokemon names
    private List<Pokemon> mPokemon = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        getSupportActionBar().setElevation(0);

        mPokemonNameListRV = findViewById(R.id.rv_pokemon_name_list);
        mPokemonHeaderTV = findViewById(R.id.tv_pokemon_type);

        mPokemonAdapter = new PokemonAdapter(this);
        mPokemonNameListRV.setAdapter(mPokemonAdapter);
        mPokemonNameListRV.setLayoutManager(new LinearLayoutManager(this));
        mPokemonNameListRV.setHasFixedSize(true);

        if(savedInstanceState != null) {
            mPokemonType = savedInstanceState.getString(PokemonUtils.POKEMON_TYPE);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PokemonUtils.POKEMON_TYPE)) {
            mPokemonType = intent.getStringExtra(PokemonUtils.POKEMON_TYPE);
        }

        Pokemon p = new Pokemon();
        p.id = 1;
        p.name = "Pikachu";
        p.url = "https://pokemon.com/1";

        mPokemon.add(p);


        mPokemonHeaderTV.setText(mPokemonType);
        mPokemonAdapter.updatePokemonResults(mPokemon);
    }

    @Override
    public void onPokemonClick(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_NAME, pokemon);
        startActivity(intent);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString(PokemonUtils.POKEMON_TYPE, mPokemonType);
//        super.onSaveInstanceState(outState);
//    }
}
