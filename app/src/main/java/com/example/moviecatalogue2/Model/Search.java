package com.example.moviecatalogue2.Model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogue2.Movie.MovieAdapter;
import com.example.moviecatalogue2.Tv.TvAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private String input;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<Movie> searchMovie = new ArrayList<>();
    private ArrayList<Tv>   searchTv = new ArrayList<>();
    private RequestQueue queueMovie;
    private RequestQueue queueTv;
    private TvAdapter searchTvAdapter;
    private MovieAdapter searchMovieAdapter;

    public Search(Context context, String input,  RecyclerView recyclerView) {
        this.input = input;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public void SearchMovie(){
        searchMovie.clear();
        queueMovie = Volley.newRequestQueue(context);
        LinearLayoutManager llms  = new LinearLayoutManager(context);
        llms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llms);
        getSearchMovie();
    }

    public void getSearchMovie(){
        JsonObjectRequest rec= new JsonObjectRequest
                ("https://api.themoviedb.org/3/search/movie?api_key=e657f5965939c3f561350f052abbafec&language=en-US&query="+input, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray listMovie = null;
                                searchMovie.clear();
                                try {
                                    listMovie = response.getJSONArray("results");
                                    for (int i = 0; i < listMovie.length(); i++) {
                                        try {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            final JSONObject obj = listMovie.getJSONObject(i);

                                            searchMovie.add(new Movie(
                                                    obj.getInt("id"),
                                                    obj.getString("original_title"),
                                                    obj.getString("overview"),
                                                    obj.getString("vote_average"),
                                                    obj.getString("release_date"),
                                                    obj.getString("poster_path")));
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    searchMovieAdapter = new MovieAdapter(context);
                                    searchMovieAdapter.setListMovie(searchMovie);
                                    recyclerView.setAdapter(searchMovieAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                        //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queueMovie.add(rec);
    }

    public void SearchTv(){
        searchTv.clear();
        queueTv = Volley.newRequestQueue(context);
        LinearLayoutManager llms  = new LinearLayoutManager(context);
        llms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llms);
        getSearchTv();
    }

    public void getSearchTv(){
        JsonObjectRequest rec= new JsonObjectRequest
                ("https://api.themoviedb.org/3/search/tv?api_key=e657f5965939c3f561350f052abbafec&language=en-US&query="+input, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray listMovie = null;
                                searchTv.clear();
                                try {
                                    listMovie = response.getJSONArray("results");
                                    for (int i = 0; i < listMovie.length(); i++) {
                                        try {
                                            recyclerView.setVisibility(View.VISIBLE);
                                            final JSONObject obj = listMovie.getJSONObject(i);

                                            searchTv.add(new Tv(
                                                    obj.getInt("id"),
                                                    obj.getString("original_name"),
                                                    obj.getString("overview"),
                                                    obj.getString("vote_average"),
                                                    obj.getString("first_air_date"),
                                                    obj.getString("poster_path")));
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    searchTvAdapter = new TvAdapter(context);
                                    searchTvAdapter.setListMovie(searchTv);
                                    recyclerView.setAdapter(searchTvAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                        //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queueTv.add(rec);
    }

}
