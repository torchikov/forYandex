package com.example.torch.yandexmusic;


import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*Класс для хранения единой базы артистов и дальнейшей работы с ней*/
public class ArtistLab {
    private static ArtistLab sArtistLab;
    private static List<Artist> sArtists;
    private static Context sContext;
    private String mJsonString = "http://download.cdn.yandex.net/mobilization-2016/artists.json";

    private ArtistLab(Context context) {
        sArtists = new ArrayList<>();
        sContext = context;
        jsonToList(mJsonString);
    }

    public static ArtistLab get(Context context) {
        if (sArtistLab == null) {
            sArtistLab = new ArtistLab(context);
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

    /*Метод для парсинга json*/
    private void jsonToList(String jsonUrl) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(jsonUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Artist artist = new Artist();
//                  Артист
                        JSONObject artistsJSONObject = response.getJSONObject(i);
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

                        String description = artistsJSONObject.getString("description");
                        String firstLatterSubString = description.substring(0, 1);
                        firstLatterSubString = firstLatterSubString.toUpperCase();
                        String subString = description.substring(1);
                        description = firstLatterSubString + subString;

                        artist.setId(artistsJSONObject.getLong("id"));
                        artist.setName(artistsJSONObject.getString("name"));
                        artist.setGenres(genres.toString());
                        artist.setTracks(artistsJSONObject.getInt("tracks"));
                        artist.setAlbums(artistsJSONObject.getInt("albums"));
                        artist.setLink(link);
                        artist.setDescription(description);
                        artist.setSmallCoverUrl(coversJSONObject.getString("small"));
                        artist.setBigCoverUrl(coversJSONObject.getString("big"));
                        sArtists.add(artist);
                    }
                    ArtistListFragment.updateUI();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        NetworkUtils.getInstance(sContext).addToRequestQueue(jsonArrayRequest);

    }
}
