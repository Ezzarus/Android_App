package com.example.star_wars.data.db;

import android.content.Context;

import androidx.room.Room;

public class CharacterDatabaseCreator {
    private static CharacterDatabase myCharacterDatabase;

    public static CharacterDatabase database(Context context){

        if(myCharacterDatabase == null){
            myCharacterDatabase = Room.databaseBuilder(context, CharacterDatabase.class, "CharacterDatabase").build();
        }

        return myCharacterDatabase;
    }
}
