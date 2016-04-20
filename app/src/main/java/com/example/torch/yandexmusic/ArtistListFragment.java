package com.example.torch.yandexmusic;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;

/*Фрагмент с главным списком всех артистов*/
public class ArtistListFragment extends Fragment {
    private RecyclerView mArtistsRecyclerView;
    private static ArtistAdapter mAdapter;
    private ImageLoader mImageLoader;

    public static void updateUI() {
        mAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.artists_list, container, false);

        mArtistsRecyclerView = (RecyclerView) v.findViewById(R.id.artists_list_recycler_view);
        mArtistsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArtistLab artistLab = ArtistLab.get(getContext());
        List<Artist> artists = artistLab.getArtists();
        mAdapter = new ArtistAdapter(artists);
        mArtistsRecyclerView.setAdapter(mAdapter);


        return v;
    }

    /*Holder для RecyclerView*/
    private class ArtistsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private NetworkImageView mSmallCover;
        private TextView mArtistName;
        private TextView mGenres;
        private TextView mAlbumsAndSongs;

        private Artist mArtist;

        public ArtistsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSmallCover = (NetworkImageView) itemView.findViewById(R.id.small_cover_image_view);
            mArtistName = (TextView) itemView.findViewById(R.id.artist_name_text_view);
            mGenres = (TextView) itemView.findViewById(R.id.genres_text_view);
            mAlbumsAndSongs = (TextView) itemView.findViewById(R.id.albums_and_songs_text_view);
        }


        public void bindArtist(Artist artist) {
            mArtist = artist;

            mArtist.setName(artist.getName());
            mArtistName.setText(mArtist.getName());
            mArtist.setGenres(artist.getGenres());
            mGenres.setText(mArtist.getGenres());
            mArtist.setAlbums(artist.getAlbums());
            mAlbumsAndSongs.setText(String.format(getString(R.string.albums_and_songs), mArtist.getAlbums(), mArtist.getTracks()));
            mArtist.setSmallCoverUrl(artist.getSmallCoverUrl());
            mImageLoader = NetworkUtils.getInstance(getContext()).getImageLoader();
            mSmallCover.setImageUrl(mArtist.getSmallCoverUrl(), mImageLoader);

        }

        @Override
        public void onClick(View v) {
            Intent intent = ArtistPagerActivity.newIntent(getActivity(), mArtist.getId());
            startActivity(intent);
        }
    }

    /*Адаптер для RecylcerView*/
    private class ArtistAdapter extends RecyclerView.Adapter<ArtistsHolder> {
        private List<Artist> mArtists;

        public ArtistAdapter(List<Artist> artists) {
            mArtists = artists;
        }

        @Override
        public ArtistsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.activity_artists_list, parent, false);
            return new ArtistsHolder(view);
        }

        @Override
        public void onBindViewHolder(ArtistsHolder holder, int position) {
            Artist artist = mArtists.get(position);
            holder.bindArtist(artist);
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }


    }

}
