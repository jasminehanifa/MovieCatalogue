package com.example.moviecatalogue2.Setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecatalogue2.Model.Movie;
import com.example.moviecatalogue2.R;
import com.example.moviecatalogue2.Reminder.MovieComingSoonReminder;
import com.example.moviecatalogue2.Reminder.MovieDailyReminder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private RequestQueue queue;
    private List<Movie> movieNotifList;
    private SwitchPreference dailySwitch;
    private SwitchPreference newReleaseSwitch;
    private MovieDailyReminder movieDailyReminder = new MovieDailyReminder();
    private MovieComingSoonReminder movieComingSoonReminder = new MovieComingSoonReminder();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final Date date = new Date();
    private final String today = dateFormat.format(date);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        queue = Volley.newRequestQueue(getActivity());
        movieNotifList = new ArrayList<>();

        dailySwitch = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
        dailySwitch.setOnPreferenceChangeListener(this);

        newReleaseSwitch = (SwitchPreference) findPreference(getString(R.string.key_new_release_reminder));
        newReleaseSwitch.setOnPreferenceChangeListener(this);

        Preference langPref = findPreference(getString(R.string.key_lang));
        langPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean value = (boolean) o;
        if (key.equals(getString(R.string.key_today_reminder))){
            if (value){
                movieDailyReminder.setRepeatAlarm(getActivity());
            }else {
                movieDailyReminder.cancelAlarm(getActivity());
            }
        }else {
            if (value) {
                setReleaseAlarm();
            } else {
                movieComingSoonReminder.cancelAlarm(getActivity());
            }
        }

        return true;
    }

    public class GetMovieTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            getDataNotif(strings[0]);
            return null;
        }
    }

    private void setReleaseAlarm() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=e657f5965939c3f561350f052abbafec&primary_release_date.gte="+today+"&primary_release_date.lte="+today;
        Log.d("testurl", url+"");
        SettingPreferenceFragment.GetMovieTask getDataAsync = new SettingPreferenceFragment.GetMovieTask();
        getDataAsync.execute(url);
    }

    public void getDataNotif(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                movieNotifList.clear();
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(data.getInt("id"));
                        movie.setMovieName(data.getString("original_title"));
                        movie.setMovieRelase(data.getString("release_date"));
                        movie.setMovieScore(data.getString("vote_average"));
                        movie.setMovieDescription(data.getString("overview"));
                        movie.setMoviePoster(data.getString("poster_path"));

                        if (data.getString("release_date").equals(today)) {
                            movieNotifList.add(movie);
                            Log.d("datanya", movie.toString());
                        }
                    }
                    movieComingSoonReminder.setAlarmComingSoon(getActivity(), movieNotifList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        queue.add(request);
        }
}
