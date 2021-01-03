package com.example.star_wars.presentation.characterDisplay.mapper;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterDetailsViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper tranformant un Character en CharacterDetailsViewItem afin d'afficher les détails d'un Character recus via l'API
 */
public class CharacterToCharacterDetailsViewItemMapper {

    public CharacterDetailsViewItem map(Character c){
        CharacterDetailsViewItem cdvi = new CharacterDetailsViewItem();
        cdvi.setImage(c.getImage());
        cdvi.setName(c.getName());
        cdvi.setSpecies(c.getSpecies());
        cdvi.setBorn(c.getBorn());
        cdvi.setDied(c.getDied());

        return cdvi;
    }

    public List<CharacterDetailsViewItem> map(List<Character> lc){
        List<CharacterDetailsViewItem> lcvi = new ArrayList<>();
        if(lc != null) {
            for (Character c : lc) {
                lcvi.add(map(c));
            }
        }
        return lcvi;
    }
}
