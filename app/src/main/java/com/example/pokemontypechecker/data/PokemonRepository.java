package com.example.pokemontypechecker.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PokemonRepository {
    private PokemonDao mPokemonDao;

    public PokemonRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPokemonDao = db.pokemonDao();
    }

    public void insertPokemon(Pokemon pokemon) {
        new InsertAsyncTask(mPokemonDao).execute(pokemon);
    }

    public void deletePokemon(Pokemon pokemon) {
        new DeleteAsyncTask(mPokemonDao).execute(pokemon);
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return mPokemonDao.getAllPokemon();
    }

    public LiveData<Pokemon> getPokemonByName(String name) {
        return mPokemonDao.getPokemonByName(name);
    }

    private static class InsertAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao mAsyncTaskDao;
        InsertAsyncTask(PokemonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemon) {
            mAsyncTaskDao.insert(pokemon[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao mAsyncTaskDao;
        DeleteAsyncTask(PokemonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemon) {
            mAsyncTaskDao.delete(pokemon[0]);
            return null;
        }
    }
}
