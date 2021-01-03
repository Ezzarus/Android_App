package com.example.star_wars.data.repository;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.data.entity.CharacterEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Interface permettant la manipulation des Character
 */
public interface CharacterDisplayRepository {

    Single<List<Character>> getAllCharacters();

    Single<Character> getCharacter(int id);

    Flowable<List<CharacterEntity>> getCharacters();

    Flowable<List<CharacterEntity>> getACharacter(int id);

    Completable addCharacter(int id);

    Completable removeCharacter(int id);
}
