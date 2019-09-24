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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.moviecatalogue2.Model.Movie;
import com.example.moviecatalogue2.R;

import java.util.Calendar;
import java.util.List;

public class MovieComingSoonReminder extends BroadcastReceiver {
    private static int notif = 2000;

    public MovieComingSoonReminder(){

    }
    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieComingSoonReminder.class);
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void sendComingSoonNotif(Context context, String title, String mDesc, int id, Movie mMovieResult){
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "AlarmManager channel 2";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(mDesc)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("11011",
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId("11011");
//            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    public void setAlarmComingSoon(Context context, List<Movie> movieList){
        for (Movie movie : movieList){
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieComingSoonReminder.class);
            intent.putExtra("movietitle", movie.getMovieName());
            intent.putExtra("movieid", movie.getId());
            intent.putExtra("movieposter", movie.getMoviePoster());
            intent.putExtra("moviemDescription", movie.getMovieDescription());
            intent.putExtra("moviedate", movie.getMovieRelase());
            intent.putExtra("id", notif);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            notif += 1;
        }
        Toast.makeText(context, "ON", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String movieTitle = intent.getStringExtra("movietitle");
        int id = intent.getIntExtra("id", 0);
        Movie mMovieResult = new Movie();
        String mDesc = context.getString(R.string.today_release) + " " +movieTitle;
        sendComingSoonNotif(context, context.getString(R.string.app_name), mDesc, id, mMovieResult);
        Log.d("dapetnotif", "onReceive: ");

    }
}
