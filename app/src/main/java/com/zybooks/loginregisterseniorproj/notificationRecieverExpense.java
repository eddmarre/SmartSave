package com.zybooks.loginregisterseniorproj;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class notificationRecieverExpense extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent repeatingIntent=new Intent(context,AddExpenseActivity.class);

        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"notifyMe")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Save Smart")
                .setContentText("An update has been applied to your account")
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());

    }
}
