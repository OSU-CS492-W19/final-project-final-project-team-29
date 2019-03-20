package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;

import com.example.pokemontypechecker.data.view_models.PokemonAPITypesViewModel;
import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.PokeAPIGeneralTypeSearchReturn;
import com.example.pokemontypechecker.utils.PokeAPIUtils;
import com.example.pokemontypechecker.utils.PokemonUtils;

import java.util.List;
import java.util.jar.Attributes;


public class MainActivity extends AppCompatActivity implements
        PokemonTypeAdapter.OnTypeClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        FavoritePokemonAdapter.OnFavPokeClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mPokemonTypesRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorTV;

    private RecyclerView mFavPokeRV;
    private FavoritePokemonAdapter mFavoritePokemonAdapter;

    private PokemonTypeAdapter mPokemonTypeAdapter;
    private PokemonAPITypesViewModel mPokemonAPITypesViewModel;

    private PokemonDBViewModel mPokemonDBViewModel;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mPokemonTypesRV = findViewById(R.id.rv_types_list);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorTV = findViewById(R.id.tv_loading_error_message);
        mFavPokeRV = findViewById(R.id.rv_fav_poke);

        mPokemonTypeAdapter = new PokemonTypeAdapter(this, getPackageName());
        mPokemonTypesRV.setAdapter(mPokemonTypeAdapter);
        mPokemonTypesRV.setLayoutManager(new LinearLayoutManager(this));
        mPokemonTypesRV.setHasFixedSize(true);

        mFavoritePokemonAdapter = new FavoritePokemonAdapter(this);
        mFavPokeRV.setAdapter(mFavoritePokemonAdapter);
        mFavPokeRV.setLayoutManager(new LinearLayoutManager(this));
        mFavPokeRV.setHasFixedSize(true);

        mPokemonDBViewModel = ViewModelProviders.of(this).get(PokemonDBViewModel.class);


        setSupportActionBar(toolbar);

        //getSupportActionBar().setElevation(0);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



        mDrawerLayout = findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
         //       this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //mDrawerLayout.addDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mPokemonAPITypesViewModel = ViewModelProviders.of(this).get(PokemonAPITypesViewModel.class);

        mPokemonAPITypesViewModel.getTypesSearchResults().observe(this, new Observer<List<NameUrlPair>>() {
            @Override
            public void onChanged(@Nullable List<NameUrlPair> allTypes) {
                if(allTypes != null) {
                    mPokemonTypeAdapter.updateSearchResults(allTypes);
                }
            }
        });

        mPokemonAPITypesViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPokemonTypesRV.setVisibility(View.VISIBLE);
                    mLoadingErrorTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPokemonTypesRV.setVisibility(View.INVISIBLE);
                    mLoadingErrorTV.setVisibility(View.VISIBLE);
                }
            }
        });
        
        
        getListOfTypes();

    }

    private void getListOfTypes()
    {
        String url = PokeAPIUtils.buildURL();
        Log.d(TAG, url);
        mPokemonAPITypesViewModel.loadTypesSearchResults(url);
    }

    @Override
    public void onTypeClick(NameUrlPair type) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onFavClick(Pokemon poke){
        NameUrlPair temp = new NameUrlPair();
        temp.name = poke.name;
        temp.url = poke.url;
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_NAME, temp );
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if( id == android.R.id.home) {
            mPokemonDBViewModel.getAllPokemon().observe(this, new Observer<List<Pokemon>>() {
                @Override
                public void onChanged(@Nullable List<Pokemon> locationItems) {
                    mFavoritePokemonAdapter.updateSearchResults(locationItems);
                }
            });
                mDrawerLayout.openDrawer(Gravity.START);
            return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
