package com.example.pokemontypechecker.utils;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.gson.Gson;

        import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


class PokeType{


}

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


    // Constrains a PokemonType to be one of the ints above
    @IntDef({NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG,
            GHOST, STEEL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE,
            DRAGON, DARK, FAIRY, UNKNOWN, SHADOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PokemonType{

    }

    /*
     * This class is used as a final representation of a single forecast item.  It condenses the
     * classes below that are used for parsing the OWN JSON response with Gson.
     */

    public static class GeneralAPIResult implements Serializable {
        public String name;
        public String url;
    }

    public static class GeneralTypeReturn implements Serializable {
        public String name;
        public GeneralAPIResult[] moves;
        public GeneralAPIResult[] pokemon;
    }

    public static class GeneralSearchReturn implements Serializable {
        public int count;
        public GeneralAPIResult[] results;
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
    public static String buildURL(@PokemonType int type)
    {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .appendPath(String.valueOf(type))
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }



    public static GeneralSearchReturn parseGeneralSearchJSON(String searchJSON) {
        Gson gson = new Gson();

        GeneralSearchReturn results = gson.fromJson(searchJSON, GeneralSearchReturn.class);

        Log.d(TAG, "The count of the results is: " + String.valueOf(results.count));

        return results;
    }

    public static GeneralTypeReturn parseTypeSearchJSON(String typeSearchJSON) {
        Gson gson = new Gson();

        GeneralTypeReturn results = gson.fromJson(typeSearchJSON, GeneralTypeReturn.class);

        Log.d(TAG, "The name of the type is: " + results.name);

        return results;
    }


}