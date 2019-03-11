package com.example.pokemontypechecker.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "pokemon")
public class Pokemon extends NameUrlPair implements Serializable {
    @NonNull
    @PrimaryKey
    public int id;
}
