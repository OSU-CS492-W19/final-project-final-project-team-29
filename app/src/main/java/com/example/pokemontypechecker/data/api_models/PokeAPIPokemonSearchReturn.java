package com.example.pokemontypechecker.data.api_models;

import java.io.Serializable;

public class PokeAPIPokemonSearchReturn implements Serializable {
    public String name;
    public PokeAPIType[] types;
    public PokeAPIMove[] moves;
}