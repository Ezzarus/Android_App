package com.example.star_wars.presentation.characterDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.star_wars.R;
import com.example.star_wars.data.di.FakeDependencyInjection;
import com.example.star_wars.presentation.viewModel.CharacterViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Fragment permttant d'afficher un Character
 */
public class CharacterFragment extends Fragment implements CharacterActionInterface {

    public static final String TAB_NAME = "Characters";
    private View rootView;
    private RecyclerView recyclerView;
    private CharacterAdapter characterAdapter_lign, characterAdapter_grid;
    private CharacterViewModel characterViewModel;
    private boolean asList;

    private CharacterFragment(){

    }

    @NotNull
    @Contract(" -> new")
    public static CharacterFragment newInstance() {
        return new CharacterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.character_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        registerViewModels();
    }

    private void registerViewModels() {
        characterViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(CharacterViewModel.class);
        characterViewModel.getAllCharacters();
        characterViewModel.getCharacters().observe(getViewLifecycleOwner(), characterItemViewModelList -> characterAdapter_grid.bindViewModels(characterItemViewModelList));
        characterViewModel.getCharacters().observe(getViewLifecycleOwner(), characterItemViewModelList -> characterAdapter_lign.bindViewModels(characterItemViewModelList));
    }

    private void setupRecyclerView() {
        final RecyclerView.LayoutManager layoutManager_lign = new LinearLayoutManager(getContext());
        final RecyclerView.LayoutManager layoutManager_grid = new GridLayoutManager(getContext(),3);

        FloatingActionButton button = rootView.findViewById(R.id.button);

        this.characterAdapter_lign = new CharacterAdapter(this, true);
        this.characterAdapter_grid = new CharacterAdapter(this, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(layoutManager_grid);

        button.setOnClickListener(v -> {
            if(!asList){
                button.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_grid_on_24));
                asList = true;
                recyclerView.setLayoutManager(layoutManager_lign);
                recyclerView.setAdapter(characterAdapter_lign);
            } else {
                button.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_list_24));
                asList = false;
                recyclerView.setLayoutManager(layoutManager_grid);
                recyclerView.setAdapter(characterAdapter_grid);
            }

        });

        recyclerView.setAdapter(characterAdapter_grid);

    }

    @Override
    public void onCharacterClicked(int characterId) {
        Intent i = new Intent(getActivity(), CharacterActivity.class);
        i.putExtra("CharacterId", characterId);
        characterViewModel.getCharacterById(characterId);
        startActivity(i);
    }
}