package com.example.pokemontypechecker.utils;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.pokemontypechecker.data.NameUrlPair;
import com.example.pokemontypechecker.data.Pokemon;
import com.example.pokemontypechecker.data.PokemonDetail;
import com.example.pokemontypechecker.data.PokemonType;
import com.google.gson.Gson;

        import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class PokeAPIUtils {

    private static final String TAG = PokeAPIUtils.class.getSimpleName();


    public final static String POKE_BASE_URL = "https://pokeapi.co/api/v2/";
    public final static String POKE_SEARCH_POKEMON = "pokemon";
    public final static String POKE_SEARCH_TYPE = "type";


    // All of the possible Pokemon Types
    public static final int NORMAL = 1;
    public static final int FIGHTING = 2;
    public static final int FLYING = 3;
    public static final int POISON = 4;
    public static final int GROUND = 5;
    public static final int ROCK = 6;
    public static final int BUG = 7;
    public static final int GHOST = 8;
    public static final int STEEL = 9;
    public static final int FIRE = 10;
    public static final int WATER = 11;
    public static final int GRASS = 12;
    public static final int ELECTRIC = 13;
    public static final int PSYCHIC = 14;
    public static final int ICE = 15;
    public static final int DRAGON = 16;
    public static final int DARK = 17;
    public static final int FAIRY = 18;
    public static final int UNKNOWN = 10001;
    public static final int SHADOW = 10002;


    // Constrains a PokemonEnumType to be one of the ints above
    @IntDef({NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG,
            GHOST, STEEL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE,
            DRAGON, DARK, FAIRY, UNKNOWN, SHADOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PokemonEnumType {

    }

    /*
     * These are the classes used to parse the PokeAPI JSON
     */


    public static class PokeApiMove implements Serializable{
        public NameUrlPair move;
    }

    public static class PokeApiType implements Serializable{
        public NameUrlPair type;
    }

    public static class PokeApiPokemon implements Serializable{
        public NameUrlPair pokemon;
    }

    public static class PokeApiTypeReturn implements Serializable {
        public String name;
        public NameUrlPair[] moves;
        public PokeApiPokemon[] pokemon;
    }

    public static class PokeApiPokemonSearchReturn implements Serializable{
        public String name;
        public PokeApiType[] types;
        public PokeApiMove[] moves;
    }

    public static class PokeApiGeneralTypeSearchReturn implements Serializable {
        public int count;
        public NameUrlPair[] results;
    }


    // Builds a URL for a search for all of the pokemon types
    public static String buildURL() {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }

    // Builds a URL for a search for the given type
    public static String buildURL(@PokemonEnumType int type)
    {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .appendPath(String.valueOf(type))
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }



    public static PokemonDetail parsePokemonSearchJSON(String searchJSON) {
        Gson gson = new Gson();

        PokeApiPokemonSearchReturn results = gson.fromJson(searchJSON, PokeApiPokemonSearchReturn.class);

        Log.d(TAG, "The count of the results is: " + results.name);

        PokemonDetail fullPokemon = new PokemonDetail();
        fullPokemon.name = results.name;
        //fullPokemon.moves = results.moves


        return fullPokemon;
    }

    public static PokeApiGeneralTypeSearchReturn parseGeneralTypeSearchJSON(String searchJSON) {
        Gson gson = new Gson();

        PokeApiGeneralTypeSearchReturn results = gson.fromJson(searchJSON, PokeApiGeneralTypeSearchReturn.class);

        return results;
    }

    public static Pokemon[] parseTypeSearchJSON(String typeSearchJSON) {
        Gson gson = new Gson();

        PokeApiTypeReturn results = gson.fromJson(typeSearchJSON, PokeApiTypeReturn.class);

        Log.d(TAG, "The name of the type is: " + results.name);

        Pokemon[] returnResult = new Pokemon[results.pokemon.length];
        for (int i = 0; i < results.pokemon.length; i++) {
            returnResult[i] = (Pokemon)results.pokemon[i].pokemon;
        }

        return returnResult;
    }


}