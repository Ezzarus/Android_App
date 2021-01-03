package com.example.star_wars.data.repository;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.data.entity.CharacterEntity;

/**
 * Mapper transformant un Character en CharacterEntity afin de le stocker en base de données
 */
public class CharacterToCharacterEntityMapper {

    public CharacterEntity map(Character character){
        CharacterEntity characterEntity = new CharacterEntity();

        characterEntity.setId(character.getId());
        characterEntity.setImage(character.getImage());
        characterEntity.setName(character.getName());
        characterEntity.setSpecies(character.getSpecies());
        characterEntity.setBorn(character.getBorn());
        characterEntity.setDied(character.getDied());

        return characterEntity;
    }
}
