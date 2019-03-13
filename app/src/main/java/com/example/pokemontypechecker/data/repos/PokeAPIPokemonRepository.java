package com.example.pokemontypechecker.data.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.PokeAPIPokemonSearchReturn;
import com.example.pokemontypechecker.data.async.PokeAPIPokemonAsyncTask;

public class PokeAPIPokemonRepository implements PokeAPIPokemonAsyncTask.Callback{

    private static final String TAG = PokeAPITypesRepository.class.getSimpleName();
    private MutableLiveData<PokeAPIPokemonSearchReturn> mPokemonSearchResult;
    private MutableLiveData<Status> mLoadingStatus;
    private String mPokemonCurrentQuery;

    public PokeAPIPokemonRepository() {
        mPokemonSearchResult = new MutableLiveData<>();
        mPokemonSearchResult.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mPokemonCurrentQuery = null;
    }

    public LiveData<PokeAPIPokemonSearchReturn> getPokemonSearchResult() {
        return mPokemonSearchResult;
    }

    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadPokemonSearchResult(String url) {
        if (loadPokemonSearchResults(url)) {
            mPokemonCurrentQuery = url;
            mPokemonSearchResult.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            Log.d(TAG, "executing search with url: " + url);
            new PokeAPIPokemonAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached results");
        }
    }

    private boolean loadPokemonSearchResults(String query) {
        return !TextUtils.equals(query, mPokemonCurrentQuery);
    }

    @Override
    public void onSearchFinished(PokeAPIPokemonSearchReturn searchResults) {
        mPokemonSearchResult.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
