package com.example.pokemontypechecker.utils;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.gson.Gson;

        import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.TimeZone;


class PokeType{


}

public class PokeAPIUtils {

    private static final String TAG = PokeAPIUtils.class.getSimpleName();


    public final static String POKE_BASE_URL = "https://pokeapi.co/api/v2/";
    public final static String POKE_SEARCH_POKEMON = "pokemon";
    public final static String POKE_SEARCH_TYPE = "type";


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
    public static class ForecastItem implements Serializable {
        public Date dateTime;
        public String description;
        public String icon;
        public long temperature;
        public long temperatureLow;
        public long temperatureHigh;
        public long humidity;
        public long windSpeed;
        public String windDirection;
    }



    public static String buildURL() {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }

    public static String buildURL(@PokemonType int type)
    {
        Uri uri =  Uri.parse(POKE_BASE_URL).buildUpon()
                .appendPath(POKE_SEARCH_TYPE)
                .appendPath(String.valueOf(type))
                .build();

        Log.d(TAG, "Built the URL: " + uri.toString());

        return uri.toString();
    }



    public static ArrayList<ForecastItem> parseForecastJSON(String forecastJSON) {
        Gson gson = new Gson();
        return null;
    }


}