package com.example.pokemontypechecker.data.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pokemontypechecker.data.api_models.PokeAPIPokemonSearchReturn;
import com.example.pokemontypechecker.utils.NetworkUtils;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.io.IOException;

public class PokeAPIPokemonAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PokeAPIPokemonAsyncTask.class.getSimpleName();
    private PokeAPIPokemonAsyncTask.Callback mCallback;
    private String mURL;

    public interface Callback {
        void onSearchFinished(PokeAPIPokemonSearchReturn searchResults);
    }

    public PokeAPIPokemonAsyncTask(String url, PokeAPIPokemonAsyncTask.Callback callback) {
        mURL = url;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(mURL != null) {
            String resultsJSON = null;
            try {
                Log.d(TAG, mURL);
                resultsJSON = NetworkUtils.doHTTPGet(mURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultsJSON;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        PokeAPIPokemonSearchReturn searchResults = null;
        if (s != null) {
            searchResults = PokeAPIUtils.parsePokemonSearchJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}
