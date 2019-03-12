package com.example.pokemontypechecker.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pokemontypechecker.utils.NetworkUtils;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.io.IOException;

public class PokemonAPIAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PokeAPIUtils.class.getSimpleName();
    private Callback mCallback;
    private String mURL;


    public interface Callback {
        void onSearchFinished(PokeAPIUtils.PokeApiGeneralTypeSearchReturn searchResults);
    }

    public PokemonAPIAsyncTask(String url, Callback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String resultsJSON = null;
        try {
            Log.d(TAG, mURL);
            resultsJSON = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultsJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        PokeAPIUtils.PokeApiGeneralTypeSearchReturn searchResults = null;
        if (s != null) {
            searchResults = PokeAPIUtils.parseGeneralSearchJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}