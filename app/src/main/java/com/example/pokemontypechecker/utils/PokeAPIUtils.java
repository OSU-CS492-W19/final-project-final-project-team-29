package com.example.pokemontypechecker.utils;

import android.net.Uri;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.pokemontypechecker.data.Pokemon;
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


public class PokeAPIUtils {

    private static final String TAG = PokeAPIUtils.class.getSimpleName();


    public final static String POKE_BASE_URL = "https://pokeapi.co/api/v2/";
    public final static String POKE_SEARCH_TYPE = "type";
    public final static int TypeCount = 18;


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

    public static String parseForPokemonIdFromUrl (NameUrlPair pair) {
        String[] strs = pair.url.split("/");
        return strs[strs.length - 1];
    }

    // Because we have two of the same types and I don't wanna do any refactoring.
    public static String parseForPokemonIdFromUrl (Pokemon pair) {
        String[] strs = pair.url.split("/");
        return strs[strs.length - 1];
    }

    private static double[] getAttackVector(@PokemonEnumType int type)
    {
        double[] attackVector = new double[TypeCount + 1];
        switch (type){
            case NORMAL:
                attackVector[0] = NORMAL;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 0.5;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 0;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case FIGHTING:
                attackVector[0] = FIGHTING;
                attackVector[NORMAL] = 2;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 0.5;
                attackVector[POISON] = 0.5;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 2;
                attackVector[BUG] = 0.5;
                attackVector[GHOST] = 0;
                attackVector[STEEL] = 2;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 0.5;
                attackVector[ICE] = 2;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 2;
                attackVector[FAIRY] = 0.5;
                break;
            case FLYING:
                attackVector[0] = FLYING;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 2;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 0.5;
                attackVector[BUG] = 2;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 2;
                attackVector[ELECTRIC] = 0.5;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case POISON:
                attackVector[0] = POISON;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 0.5;
                attackVector[GROUND] = 0.5;
                attackVector[ROCK] = 0.5;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 0.5;
                attackVector[STEEL] = 0;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 2;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 2;
                break;
            case GROUND:
                attackVector[0] = GROUND;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 0;
                attackVector[POISON] = 2;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 2;
                attackVector[BUG] = 0.5;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 2;
                attackVector[FIRE] = 2;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 0.5;
                attackVector[ELECTRIC] = 2;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case ROCK:
                attackVector[0] = ROCK;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 0.5;
                attackVector[FLYING] = 2;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 0.5;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 2;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 2;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 2;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case BUG:
                attackVector[0] = BUG;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 0.5;
                attackVector[FLYING] = 0.5;
                attackVector[POISON] = 0.5;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 0.5;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 2;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 2;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 2;
                attackVector[FAIRY] = 0.5;
                break;
            case GHOST:
                attackVector[0] = GHOST;
                attackVector[NORMAL] = 0;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 2;
                attackVector[STEEL] = 1;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 2;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 0.5;
                attackVector[FAIRY] = 1;
                break;
            case STEEL:
                attackVector[0] = STEEL;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 2;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 0.5;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 0.5;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 2;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 2;
                break;
            case FIRE:
                attackVector[0] = FIRE;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 0.5;
                attackVector[BUG] = 2;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 2;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 0.5;
                attackVector[GRASS] = 2;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 2;
                attackVector[DRAGON] = 0.5;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case WATER:
                attackVector[0] = WATER;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 2;
                attackVector[ROCK] = 2;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 1;
                attackVector[FIRE] = 2;
                attackVector[WATER] = 0.5;
                attackVector[GRASS] = 0.5;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 0.5;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case GRASS:
                attackVector[0] = GRASS;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 0.5;
                attackVector[POISON] = 0.5;
                attackVector[GROUND] = 2;
                attackVector[ROCK] = 2;
                attackVector[BUG] = 0.5;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 2;
                attackVector[GRASS] = 0.5;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 0.5;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case ELECTRIC:
                attackVector[0] = ELECTRIC;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 2;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 0;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 1;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 2;
                attackVector[GRASS] = 0.5;
                attackVector[ELECTRIC] = 0.5;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 0.5;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case PSYCHIC:
                attackVector[0] = PSYCHIC;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 2;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 2;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 0.5;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 0;
                attackVector[FAIRY] = 1;
                break;
            case ICE:
                attackVector[0] = ICE;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 2;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 2;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 0.5;
                attackVector[GRASS] = 2;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 0.5;
                attackVector[DRAGON] = 2;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
            case DRAGON:
                attackVector[0] = DRAGON;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 2;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 0;
                break;
            case DARK:
                attackVector[0] = DARK;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 0.5;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 2;
                attackVector[STEEL] = 1;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 2;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 0.5;
                attackVector[FAIRY] = 0.5;
                break;
            case FAIRY:
                attackVector[0] = FAIRY;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 2;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 0.5;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 0.5;
                attackVector[FIRE] = 0.5;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 2;
                attackVector[DARK] = 2;
                attackVector[FAIRY] = 1;
                break;
            default:
                attackVector[0] = 0;
                attackVector[NORMAL] = 1;
                attackVector[FIGHTING] = 1;
                attackVector[FLYING] = 1;
                attackVector[POISON] = 1;
                attackVector[GROUND] = 1;
                attackVector[ROCK] = 1;
                attackVector[BUG] = 1;
                attackVector[GHOST] = 1;
                attackVector[STEEL] = 1;
                attackVector[FIRE] = 1;
                attackVector[WATER] = 1;
                attackVector[GRASS] = 1;
                attackVector[ELECTRIC] = 1;
                attackVector[PSYCHIC] = 1;
                attackVector[ICE] = 1;
                attackVector[DRAGON] = 1;
                attackVector[DARK] = 1;
                attackVector[FAIRY] = 1;
                break;
        }

        return attackVector;
    }

