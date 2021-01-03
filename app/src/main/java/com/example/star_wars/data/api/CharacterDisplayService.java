package com.example.star_wars.data.api;

import com.example.star_wars.data.api.model.Character;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Entité permettant d'appeler les différents chemins accessibles dans l'API
 */
public interface CharacterDisplayService {

    @GET("all.json")
    Single<List<Character>> getAllCharacters();

    @GET("id/{id}.json")
    Single<Character> getCharacter(@Path("id") int id);


}
