package com.example.torch.yandexmusic;

/*Модель Артиста*/
public class Artist {
    private long mId;
    private String mName;
    private String mGenres;
    private int mTracks;
    private int mAlbums;
    private String mLink;
    private String mDescription;
    private String mSmallCoverUrl;
    private String mBigCoverUrl;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getGenres() {
        return mGenres;
    }

    public void setGenres(String genres) {
        mGenres = genres;
    }

    public int getTracks() {
        return mTracks;
    }

    public void setTracks(int tracks) {
        mTracks = tracks;
    }

    public int getAlbums() {
        return mAlbums;
    }

    public void setAlbums(int albums) {
        mAlbums = albums;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getSmallCoverUrl() {
        return mSmallCoverUrl;
    }

    public void setSmallCoverUrl(String smallCoverUrl) {
        mSmallCoverUrl = smallCoverUrl;
    }

    public String getBigCoverUrl() {
        return mBigCoverUrl;
    }

    public void setBigCoverUrl(String bigCoverUrl) {
        mBigCoverUrl = bigCoverUrl;
    }
}
