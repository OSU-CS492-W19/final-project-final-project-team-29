package com.example.pokemontypechecker.data.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.repos.PokeAPITypesRepository;

import java.util.List;

public class PokemonAPITypesViewModel extends ViewModel{
    private LiveData<List<NameUrlPair>> mTypesSearchResults;
    private LiveData<Status> mLoadingStatus;
    private PokeAPITypesRepository mRepository;

    public PokemonAPITypesViewModel() {
        mRepository = new PokeAPITypesRepository();
        mTypesSearchResults = mRepository.getTypesSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<List<NameUrlPair>> getTypesSearchResults() {
        return mTypesSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadTypesSearchResults(String query) {
        mRepository.loadAllTypesSearchResults(query);
    }

}