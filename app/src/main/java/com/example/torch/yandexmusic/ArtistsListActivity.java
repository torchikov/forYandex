package com.example.torch.yandexmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*Хост для главного фрагмента со списком артистов*/
public class ArtistsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle(getString(R.string.title_main_activity));

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ArtistListFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }

}
