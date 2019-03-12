package com.example.pokemontypechecker.data;




import android.arch.lifecycle.LiveData;
        import android.arch.lifecycle.ViewModel;


import com.example.pokemontypechecker.utils.PokeAPIUtils;

public class PokemonAPIViewModel extends ViewModel{

    private LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> mTypesSearchResults;
    private LiveData<PokeAPIUtils.PokeApiTypeReturn> mTypeSearchResults;
    private LiveData<PokeAPIUtils.PokeApiPokemonSearchReturn> mPokemonSearchResult;
    private LiveData<Status> mLoadingStatus;
    private PokemonAPIRepository mRepository;

    public PokemonAPIViewModel() {
        mRepository = new PokemonAPIRepository();
        mTypesSearchResults = mRepository.getTypesSearchResults();
        mTypeSearchResults = mRepository.getTypeSearchResults();
        mPokemonSearchResult = mRepository.getPokemonSearchResult();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public LiveData<PokeAPIUtils.PokeApiGeneralTypeSearchReturn> getTypesSearchResults() {
        return mTypesSearchResults;
    }

    public LiveData<PokeAPIUtils.PokeApiTypeReturn> getTypeSearchResults() {
        return mTypeSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    public void loadTypesSearchResults(String query) {
        mRepository.loadAllTypesSearchResults(query);
    }

    public void loadTypeSearchResults(String url) {
        mRepository.loadTypeSearchResults(url);
    }

    public void loadPokemonSearchReult(String url) { mRepository.loadPokemonSearchResult(url);}

}