package com.example.pokemontypechecker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pokemontypechecker.data.PokemonAllTypesViewModel;
import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.utils.NetworkUtils;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.io.IOException;

import com.example.pokemontypechecker.utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements
        PokemonTypeAdapter.OnTypeClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = PokeAPIUtils.class.getSimpleName();


    private RecyclerView mPokemonTypesRV;

    private PokemonTypeAdapter mPokemonTypeAdapter;
    private PokemonAllTypesViewModel mAllTypesViewModel;

    private List<String> mTypes = new ArrayList<String>() {{
        add("fire");
        add("water");
        add("poison");
        add("psychic");
        add("ice");
        add("ghost");
        add("steel");
        add("fairy");
        add("grass");
        add("bug");
        add("fighting");
        add("dark");
        add("dragon");
        add("normal");
        add("ground");
        add("rock");
        add("electric");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mPokemonTypesRV = findViewById(R.id.rv_types_list);

        mPokemonTypeAdapter = new PokemonTypeAdapter(this);
        mPokemonTypesRV.setAdapter(mPokemonTypeAdapter);
        mPokemonTypesRV.setLayoutManager(new LinearLayoutManager(this));
        mPokemonTypesRV.setHasFixedSize(true);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mAllTypesViewModel = ViewModelProviders.of(this).get(PokemonAllTypesViewModel.class);

        mAllTypesViewModel.getSearchResults().observe(this, new Observer<PokeAPIUtils.PokeApiGeneralTypeSearchReturn>() {
            @Override
            public void onChanged(@Nullable PokeAPIUtils.PokeApiGeneralTypeSearchReturn allTypes) {
                mPokemonTypeAdapter.updateSearchResults(allTypes);
            }
        });

        mAllTypesViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    //mLoadingPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    //mLoadingPB.setVisibility(View.INVISIBLE);
                    //mSearchResultsRV.setVisibility(View.VISIBLE);
                    //mLoadingErrorTV.setVisibility(View.INVISIBLE);
                } else {
                    //mLoadingPB.setVisibility(View.INVISIBLE);
                    //mSearchResultsRV.setVisibility(View.INVISIBLE);
                    //mLoadingErrorTV.setVisibility(View.VISIBLE);
                }
            }
        });
        
        
        getListOfTypes();

    }

    private void getListOfTypes()
    {
        //String url = "https://google.com";
        String url = PokeAPIUtils.buildURL();
        Log.d(TAG, url);
        mAllTypesViewModel.loadSearchResults(url);

        //mPokemonTypeAdapter.updateSearchResults(mTypes);
    }

    @Override
    public void onTypeClick(String s) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_TYPE, s);
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
