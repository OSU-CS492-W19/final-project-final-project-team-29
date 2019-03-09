package com.example.pokemontypechecker.data;




import android.arch.lifecycle.LiveData;
        import android.arch.lifecycle.ViewModel;


import com.example.pokemontypechecker.utils.PokeAPIUtils;

import java.util.List;

public class PokemonAllTypesViewModel extends ViewModel{

    private LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> mSearchResults;
    private LiveData<Status> mLoadingStatus;
    private PokemonSearchAllTypesRepository mRepository;

    public PokemonAllTypesViewModel() {
        mRepository = new PokemonSearchAllTypesRepository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadSearchResults(String query) {
        mRepository.loadAllTypesSearchResults(query);
    }
}