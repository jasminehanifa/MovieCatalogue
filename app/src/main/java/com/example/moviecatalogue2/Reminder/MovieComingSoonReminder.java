package com.example.moviecatalogue2.Reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.moviecatalogue2.Setting.SettingPreferenceFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieComingSoonReminder extends BroadcastReceiver {
    private RequestQueue queue;
    int idReminder = 1;
    private List<Movie> movieNotifList = new ArrayList<>();
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    final Date date = new Date();
    final String today = dateFormat.format(date);


    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
//        String movieTitle = intent.getStringExtra("movietitle");
//        final int id = intent.getIntExtra("id", 0);
//        final Movie mMovieResult = new Movie();
//        final String mDesc = context.getString(R.string.today_release) + " " +movieTitle;
        getDataNotif(today);
        Log.d("dapetnotif", "onReceive: ");

    }

    public void getDataNotif(final String releaseToday){
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=e657f5965939c3f561350f052abbafec&primary_release_date.gte="+releaseToday+"&primary_release_date.lte="+releaseToday;
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

                        if (data.getString("release_date").equals(releaseToday)) {
                            movieNotifList.add(movie);
                            Log.d("datanya", movie.toString());
                        }
                    }

                    for (Movie movie : movieNotifList){
                        if(movie.getMovieRelase().equalsIgnoreCase(releaseToday)){
                            sendComingSoonNotif(context, movie.getMovieName(), movie.getMovieRelase(), idReminder);
                            idReminder++;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieComingSoonReminder.class);
        return PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void sendComingSoonNotif(Context context, String title, String desc, int id) {
        String CHANNEL_ID = "10002";
        String CHANNEL_NAME = "AlarmManager_channel_2";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

    public void setAlarmComingSoon(Context context) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieComingSoonReminder.class);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

        Toast.makeText(context, "ON", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show();
    }

}
