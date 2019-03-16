package com.example.pokemontypechecker.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.pokemontypechecker.data.api_models.NameUrlPair;

import java.io.Serializable;

@Entity(tableName = "pokemon")
public class Pokemon implements Serializable {
    @NonNull
    @PrimaryKey
    public String name;

    public String url;

}
