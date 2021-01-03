package com.example.star_wars.presentation.viewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.star_wars.data.repository.CharacterDisplayRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final CharacterDisplayRepository characterDisplayRepository;

    public ViewModelFactory(CharacterDisplayRepository characterDisplayRepository) {
        this.characterDisplayRepository = characterDisplayRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CharacterViewModel.class)) {
            return (T) new CharacterViewModel(characterDisplayRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
