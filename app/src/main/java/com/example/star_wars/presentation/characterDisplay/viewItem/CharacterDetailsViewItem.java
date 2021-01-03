package com.example.star_wars.presentation.characterDisplay.viewItem;

/**
 * Classe permttant de spécifier les détails d'un Character à afficher
 */
public class CharacterDetailsViewItem {

    private String image;
    private String name;
    private String species;
    private String born;
    private String died;

    public CharacterDetailsViewItem() {
        this.image = "";
        this.name = "";
        this.species = "";
        this.born = "";
        this.died = "";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getDied() {
        return died;
    }

    public void setDied(String died) {
        this.died = died;
    }

    @Override
    public String toString() {
        return "CharacterDetailsViewItem{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", born='" + born + '\'' +
                ", died='" + died + '\'' +
                '}';
    }
}
