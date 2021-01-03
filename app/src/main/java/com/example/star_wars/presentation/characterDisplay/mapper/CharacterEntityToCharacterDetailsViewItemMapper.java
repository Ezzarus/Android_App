package com.example.star_wars.presentation.characterDisplay.mapper;

import com.example.star_wars.data.entity.CharacterEntity;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterDetailsViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper tranformant un CharacterEntity en CharacterDetailsViewItem afin d'afficher les détails d'un Character
 */
public class CharacterEntityToCharacterDetailsViewItemMapper {

    public CharacterDetailsViewItem map(CharacterEntity c){
        CharacterDetailsViewItem cdvi = new CharacterDetailsViewItem();
        cdvi.setImage(c.getImage());
        cdvi.setName(c.getName());
        cdvi.setSpecies(c.getSpecies());
        cdvi.setBorn(c.getBorn());
        cdvi.setDied(c.getDied());

        return cdvi;
    }

    public List<CharacterDetailsViewItem> map(List<CharacterEntity> lc){
        List<CharacterDetailsViewItem> lcvi = new ArrayList<>();
        if(lc != null) {
            for (CharacterEntity c : lc) {
                lcvi.add(map(c));
            }
        }
        return lcvi;
    }
}
