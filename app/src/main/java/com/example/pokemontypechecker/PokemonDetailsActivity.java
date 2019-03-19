package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.api_models.PokeAPIPokemonSearchReturn;
import com.example.pokemontypechecker.data.view_models.PokemonAPIPokemonViewModel;
import com.example.pokemontypechecker.utils.PokeAPIUtils;
import com.example.pokemontypechecker.utils.PokemonUtils;

import java.util.List;

public class PokemonDetailsActivity extends AppCompatActivity {
    private static final String TAG = PokemonActivity.class.getSimpleName();

    private PokemonDBViewModel mPokemonDBViewModel;
    private PokemonAPIPokemonViewModel mPokemonAPIViewModel;

    private TextView mPokemonDetailsTV, mFourTimesTitleTV, mTwoTimesTitleTV,
                    mNormalTitleTV, mHalfTitleTV, mQuarterTitleTV, mNoDamageTitleTV;
    private ImageView mPokemonStarIV;
    private TableLayout mDamageTable4, mDamageTable2, mDamageTable1,
            mDamageTableHalf, mDamageTableQuarter, mDamageTable0;
    private NameUrlPair mPokemon;

    private boolean mIsFavorite = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details);

        mPokemonDetailsTV = findViewById(R.id.tv_pokemon_details_name);
        mPokemonStarIV = findViewById(R.id.iv_pokemon_favorite);

        mDamageTable4 = findViewById(R.id.tl_damage_table4);
        mDamageTable2 = findViewById(R.id.tl_damage_table2);
        mDamageTable1 = findViewById(R.id.tl_damage_table1);
        mDamageTableHalf = findViewById(R.id.tl_damage_table0_5);
        mDamageTableQuarter = findViewById(R.id.tl_damage_table0_25);
        mDamageTable0 = findViewById(R.id.tl_damage_table0);

        mFourTimesTitleTV = findViewById(R.id.tv_4_times_damage_title);
        mTwoTimesTitleTV = findViewById(R.id.tv_2_times_damage_title);
        mNormalTitleTV = findViewById(R.id.tv_1_times_damage_title);
        mHalfTitleTV = findViewById(R.id.tv_half_times_damage_title);
        mQuarterTitleTV = findViewById(R.id.tv_quarter_times_damage_title);
        mNoDamageTitleTV = findViewById(R.id.tv_no_damage_title);


        mPokemonDBViewModel = ViewModelProviders.of(this).get(PokemonDBViewModel.class);
        mPokemonAPIViewModel = ViewModelProviders.of(this).get(PokemonAPIPokemonViewModel.class);

        mPokemon = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PokemonUtils.POKEMON_NAME)) {
            mPokemon = (NameUrlPair) intent.getSerializableExtra(PokemonUtils.POKEMON_NAME);

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

        }
//
//        TableRow row = new TableRow(this);
//        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//        row.setLayoutParams(params);
//
//        double[] damageMod = PokeAPIUtils.getTypeDamageModifiers();
//        for (int i = 0; i < damageMod.length; i ++)
//        {
//            TextView temp = new TextView(this);
//            mDamageTable.addView(temp);
//            tvDamageList[i] = temp;
//        }

        mPokemonAPIViewModel.getPokemonSearchResult().observe(this, new Observer<PokeAPIPokemonSearchReturn>() {
            @Override
            public void onChanged(@Nullable PokeAPIPokemonSearchReturn pokemon) {
                if (pokemon != null) {
                    mPokemonDetailsTV.setText(pokemon.name);
                    double[] damageMod = PokeAPIUtils.getTypeDamageModifiers(PokeAPIUtils.typeStringToEnum(pokemon.types));
                    for (int i = 0; i < damageMod.length; i ++)
                    {
                        //tvDamageList[i].setText(String.valueOf(damageMod[i]) + " : " + i);

                        displayDamageMods(damageMod);
                    }
                }
            }
        });

        mPokemonStarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPokemon != null) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.name = mPokemon.name;
                    pokemon.url = mPokemon.url;

                    if (!mIsFavorite) {
                        mPokemonDBViewModel.insertPokemon(pokemon);
                    } else {
                        mPokemonDBViewModel.deletePokemon(pokemon);
                    }
                }
            }
        });


        getPokemon();
    }

    private void displayDamageMods(double[] damageMod)
    {
        mDamageTable4.removeAllViews();
        mDamageTable2.removeAllViews();
        mDamageTable1.removeAllViews();
        mDamageTableHalf.removeAllViews();
        mDamageTableQuarter.removeAllViews();
        mDamageTable0.removeAllViews();

        for (int i = 1; i < damageMod.length; i ++)
        {
            TextView typeTextView = new TextView(this);
            typeTextView.setTextSize(14);

            if (damageMod[i] == 4){
                mDamageTable4.addView(typeTextView);
            }
            else if (damageMod[i] == 2){
                mDamageTable2.addView(typeTextView);
            }
            else if (damageMod[i] == 1){
                mDamageTable1.addView(typeTextView);
            }
            else if (damageMod[i] == 0.5){
                mDamageTableHalf.addView(typeTextView);
            }
            else if (damageMod[i] == 0.25){
                mDamageTableQuarter.addView(typeTextView);
            }
            else if (damageMod[i] == 0){
                mDamageTable0.addView(typeTextView);
            }

            typeTextView.setText(PokeAPIUtils.typeEnumToString(i));
            typeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        }
    }

    private void getPokemon() {
        Log.d(TAG, mPokemon.url);

        mPokemonAPIViewModel.loadPokemonSearchResults(mPokemon.url);
        mPokemonDBViewModel.getPokemonByName(mPokemon.name);
    }
}