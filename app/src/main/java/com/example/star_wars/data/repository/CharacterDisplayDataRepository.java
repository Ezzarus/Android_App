package com.example.star_wars.data.repository;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.data.entity.CharacterEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Repository choisissant entre un appel en base de données ou un appel d'API
 */
public class CharacterDisplayDataRepository implements CharacterDisplayRepository{

    private CharacterDisplayRemoteDataSource characterDisplayRemoteDataSource;
    private CharacterDisplayLocalDataSource characterDisplayLocalDataSource;
    private CharacterToCharacterEntityMapper characterToCharacterEntityMapper;

    public CharacterDisplayDataRepository(CharacterDisplayRemoteDataSource characterDisplayRemoteDataSource, CharacterDisplayLocalDataSource characterDisplayLocalDataSource){
        this.characterDisplayRemoteDataSource = characterDisplayRemoteDataSource;
        this.characterDisplayLocalDataSource = characterDisplayLocalDataSource;
        this.characterToCharacterEntityMapper = new CharacterToCharacterEntityMapper();
    }

    @Override
    public Single<List<Character>> getAllCharacters() {
        return this.characterDisplayRemoteDataSource.getAllCharacters();
    }

    @Override
    public Single<Character> getCharacter(int id) {
        return this.characterDisplayRemoteDataSource.getCharacter(id);
    }

    @Override
    public Flowable<List<CharacterEntity>> getCharacters() {
        return this.characterDisplayLocalDataSource.getCharacters();
    }

    @Override
    public Flowable<List<CharacterEntity>> getACharacter(int id) {
        return this.characterDisplayLocalDataSource.getACharacter(id);
    }

    @Override
    public Completable addCharacter(int id) {
        return characterDisplayRemoteDataSource.getCharacter(id)
                .map(new Function<Character, CharacterEntity>() {
                    @Override
                    public CharacterEntity apply(@NonNull Character character) throws Exception {
                        return characterToCharacterEntityMapper.map(character);
                    }
                }).flatMapCompletable(new Function<CharacterEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NonNull CharacterEntity characterEntity) throws Exception {
                        return characterDisplayLocalDataSource.addCharacter(characterEntity);
                    }
                });
    }

    @Override
    public Completable removeCharacter(int id) {
        return characterDisplayLocalDataSource.removeCharacter(id);
    }

}
