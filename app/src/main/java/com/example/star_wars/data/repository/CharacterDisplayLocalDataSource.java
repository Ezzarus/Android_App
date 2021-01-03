package com.example.star_wars.data.repository;

import com.example.star_wars.data.db.CharacterDatabase;
import com.example.star_wars.data.entity.CharacterEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Repository gérant les appels en base de données
 */
public class CharacterDisplayLocalDataSource {

    private CharacterDatabase characterDatabase;

    public CharacterDisplayLocalDataSource(CharacterDatabase characterDatabase){
        this.characterDatabase = characterDatabase;
    }

    public Flowable<List<CharacterEntity>> getCharacters(){
        return characterDatabase.characterDao().getCharacters();
    }

    public Flowable<List<CharacterEntity>> getACharacter(int id){
        return characterDatabase.characterDao().getACharacter(id);
    }

    public Completable addCharacter(CharacterEntity entity){
        return characterDatabase.characterDao().addCharacter(entity);
    }

    public Completable removeCharacter(int id){
        return characterDatabase.characterDao().deleteCharacter(id);
    }

}
