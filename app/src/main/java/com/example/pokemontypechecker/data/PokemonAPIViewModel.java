package com.example.pokemontypechecker.data;




import android.arch.lifecycle.LiveData;
        import android.arch.lifecycle.ViewModel;


import com.example.pokemontypechecker.utils.PokeAPIUtils;

public class PokemonAPIViewModel extends ViewModel{

    private LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> mSearchResults;
    private LiveData<Status> mLoadingStatus;
    private PokemonAPIRepository mRepository;

    public PokemonAPIViewModel() {
        mRepository = new PokemonAPIRepository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadTypesSearchResults(String query) {
        mRepository.loadAllTypesSearchResults(query);
    }

}