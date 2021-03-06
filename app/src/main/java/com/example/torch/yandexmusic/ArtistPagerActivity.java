package com.example.torch.yandexmusic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/*Хост для фрагментов с детальной информацией об артисте*/
public class ArtistPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String EXTRA_ARTIST_ID = "artist_id";
    public static final String EXTRA_POSITION_ON_LIST = "position";

    private ViewPager mViewPager;
    private List<Artist> mArtists;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_pager);

        final long artistId = getIntent().getLongExtra(EXTRA_ARTIST_ID, 0);

        mViewPager = (ViewPager) findViewById(R.id.artist_view_pager);
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(this);
        }

        mArtists = ArtistLab.get(this).getArtists();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Artist artist = mArtists.get(position);
                return ArtistFragment.newInstance(artist.getId());
            }

            @Override
            public int getCount() {
                return mArtists.size();
            }
        });

        for (int i = 0; i < mArtists.size(); i++) {
            if (mArtists.get(i).getId() == artistId) {
                mViewPager.setCurrentItem(i);
                /*Другого костыля не придумал, т.к. если position == 0, то onPageSelected сам не вызывается*/
                if (i == 0) {
                    onPageSelected(0);
                }
                break;
            }
        }

    }

    public static Intent newIntent(Context context, long artistId) {
        Intent intent = new Intent(context, ArtistPagerActivity.class);
        intent.putExtra(EXTRA_ARTIST_ID, artistId);
        return intent;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        /*Для установки заголовка для каждого артиста*/
        Artist artist = mArtists.get(position);
        setTitle(artist.getName());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = super.getSupportParentActivityIntent();
        if (intent != null) {
            intent.putExtra(EXTRA_POSITION_ON_LIST, mViewPager.getCurrentItem());
        }
        return intent;
    }

    


}
