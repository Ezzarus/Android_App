package com.example.star_wars.presentation.characterDisplay.mapper;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 *  Mapper tranformant un Character en CharacterViewItem afin d'afficher le nom et l'image d'un Character recu via l'API
 */
public class CharacterToCharacterViewItemMapper {

    public CharacterViewItem map(Character c){
        CharacterViewItem cvi = new CharacterViewItem();
        cvi.setCharacterId(c.getId());
        cvi.setImage(c.getImage());
        cvi.setName(c.getName());
        return cvi;
    }

    public List<CharacterViewItem> map(List<Character> lc){
        List<CharacterViewItem> lcvi = new ArrayList<>();
        if(lc != null) {
            for (Character c : lc) {
                lcvi.add(map(c));
            }
        }
        return lcvi;
    }
}
