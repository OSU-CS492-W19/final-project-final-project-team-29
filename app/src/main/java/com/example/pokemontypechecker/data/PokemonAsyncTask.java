package com.example.pokemontypechecker.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pokemontypechecker.utils.NetworkUtils;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.io.IOException;

public class PokemonAsyncTask extends AsyncTask<String, Void, String> {
    private static final String TAG = PokeAPIUtils.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

        } else {

        }
    }
}