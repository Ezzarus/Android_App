package com.example.star_wars.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.star_wars.data.entity.CharacterEntity;

/**
 * Entité permettant de spécifier les classes à stocker dans la base de données
 */
@Database(entities = {CharacterEntity.class}, version = 1)
public abstract class CharacterDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
