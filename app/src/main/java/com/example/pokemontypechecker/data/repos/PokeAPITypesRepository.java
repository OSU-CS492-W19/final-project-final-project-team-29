package com.example.pokemontypechecker.data.repos;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.api_models.PokeAPIGeneralTypeSearchReturn;
import com.example.pokemontypechecker.data.async.PokeAPITypesAsyncTask;
import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.util.List;

public class PokeAPITypesRepository implements PokeAPITypesAsyncTask.Callback {

    private static final String TAG = PokeAPITypesRepository.class.getSimpleName();
    private MutableLiveData<List<NameUrlPair>> mTypesSearchResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mTypesCurrentQuery;


    public PokeAPITypesRepository() {
        mTypesSearchResults = new MutableLiveData<>();
        mTypesSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mTypesCurrentQuery = null;
    }

    public LiveData<List<NameUrlPair>> getTypesSearchResults() {
        return mTypesSearchResults;
    }


    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadAllTypesSearchResults(String query) {
        if (shouldExecuteAllTypesSearch(query)) {
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

    private boolean shouldExecuteAllTypesSearch(String query) {
        return !TextUtils.equals(query, mTypesCurrentQuery);
    }

    @Override
    public void onSearchFinished(List<NameUrlPair> searchResults) {
        mTypesSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }


}