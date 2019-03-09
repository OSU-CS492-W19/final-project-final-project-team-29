package com.example.pokemontypechecker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.PokemonRepository;

import java.util.List;

public class PokemonDBViewModel extends AndroidViewModel {
    private PokemonRepository mPokemonRepository;

    public PokemonDBViewModel(Application application) {
        super(application);
        mPokemonRepository = new PokemonRepository(application);
    }

    public void insertPokemon(Pokemon pokemon) {
        mPokemonRepository.insertPokemon(pokemon);
    }

    public void deletePokemon(Pokemon pokemon) {
        mPokemonRepository.deletePokemon(pokemon);
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return mPokemonRepository.getAllPokemon();
    }

    public LiveData<Pokemon> getPokemonByName(String name) {
        return mPokemonRepository.getPokemonByName(name);
    }

}
