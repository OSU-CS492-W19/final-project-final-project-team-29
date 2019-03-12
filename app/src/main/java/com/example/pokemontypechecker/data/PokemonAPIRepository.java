package com.example.pokemontypechecker.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.pokemontypechecker.data.async.PokeAPIPokemonAsyncTask;
import com.example.pokemontypechecker.data.async.PokeAPITypeAsyncTask;
import com.example.pokemontypechecker.data.async.PokeAPITypesAsyncTask;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

public class PokemonAPIRepository implements
        PokeAPITypesAsyncTask.Callback,
        PokeAPITypeAsyncTask.Callback,
        PokeAPIPokemonAsyncTask.Callback{
    private static final String TAG = PokemonAPIRepository.class.getSimpleName();

    private MutableLiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> mTypesSearchResults;
    private MutableLiveData<PokeAPIUtils.PokeApiTypeReturn> mTypeSearchResults;
    private MutableLiveData<PokeAPIUtils.PokeApiPokemonSearchReturn> mPokemonSearchResult;
    private MutableLiveData<Status> mLoadingStatus;

    private String mTypesCurrentQuery;
    private String mTypeCurrentQuery;
    private String mPokemonCurrentQuery;

    public PokemonAPIRepository() {
        mTypesSearchResults = new MutableLiveData<>();
        mTypesSearchResults.setValue(null);

        mTypeSearchResults = new MutableLiveData<>();
        mTypeSearchResults.setValue(null);

        mPokemonSearchResult = new MutableLiveData<>();
        mPokemonSearchResult.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mTypesCurrentQuery = null;
    }

    public LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> getTypesSearchResults() {
        return mTypesSearchResults;
    }

    public LiveData<PokeAPIUtils.PokeApiTypeReturn> getTypeSearchResults() {
        return mTypeSearchResults;
    }

    public LiveData<PokeAPIUtils.PokeApiPokemonSearchReturn> getPokemonSearchResult() {
        return mPokemonSearchResult;
    }

    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadAllTypesSearchResults(String query) {
        if (shouldExecuteSearch(query)) {
            mTypesCurrentQuery = query;
            mTypesSearchResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = PokeAPIUtils.buildURL();
            Log.d(TAG, "executing search with url: " + url);
            new PokeAPITypesAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached results");
        }
    }

    public void loadTypeSearchResults(String url) {
        if (shouldExecuteSearch(url)) {
            mTypeCurrentQuery = url;
            mTypeSearchResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            Log.d(TAG, "executing search with url: " + url);
            new PokeAPITypeAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached results");
        }
    }

    public void loadPokemonSearchResult(String url) {
        if (shouldExecuteSearch(url)) {
            mPokemonCurrentQuery = url;
            mPokemonSearchResult.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            Log.d(TAG, "executing search with url: " + url);
            new PokeAPIPokemonAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached results");
        }
    }

    private boolean shouldExecuteSearch(String query) {
    return !TextUtils.equals(query, mTypesCurrentQuery) && !TextUtils.equals(query, mTypeCurrentQuery) && !TextUtils.equals(query, mPokemonCurrentQuery);
    }

    @Override
    public void onSearchFinished(PokeAPIUtils.PokeApiGeneralTypeSearchReturn searchResults) {
        mTypesSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    @Override
    public void onSearchFinished(PokeAPIUtils.PokeApiTypeReturn searchResults) {
        mTypeSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }

    @Override
    public void onSearchFinished(PokeAPIUtils.PokeApiPokemonSearchReturn searchResults) {
        mPokemonSearchResult.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}