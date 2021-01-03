package com.example.star_wars.presentation.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.star_wars.data.api.model.Character;
import com.example.star_wars.data.entity.CharacterEntity;
import com.example.star_wars.data.repository.CharacterDisplayRepository;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterDetailsViewItem;
import com.example.star_wars.presentation.characterDisplay.viewItem.CharacterViewItem;
import com.example.star_wars.presentation.characterDisplay.mapper.CharacterToCharacterDetailsViewItemMapper;
import com.example.star_wars.presentation.characterDisplay.mapper.CharacterEntityToCharacterDetailsViewItemMapper;
import com.example.star_wars.presentation.characterDisplay.mapper.CharacterEntityToCharacterViewItemMapper;
import com.example.star_wars.presentation.characterDisplay.mapper.CharacterToCharacterViewItemMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class CharacterViewModel extends ViewModel {

    private CharacterDisplayRepository characterDisplayRepository;
    private CompositeDisposable compositeDisposable;

    private CharacterToCharacterViewItemMapper characterToCharacterViewItemMapper;
    private CharacterToCharacterDetailsViewItemMapper characterToCharacterDetailsViewItemMapper;
    private CharacterEntityToCharacterViewItemMapper characterEntityToCharacterViewItemMapper;
    private CharacterEntityToCharacterDetailsViewItemMapper characterEntityToCharacterDetailsViewItemMapper;
    private MutableLiveData<List<CharacterViewItem>> characters;
    private MutableLiveData<List<CharacterDetailsViewItem>> character;

    public CharacterViewModel(CharacterDisplayRepository characterDisplayRepository){
        this.characterDisplayRepository = characterDisplayRepository;
        this.compositeDisposable = new CompositeDisposable();
        this.characterToCharacterViewItemMapper = new CharacterToCharacterViewItemMapper();
        this.characterToCharacterDetailsViewItemMapper = new CharacterToCharacterDetailsViewItemMapper();
        this.characterEntityToCharacterViewItemMapper = new CharacterEntityToCharacterViewItemMapper();
        this.characterEntityToCharacterDetailsViewItemMapper = new CharacterEntityToCharacterDetailsViewItemMapper();
        this.characters = new MutableLiveData<>();
        this.character = new MutableLiveData<>();
    }

    public MutableLiveData<List<CharacterViewItem>> getCharacters(){
        return characters;
    }
    public MutableLiveData<List<CharacterDetailsViewItem>> getCharacter(){
        return character;
    }

    public void getAllCharacters(){
        compositeDisposable.clear();
        compositeDisposable.add(characterDisplayRepository.getAllCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Character>>() {

                    @Override
                    public void onSuccess(@NonNull List<Character> characterList) {
                        characters.setValue(characterToCharacterViewItemMapper.map(characterList));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle the error case
                        //Yet, do not do nothing in this app

                        System.out.println(e.toString());
                        getAllCharactersFromBDD();

                    }
                }));
    }

    public void getAllCharactersFromBDD() {
        compositeDisposable.clear();
        compositeDisposable.add(characterDisplayRepository.getCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<CharacterEntity>>() {

                    @Override
                    public void onNext(List<CharacterEntity> characterEntities) {
                        characters.setValue(characterEntityToCharacterViewItemMapper.map(characterEntities));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle the error case
                        //Yet, do not do nothing in this app

                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void getCharacterById(int id){
        Log.i("DANS LE VIEW MODEL","Récupération du personnage id = " + id);
        compositeDisposable.clear();
        compositeDisposable.add(characterDisplayRepository.getCharacter(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Character>(){

                    @Override
                    public void onSuccess(@NonNull Character character) {
                        Log.i("DANS LE VIEW MODEL","Succès !");
                        try {
                            deleteCharacter(character.getId());
                        } catch (Exception e){}
                        addCharacter(character.getId());
                        List<CharacterDetailsViewItem> characterDetailsViewItemList = new ArrayList<>();
                        characterDetailsViewItemList.add(characterToCharacterDetailsViewItemMapper.map(character));
                        CharacterViewModel.this.character.setValue(characterDetailsViewItemList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("DANS LE VIEW MODEL", "Une erreur s'est produite " + e.getMessage());
                        getCharacterFromBDD(id);
                    }
                }));
    }


    public void getCharacterFromBDD(int id) {
        compositeDisposable.clear();
        compositeDisposable.add(characterDisplayRepository.getACharacter(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<CharacterEntity>>() {

                    @Override
                    public void onNext(List<CharacterEntity> characterEntities) {
                        try {
                            character.setValue(characterEntityToCharacterDetailsViewItemMapper.map(characterEntities));
                        } catch (Exception e) {
                            this.onError(e);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // handle the error case
                        //Yet, do not do nothing in this app

                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void addCharacter(final int charId){
        compositeDisposable.add(characterDisplayRepository.addCharacter(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        System.out.println("ADDED ID " + charId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e.toString() + " / ADD " + charId);
                    }
                }));
    }

    public void deleteCharacter(final int charId){
        compositeDisposable.add(characterDisplayRepository.removeCharacter(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        System.out.println("DELETED ID " + charId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println(e.toString()+ " / DELETE " + charId);
                    }
                }));
    }
}