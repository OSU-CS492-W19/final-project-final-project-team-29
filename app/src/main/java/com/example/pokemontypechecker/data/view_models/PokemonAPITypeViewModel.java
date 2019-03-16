package com.example.pokemontypechecker.data.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.repos.PokeAPITypeRepository;

import java.util.List;

public class PokemonAPITypeViewModel extends ViewModel {
    private LiveData<List<NameUrlPair>> mTypeSearchResults;
    private PokeAPITypeRepository mRepository;
    private LiveData<Status> mLoadingStatus;

    public PokemonAPITypeViewModel() {
        mRepository = new PokeAPITypeRepository();
        mTypeSearchResults = mRepository.getTypeSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<List<NameUrlPair>> getTypeSearchResults() {
        return mTypeSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadTypeSearchResults(String url) {
        mRepository.loadTypeSearchResults(url);
    }

}
