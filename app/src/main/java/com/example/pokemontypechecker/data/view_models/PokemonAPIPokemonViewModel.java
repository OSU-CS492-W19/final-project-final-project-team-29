package com.example.pokemontypechecker.data.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pokemontypechecker.data.Status;
import com.example.pokemontypechecker.data.api_models.PokeAPIPokemonSearchReturn;
import com.example.pokemontypechecker.data.repos.PokeAPIPokemonRepository;

public class PokemonAPIPokemonViewModel extends ViewModel {
    private LiveData<PokeAPIPokemonSearchReturn> mPokemonSearchResults;
    private LiveData<Status> mLoadingStatus;
    private PokeAPIPokemonRepository mRepository;

    public PokemonAPIPokemonViewModel() {
        mRepository = new PokeAPIPokemonRepository();
        mPokemonSearchResults = mRepository.getPokemonSearchResult();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<PokeAPIPokemonSearchReturn> getPokemonSearchResult() {
        return mPokemonSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadPokemonSearchResults(String query) {
        mRepository.loadPokemonSearchResult(query);
    }
}
