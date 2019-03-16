package com.example.pokemontypechecker.data.api_models;


import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;

public class PokeAPITypeReturn implements Serializable {
    public String name;
    public ArrayList<NameUrlPair> moves;
    public ArrayList<PokeAPIPokemon> pokemon;
}
