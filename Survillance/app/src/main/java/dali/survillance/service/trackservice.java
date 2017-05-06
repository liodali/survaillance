package dali.survillance.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import dali.survillance.R;
import dali.survillance.activity.MoreGPSTrack;
import dali.survillance.controlleur.GpsControlleur;
import dali.survillance.model.GPS;
import dali.survillance.other.Constant;
import dali.survillance.other.SessionManager;

/**
 * Created by Mohamed ali on 26/01/2017.
 */

public class trackservice extends IntentService {
    int id_notif=0;
    public trackservice() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String id_user = sessionManager.getUserDetails().get(sessionManager.KEY_ID);
        Log.i("id","id:"+id_user);
        while (Constant.checkNetworkConnection(getApplicationContext())) {
            Log.i("id","id:"+id_user);
            ArrayList<GPS> list= GpsControlleur.TrackPosition(id_user);

            for(int i=0;i<list.size();i++){
                GPS gps=list.get(i);
                Intent openIntent=new Intent(this, MoreGPSTrack.class);
                openIntent.putExtra("gps",gps);
                Log.i("Log gps nom","n:"+gps.getTracker().getNom());
                PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.valueOf(gps.getId()),openIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationManager managerCompat= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                Notification.Builder builder=new Notification.Builder(this);
                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_stat_track_changes)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("Tracking "+gps.getTracker().getNom())
                        .setContentText("Infomation about "+gps.getTracker().getNom())
                        .setAutoCancel(true);
                Notification notification=builder.build();
                managerCompat.notify(Integer.valueOf(gps.getId()),notification);

                GpsControlleur.TrackerRead(gps.getId());


            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
