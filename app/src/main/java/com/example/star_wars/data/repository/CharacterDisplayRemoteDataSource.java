package com.example.star_wars.data.repository;

import com.example.star_wars.data.api.CharacterDisplayService;
import com.example.star_wars.data.api.model.Character;

import java.util.List;

import io.reactivex.Single;

/**
 * Repository gérant les appels d'API
 */
public class CharacterDisplayRemoteDataSource {

    private CharacterDisplayService characterDisplayService;

    public CharacterDisplayRemoteDataSource(CharacterDisplayService characterDisplayService){
        this.characterDisplayService = characterDisplayService;
    }

    public Single<List<Character>> getAllCharacters(){
        return this.characterDisplayService.getAllCharacters();
    }

    public Single<Character> getCharacter(int id){
        return this.characterDisplayService.getCharacter(id);
    }

}
