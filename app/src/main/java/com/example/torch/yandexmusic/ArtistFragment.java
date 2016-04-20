package com.example.torch.yandexmusic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/*Фрагмент с детальным описанием артиста*/
public class ArtistFragment extends Fragment {
    private static final String ARG_ARTIST_ID = "artist_id";

    private Artist mArtist;
    private NetworkImageView mBigCoverImageView;
    private TextView mGenresTextView;
    private TextView mAlbumsAndTracksTextView;
    private TextView mDescriptionTextView;
    private ImageLoader mImageLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        long artistId = getArguments().getLong(ARG_ARTIST_ID);
        mArtist = ArtistLab.get(getContext()).getArtistById(artistId);
        mImageLoader = NetworkUtils.getInstance(getContext()).getImageLoader();

        View v = inflater.inflate(R.layout.artist_fragment, container, false);


        mBigCoverImageView = (NetworkImageView) v.findViewById(R.id.artist_big_cover_image_view);
        mGenresTextView = (TextView) v.findViewById(R.id.artist_genres_text_view);
        mAlbumsAndTracksTextView = (TextView) v.findViewById(R.id.artist_albums_and_tracks_text_view);
        mDescriptionTextView = (TextView) v.findViewById(R.id.artist_description_text_view);

        mBigCoverImageView.setImageUrl(mArtist.getBigCoverUrl(), mImageLoader);
        mGenresTextView.setText(mArtist.getGenres());
        mAlbumsAndTracksTextView.setText(String.format(getString(R.string.artist_albums_and_tracks), mArtist.getAlbums(), mArtist.getTracks()));
        mDescriptionTextView.setText(mArtist.getDescription());

        return v;
    }

    public static ArtistFragment newInstance(long artistId) {
        Bundle args = new Bundle();
        args.putLong(ARG_ARTIST_ID, artistId);

        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