    private static double getDamageModifier(@PokemonEnumType int attackerType, @PokemonEnumType int defenderType)
    {
        return getAttackVector(attackerType)[defenderType];
    }

    public static int typeStringToEnum(String typeString){
        switch (typeString){
            case "normal":
                return NORMAL;
            case "fighting":
                return FIGHTING;
            case "flying":
                return FLYING;
            case "poison":
                return POISON;
            case "ground":
                return GROUND;
            case "rock":
                return ROCK;
            case "bug":
                return BUG;
            case "ghost":
                return GHOST;
            case "steel":
                return STEEL;
            case "fire":
                return FIRE;
            case "water":
                return WATER;
            case "grass":
                return GRASS;
            case "electric":
                return ELECTRIC;
            case "psychic":
                return PSYCHIC;
            case "ice":
                return ICE;
            case "dragon":
                return DRAGON;
            case "dark":
                return DARK;
            case "fairy":
                return FAIRY;
            default:
                return UNKNOWN;

        }
    }

    public static String typeEnumToString(int typeEnum){
        switch (typeEnum){
            case 1:
                return "normal";
            case 2:
                return "fighting";
            case 3:
                return "flying";
            case 4:
                return "poison";
            case 5:
                return "ground";
            case 6:
                return "rock";
            case 7:
                return "bug";
            case 8:
                return "ghost";
            case 9:
                return "steel";
            case 10:
                return "fire";
            case 11:
                return "water";
            case 12:
                return "grass";
            case 13:
                return "electric";
            case 14:
                return "psychic";
            case 15:
                return "ice";
            case 16:
                return "dragon";
            case 17:
                return "dark";
            case 18:
                return "fairy";
            default:
                return "";
        }
    }

    public static int[] typeStringToEnum(PokeAPIType[] strings)
    {
        int[] enums = new int[strings.length];

        for (int i = 0; i < strings.length; i++){
            enums[i] = typeStringToEnum(strings[i].type.name);
        }
        return enums;
    }

    public static double[] getTypeDamageModifiers(@PokemonEnumType int ... types)
    {

        double[] modifiers = new double[TypeCount + 1];
        for (int i = 0; i < modifiers.length; i++)
        {
            modifiers[i] = 1;
        }


        for (@PokemonEnumType int type: types) {
            for (int i = 0; i < modifiers.length; i++)
            {
                modifiers[i] = modifiers[i] * getDamageModifier(type, i);
            }
        }
        return modifiers;
    }

}