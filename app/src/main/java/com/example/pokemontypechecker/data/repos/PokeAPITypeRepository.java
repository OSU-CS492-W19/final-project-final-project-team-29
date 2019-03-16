package com.example.pokemontypechecker.data.repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.api_models.PokeAPITypeReturn;
import com.example.pokemontypechecker.data.async.PokeAPITypeAsyncTask;

import java.util.List;


public class PokeAPITypeRepository implements PokeAPITypeAsyncTask.Callback {

    private static final String TAG = PokeAPITypesRepository.class.getSimpleName();

    private MutableLiveData<List<NameUrlPair>> mTypeSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mTypeCurrentQuery;

    public PokeAPITypeRepository() {
        mTypeSearchResults = new MutableLiveData<>();
        mTypeSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mTypeCurrentQuery = null;
    }

    public LiveData<List<NameUrlPair>> getTypeSearchResults() {
        return mTypeSearchResults;
    }


    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }


    public void loadTypeSearchResults(String url) {
        if (shouldExecuteTypeSearch(url)) {
            mTypeCurrentQuery = url;
            mTypeSearchResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            Log.d(TAG, "executing search with url: " + url);
            new PokeAPITypeAsyncTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached results");
        }
    }


    private boolean shouldExecuteTypeSearch(String query) {
        return !TextUtils.equals(query, mTypeCurrentQuery);
    }


    @Override
    public void onSearchFinished(List<NameUrlPair> searchResults) {
        mTypeSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
