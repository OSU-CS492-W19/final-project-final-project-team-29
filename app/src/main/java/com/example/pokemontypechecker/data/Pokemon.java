package com.example.pokemontypechecker.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "pokemon")
public class Pokemon extends NameUrlPair {
    @NonNull
    @PrimaryKey
    public int id;
}
