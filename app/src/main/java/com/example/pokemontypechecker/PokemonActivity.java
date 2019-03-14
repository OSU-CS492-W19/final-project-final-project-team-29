package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pokemontypechecker.data.view_models.PokemonAPITypeViewModel;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.utils.PokemonUtils;

import java.util.List;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClickListener {

    private static final String TAG = PokemonActivity.class.getSimpleName();

    private RecyclerView mPokemonNameListRV;
    private PokemonAdapter mPokemonAdapter;
    private PokemonAPITypeViewModel mPokemonAPIViewModel;
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

        mPokemonAPIViewModel = ViewModelProviders.of(this).get(PokemonAPITypeViewModel.class);

        mPokemonAPIViewModel.getTypeSearchResults().observe(this, new Observer<List<NameUrlPair>>() {
            @Override
            public void onChanged(@Nullable List<NameUrlPair> allPokemon) {
                if(allPokemon != null) {
                    mPokemonAdapter.updatePokemonResults(allPokemon);
                }
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

        getTypeToSearch();
        getListOfPokemon();
    }

    private void getTypeToSearch() {
        // update the current pokemon type search using shared preferences
        SharedPreferences shared =  PreferenceManager.getDefaultSharedPreferences(this);
        if(mPokemonType != null) {
            SharedPreferences.Editor editor = shared.edit();
            editor.putString(PokemonUtils.POKEMON_TYPE, mPokemonType.name);
            editor.putString(PokemonUtils.POKEMON_URL, mPokemonType.url);
            editor.apply();
        } else {
            mPokemonType = new NameUrlPair();
            mPokemonType.name = shared.getString(PokemonUtils.POKEMON_TYPE, "");
            mPokemonType.url = shared.getString(PokemonUtils.POKEMON_URL, "");
            mTypeHeaderTV.setText(mPokemonType.name);
        }
    }

    private void getListOfPokemon() {
        if(mPokemonType != null) {
            Log.d(TAG, mPokemonType.url);
            mPokemonAPIViewModel.loadTypeSearchResults(mPokemonType.url);
        } else {
            mPokemonAPIViewModel.getTypeSearchResults();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState){
        outState.putSerializable("name_url_pair", mPokemonType);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onPokemonClick(NameUrlPair pokemon) {
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_NAME, pokemon);
        startActivity(intent);
    }

}
