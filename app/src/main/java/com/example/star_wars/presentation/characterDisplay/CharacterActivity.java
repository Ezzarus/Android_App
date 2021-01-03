package com.example.star_wars.presentation.characterDisplay;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.star_wars.data.di.FakeDependencyInjection;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterDetailsViewItem;
import com.example.star_wars.presentation.viewModel.CharacterViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.star_wars.R;

import java.util.List;

/**
 * Activité affichant les détails d'un personnage
 */
public class CharacterActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView name;
    private TextView species;
    private TextView birth;
    private TextView death;
    private int characterId;
    private CharacterViewModel characterViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Character Details");

        Intent i = getIntent();
        this.characterId = i.getIntExtra("CharacterId", 1);

        registerViewModel();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void registerViewModel() {
        characterViewModel = new ViewModelProvider(this, FakeDependencyInjection.getViewModelFactory()).get(CharacterViewModel.class);
        characterViewModel.getCharacter().observe(this, new Observer<List<CharacterDetailsViewItem>>() {
            @Override
            public void onChanged(List<CharacterDetailsViewItem> characterDetailsViewItemList) {
                setLayout(characterDetailsViewItemList.get(0));
            }
        });
        characterViewModel.getCharacterById(characterId);

    }

    private void setLayout(CharacterDetailsViewItem characterToDisplay) {

        imageView = findViewById(R.id.character_image);
        name = findViewById(R.id.character_name);
        species = findViewById(R.id.character_species);
        birth = findViewById(R.id.character_birth);
        death = findViewById(R.id.character_death);

        name.append(" " + characterToDisplay.getName());

        if (characterToDisplay.getSpecies() == null) {
            species.append(" Unknown");
        } else {
            species.append(" " + characterToDisplay.getSpecies());
        }
        if (characterToDisplay.getBorn() == null) {
            birth.append(" Unknown");
        } else {
            birth.append(" " + characterToDisplay.getBorn());
        }
        if (characterToDisplay.getDied() == null) {
            death.append(" Unknown");
        } else {
            death.append(" " + characterToDisplay.getDied());
        }
        Glide.with(this)
                .load(characterToDisplay.getImage())
                .into(imageView);
    }
}