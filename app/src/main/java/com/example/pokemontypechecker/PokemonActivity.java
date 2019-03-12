package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pokemontypechecker.data.NameUrlPair;
import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.PokemonAPIViewModel;
import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.utils.PokeAPIUtils;
import com.example.pokemontypechecker.utils.PokemonUtils;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClickListener {

    private static final String TAG = PokemonActivity.class.getSimpleName();


    private RecyclerView mPokemonNameListRV;
    private PokemonAdapter mPokemonAdapter;
    private PokemonAPIViewModel mPokemonAPIViewModel;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mTypeHeaderTV;
    private TextView mLoadingErrorTV;

    private NameUrlPair mPokemonType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        getSupportActionBar().setElevation(0);

        mPokemonNameListRV = findViewById(R.id.rv_pokemon_name_list);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorTV = findViewById(R.id.tv_loading_error_message);
        mTypeHeaderTV = findViewById(R.id.tv_pokemon_type_header);

        mPokemonAdapter = new PokemonAdapter(this);
        mPokemonNameListRV.setAdapter(mPokemonAdapter);
        mPokemonNameListRV.setLayoutManager(new LinearLayoutManager(this));
        mPokemonNameListRV.setHasFixedSize(true);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PokemonUtils.POKEMON_TYPE)) {
            mPokemonType = (NameUrlPair) intent.getSerializableExtra(PokemonUtils.POKEMON_TYPE);
            mTypeHeaderTV.setText(mPokemonType.name);
        }

        mPokemonAPIViewModel = ViewModelProviders.of(this).get(PokemonAPIViewModel.class);

        mPokemonAPIViewModel.getTypeSearchResults().observe(this, new Observer<PokeAPIUtils.PokeApiTypeReturn>() {
            @Override
            public void onChanged(@Nullable PokeAPIUtils.PokeApiTypeReturn allTypes) {
                mPokemonAdapter.updatePokemonResults(allTypes);
            }
        });

        mPokemonAPIViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPokemonNameListRV.setVisibility(View.VISIBLE);
                    mLoadingErrorTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPokemonNameListRV.setVisibility(View.INVISIBLE);
                    mLoadingErrorTV.setVisibility(View.VISIBLE);
                }
            }
        });

       getListOfPokemon();
    }


    private void getListOfPokemon() {
        Log.d(TAG, mPokemonType.url);
        mPokemonAPIViewModel.loadTypeSearchResults(mPokemonType.url);
    }

    @Override
    public void onPokemonClick(PokeAPIUtils.PokeApiPokemon pokemon) {
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_NAME, pokemon);
        startActivity(intent);
    }

}
