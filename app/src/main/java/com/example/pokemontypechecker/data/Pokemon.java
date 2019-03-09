package com.example.pokemontypechecker.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "pokemon")
public class Pokemon {
    @NonNull
    @PrimaryKey
    public int id;
    public String name;
    public String url;
}
