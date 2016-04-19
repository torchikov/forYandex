package com.example.torch.yandexmusic;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*Класс для хранения единой базы артистов и дальнейшей работы с ней*/
public class ArtistLab {
    private static ArtistLab sArtistLab;
    private static List<Artist> sArtists;

    private ArtistLab() {
        sArtists = new ArrayList<>();
        new ParseTask().execute();
    }

    public static ArtistLab get() {
        if (sArtistLab == null) {
            sArtistLab = new ArtistLab();
        }
        return sArtistLab;
    }

    public List<Artist> getArtists() {
        return sArtists;
    }

    public Artist getArtistById(long artistId) {
        Artist artist = null;
        for (int i = 0; i < sArtists.size(); i++) {
            if (sArtists.get(i).getId() == artistId) {
                artist = sArtists.get(i);
                break;
            }
        }
        return artist;
    }

    /*Внутренний класс для парсинга JSON*/
    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection mHttpURLConnection;
        BufferedReader mBufferedReader;
        String mResultJSON = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("http://download.cdn.yandex.net/mobilization-2016/artists.json");
                mHttpURLConnection = (HttpURLConnection) url.openConnection();
                mHttpURLConnection.setRequestMethod("GET");
                mHttpURLConnection.setInstanceFollowRedirects(true);
                mHttpURLConnection.connect();

                InputStream inputStream = mHttpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                mBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = mBufferedReader.readLine()) != null) {
                    buffer.append(line);
                }

                mResultJSON = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return mResultJSON;
        }


        @Override
        protected void onPostExecute(String stringJSON) {
            super.onPostExecute(stringJSON);

            try {
//                Массив со всеми артистами
                JSONArray artists = new JSONArray(stringJSON);

                for (int i = 0; i < artists.length(); i++) {
                    Artist artist = new Artist();
//                  Артист
                    JSONObject artistsJSONObject = artists.getJSONObject(i);
//                    Массив с жанрами
                    JSONArray genresJSONArray = artistsJSONObject.getJSONArray("genres");

                    StringBuilder genres = new StringBuilder();

                    for (int j = 0; j < genresJSONArray.length(); j++) {
                        genres.append(genresJSONArray.getString(j));
                        if (j == genresJSONArray.length() - 1) {
                            break;
                        }
                        genres.append(", ");
                    }

//                  Сайт
                    String link;
                    try {
                        link = artistsJSONObject.getString("link");
                    } catch (Exception e) {
                        link = "";
                    }

//                    Обложки
                    JSONObject coversJSONObject = artistsJSONObject.getJSONObject("cover");

                    artist.setId(artistsJSONObject.getLong("id"));
                    artist.setName(artistsJSONObject.getString("name"));
                    artist.setGenres(genres.toString());
                    artist.setTracks(artistsJSONObject.getInt("tracks"));
                    artist.setAlbums(artistsJSONObject.getInt("albums"));
                    artist.setLink(link);
                    artist.setDescription(artistsJSONObject.getString("description"));
                    artist.setSmallCoverUrl(coversJSONObject.getString("small"));
                    artist.setBigCoverUrl(coversJSONObject.getString("big"));
                    sArtists.add(artist);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Обновляем UI после выполнения задачи
            ArtistListFragment.updateUI();

        }
    }
}
