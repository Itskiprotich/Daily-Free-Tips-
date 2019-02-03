package com.keeprawteach.free.Message;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.keeprawteach.free.FirstLaunch;
import com.keeprawteach.free.R;

public class Mess  extends FirebaseMessagingService {

    private static final String TAG="Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            String message=remoteMessage.getNotification().getBody().toString();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getBaseContext())
                            .setSmallIcon(R.drawable.applogo)
                            .setContentTitle("Daily Sports Tips")
                            .setAutoCancel(true)
                            .setContentText(message);

            Intent notificationIntent = new Intent(getBaseContext(), FirstLaunch.class);

            PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

    }
}
