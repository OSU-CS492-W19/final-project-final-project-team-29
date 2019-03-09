package com.example.pokemontypechecker.data;


        import android.arch.lifecycle.LiveData;
        import android.arch.lifecycle.MutableLiveData;
        import android.text.TextUtils;
        import android.util.Log;

        import com.example.pokemontypechecker.utils.PokeAPIUtils;

public class PokemonSearchAllTypesRepository implements PokemonAllTypesAsyncTask.Callback {

    private MutableLiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentQuery;

    public PokemonSearchAllTypesRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mCurrentQuery = null;
    }

    public LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> getSearchResults() {
        return mSearchResults;
    }

    public MutableLiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadAllTypesSearchResults(String query) {
        if (shouldExecuteSearch(query)) {
            mCurrentQuery = query;
            mSearchResults.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = PokeAPIUtils.buildURL();
            Log.d("GitHubSearchRepository", "executing search with url: " + url);
            new PokemonAllTypesAsyncTask(url, this).execute();
        } else {
            Log.d("GitHubSearchRepository", "using cached results");
        }
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, mCurrentQuery);
    }

    @Override
    public void onSearchFinished(PokeAPIUtils.PokeApiGeneralTypeSearchReturn searchResults) {
        mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}