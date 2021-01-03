package com.example.star_wars.data.di;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.star_wars.data.api.CharacterDisplayService;
import com.example.star_wars.data.db.CharacterDatabase;
import com.example.star_wars.data.repository.CharacterDisplayDataRepository;
import com.example.star_wars.data.repository.CharacterDisplayRepository;
import com.example.star_wars.data.repository.CharacterDisplayLocalDataSource;
import com.example.star_wars.data.repository.CharacterDisplayRemoteDataSource;
import com.example.star_wars.presentation.viewModel.ViewModelFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;

/**
 * Classe permettant d'initialiser les repository au lancement de l'application
 */
public class FakeDependencyInjection {

    private static Context applicationContext;
    private static Retrofit retrofit;
    private static Gson gson;
    private static CharacterDatabase characterDatabase;
    private static CharacterDisplayRepository characterDisplayRepository;
    private static CharacterDisplayService characterDisplayService;
    private static ViewModelFactory viewModelFactory;

    //Création d'une instance pour utiliser l'interface Retrofit
    public static Retrofit getRetrofit() {
        Log.i("Fake DI", "Debut retrofit");
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://akabab.github.io/starwars-api/api/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        Log.i("Fake DI", "Fin retrofit");
        return retrofit;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static CharacterDisplayRepository getCharacterDisplayRepository() {
        if (characterDisplayRepository == null) {
            characterDisplayRepository = new CharacterDisplayDataRepository(
                    new CharacterDisplayRemoteDataSource(getCharacterDisplayService()),
                    new CharacterDisplayLocalDataSource(getCharacterDatabase())
            );
        }
        return characterDisplayRepository;
    }

    public static CharacterDisplayService getCharacterDisplayService() {
        if (characterDisplayService == null) {
            characterDisplayService = getRetrofit().create(CharacterDisplayService.class);
        }
        return characterDisplayService;
    }

    public static void setContext(Context context) {
        applicationContext = context;
    }

    public static ViewModelFactory getViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(getCharacterDisplayRepository());
        }
        return viewModelFactory;
    }

    public static CharacterDatabase getCharacterDatabase() {
        if (characterDatabase == null) {
            characterDatabase = Room.databaseBuilder(applicationContext,
                    CharacterDatabase.class, "character-database").build();
        }
        return characterDatabase;
    }
}
