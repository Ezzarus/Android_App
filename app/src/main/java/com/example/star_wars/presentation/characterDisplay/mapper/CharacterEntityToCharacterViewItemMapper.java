package com.example.star_wars.presentation.characterDisplay.mapper;

import com.example.star_wars.data.entity.CharacterEntity;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper transformant un CharacterEntity en CharacterViewItem afin d'afficher le nom et la photo d'un Character
 */
public class CharacterEntityToCharacterViewItemMapper {

    public CharacterViewItem map(CharacterEntity c){
        CharacterViewItem cvi = new CharacterViewItem();
        cvi.setImage(c.getImage());
        cvi.setName(c.getName());
        return cvi;
    }

    public List<CharacterViewItem> map(List<CharacterEntity> lc){
        List<CharacterViewItem> lcvi = new ArrayList<>();
        if(lc != null) {
            for (CharacterEntity c : lc) {
                lcvi.add(map(c));
            }
        }
        return lcvi;
    }
}
