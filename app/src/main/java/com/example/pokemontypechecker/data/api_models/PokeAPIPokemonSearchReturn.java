package com.example.pokemontypechecker.data.api_models;

import java.io.Serializable;

public class PokeAPIPokemonSearchReturn extends NameUrlPair implements Serializable {
    public PokeAPIType[] types;
}