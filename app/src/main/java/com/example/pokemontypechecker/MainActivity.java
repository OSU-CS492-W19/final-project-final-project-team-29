package com.example.pokemontypechecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.PokemonType;
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

        getListOfTypes();

    }

    private void getSpecificType(@PokeAPIUtils.PokemonEnumType int type){
        String url = PokeAPIUtils.buildURL(type);
        Log.d(TAG, url);
        new TempNetworkTask().execute(url);

    }

    private void getListOfTypes()
    {
        //String url = "https://google.com";
        String url = PokeAPIUtils.buildURL();
        Log.d(TAG, url);
        new TempNetworkTask().execute(url);

        mPokemonTypeAdapter.updateSearchResults(mTypes);
    }

    @Override
    public void onTypeClick(String s) {
        Intent intent = new Intent(this, PokemonActivity.class);
        intent.putExtra(PokemonUtils.POKEMON_TYPE, s);
        //startActivity(intent);
        getSpecificType(PokeAPIUtils.POISON);
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

    class TempNetworkTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // mTempMainContentText.setText("Starting data fetch");
        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            String results = null;
            try {
                Log.d(TAG, url);
                results = NetworkUtils.doHTTPGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                PokeAPIUtils.PokeApiGeneralTypeSearchReturn ParsedString = PokeAPIUtils.parseGeneralTypeSearchJSON(s);
                //mTempMainContentText.setText(String.valueOf(ParsedString[0].name));
            } else {
                //mTempMainContentText.setText("Error Getting Data");
            }
        }
    }
}
