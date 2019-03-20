package com.example.pokemontypechecker.data.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.api_models.PokeAPIGeneralTypeSearchReturn;
import com.example.pokemontypechecker.utils.NetworkUtils;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.io.IOException;
import java.util.List;

public class PokeAPITypesAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = PokeAPIUtils.class.getSimpleName();
    private Callback mCallback;
    private String mURL;


    public interface Callback {
        void onSearchFinished(List<NameUrlPair> searchResults);
    }

    public PokeAPITypesAsyncTask(String url, Callback callback) {
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
        List<NameUrlPair> searchResults = null;
        if (s != null) {
            searchResults = PokeAPIUtils.parseGeneralTypeSearchJSON(s);
        }
        mCallback.onSearchFinished(searchResults);
    }
}