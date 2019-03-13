package com.example.pokemontypechecker.utils;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.pokemontypechecker.data.api_models.NameUrlPair;
import com.example.pokemontypechecker.data.api_models.PokeAPIGeneralTypeSearchReturn;
import com.example.pokemontypechecker.data.api_models.PokeAPIPokemon;
import com.example.pokemontypechecker.data.api_models.PokeAPIPokemonSearchReturn;
import com.example.pokemontypechecker.data.api_models.PokeAPIType;
import com.example.pokemontypechecker.data.api_models.PokeAPITypeReturn;
import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


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


    // Builds a URL for a search for all of the pokemon types
    public static String buildURL() {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }


    public static PokeAPIPokemonSearchReturn parsePokemonSearchJSON(String searchJSON) {
        Gson gson = new Gson();

        PokeAPIPokemonSearchReturn results = gson.fromJson(searchJSON, PokeAPIPokemonSearchReturn.class);

        return results;
    }

    public static ArrayList<NameUrlPair> parseGeneralTypeSearchJSON(String searchJSON) {
        Gson gson = new Gson();

        PokeAPIGeneralTypeSearchReturn results = gson.fromJson(searchJSON, PokeAPIGeneralTypeSearchReturn.class);

        if (results != null && results.results != null) {
            return results.results;
        } else {
            return null;
        }
    }

    public static ArrayList<NameUrlPair>parseTypeSearchJSON(String typeSearchJSON) {
        Gson gson = new Gson();

        PokeAPITypeReturn result = gson.fromJson(typeSearchJSON, PokeAPITypeReturn.class);
        ArrayList<NameUrlPair> nameUrlPairList = new ArrayList<>();

        if (result != null && result.pokemon != null) {
            for(PokeAPIPokemon pokemon : result.pokemon) {
                nameUrlPairList.add(pokemon.pokemon);
            }
            return nameUrlPairList;
        } else {
            return null;
        }
    }

}