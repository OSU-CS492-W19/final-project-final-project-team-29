package com.example.pokemontypechecker.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {
    @Insert
    void insert(Pokemon pokemon);

    @Delete
    void delete(Pokemon pokemon);

    @Query("SELECT * FROM pokemon")
    LiveData<List<Pokemon>> getAllPokemon();

    @Query("SELECT * FROM pokemon WHERE name = :name LIMIT 1")
    LiveData<Pokemon> getPokemonByName(String name);
}
