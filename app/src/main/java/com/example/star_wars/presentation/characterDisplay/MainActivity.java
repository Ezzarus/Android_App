package com.example.star_wars.presentation.characterDisplay;

import android.os.Bundle;

import com.example.star_wars.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité Principale de l'application
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPagerAndTabs();
    }


    private void setupViewPagerAndTabs() {
        viewPager = findViewById(R.id.view_pager);

        final CharacterFragment allCharactersFragment = CharacterFragment.newInstance();

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                    return allCharactersFragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                    return CharacterFragment.TAB_NAME;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }

}