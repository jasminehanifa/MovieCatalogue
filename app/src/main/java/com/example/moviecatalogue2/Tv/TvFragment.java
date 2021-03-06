package com.example.moviecatalogue2.Tv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogue2.Model.Search;
import com.example.moviecatalogue2.Model.Tv;
import com.example.moviecatalogue2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TvFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rvMovie;
    private ArrayList<Tv> mList = new ArrayList<>();
    private RequestQueue queue;
    private TvAdapter movieAdapter;
    private ProgressBar progressBar;
    private EditText etSearch;
    private ImageView ivSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Search       search;



    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_tv,container,false);
        progressBar = v.findViewById(R.id.progressBar);
        etSearch = v.findViewById(R.id.etSearch);
        ivSearch = v.findViewById(R.id.ivSearch);
        rvMovie = v.findViewById(R.id.rvMovie);
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        queue   = Volley.newRequestQueue(getContext());
        show_tv();
        swipeRefreshLayout = v.findViewById(R.id.swipeMovie);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    searchProcess(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etSearch.getText().toString().equals("")){
                    show_tv();
                }else {
                    searchProcess(etSearch.getText().toString());
                }
            }
        });

        return v;
    }

    private void show_tv(){
        JsonObjectRequest rec= new JsonObjectRequest
                ("https://api.themoviedb.org/3/discover/tv?api_key=e657f5965939c3f561350f052abbafec&language=en-US", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray listMovie = null;
                                mList.clear();
                                try {
                                    listMovie = response.getJSONArray("results");
                                    for (int i = 0; i < listMovie.length(); i++) {
                                        try {
                                            progressBar.setVisibility(View.GONE);
                                            rvMovie.setVisibility(View.VISIBLE);
                                            final JSONObject obj = listMovie.getJSONObject(i);

                                            mList.add(new Tv(
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
                                    movieAdapter = new TvAdapter(getContext());
                                    movieAdapter.setListMovie(mList);
                                    rvMovie.setAdapter(movieAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                        //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(rec);
    }

    public void searchProcess(String text){
        search = new Search(getContext(), text, rvMovie);
        search.SearchTv();
    }


    @Override
    public void onRefresh() {
        show_tv();
        swipeRefreshLayout.setRefreshing(false);
    }
}