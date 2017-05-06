package dali.survillance.service;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

import dali.survillance.activity.SaveCall;
import dali.survillance.model.Call;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;

/**
 * Created by Mohamed ali on 18/01/2017.
 */

public class TService extends Service {
    MediaRecorder recorder;
    File audiofile;
    String file_name,date;
    String name, phonenumber;
    String audio_format;
    public String Audio_Type;
    int audioSource;
    long start_time, end_time;
    Context context;
    private Handler handler;
    Timer timer;
    Boolean offHook = false, ringing = false;
    Toast toast;
    Boolean isOffHook = false;
    private boolean recordstarted = false;

    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";
    private CallBr br_call;




    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("service", "destroy");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // final String terminate =(String)
        // intent.getExtras().get("terminate");//
        // intent.getStringExtra("terminate");
        // Log.d("TAG", "service started");
        //
        // TelephonyManager telephony = (TelephonyManager)
        // getSystemService(Context.TELEPHONY_SERVICE); // TelephonyManager
        // // object
        // CustomPhoneStateListener customPhoneListener = new
        // CustomPhoneStateListener();
        // telephony.listen(customPhoneListener,
        // PhoneStateListener.LISTEN_CALL_STATE);
        // context = getApplicationContext();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OUT);
        filter.addAction(ACTION_IN);
        this.br_call = new CallBr();
        this.registerReceiver(this.br_call, filter);

        // if(terminate != null) {
        // stopSelf();
        // }
        return START_NOT_STICKY;
    }

    public class CallBr extends BroadcastReceiver {
        Bundle bundle;
        String state;
        String inCall, outCall;
        public boolean wasRinging = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_IN)) {
                if ((bundle = intent.getExtras()) != null) {
                    state = bundle.getString(TelephonyManager.EXTRA_STATE);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        inCall = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        wasRinging = true;
                        Toast.makeText(context, "IN : " + inCall, Toast.LENGTH_LONG).show();
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                        //if (wasRinging == true) {
                            Toast.makeText(context, "HERE_OFFHOOK", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "ANSWERED", Toast.LENGTH_LONG).show();
                            start_time = System.currentTimeMillis();
                            date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());
                            String out = new SimpleDateFormat("ddMMyyyyhhmmss").format(Calendar.getInstance().getTime());
                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/TestRecordingDasa1");
                            if (!sampleDir.exists()) {
                                sampleDir.mkdirs();
                            }
                            file_name = "Record"+out;
                            try {
                                audiofile = File.createTempFile(file_name, ".mp3", sampleDir);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                            recorder = new MediaRecorder();
//                          recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

                            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            recorder.setOutputFile(audiofile.getAbsolutePath());
                            try {
                                recorder.prepare();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            recorder.start();
                            recordstarted = true;
                      //  }
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        wasRinging = false;
                        Toast.makeText(context, "REJECT || DISCO", Toast.LENGTH_LONG).show();
                        if (recordstarted) {
                            recorder.stop();
                            recordstarted = false;
                            end_time = System.currentTimeMillis();
                            long total_time = end_time - start_time;
                            Intent saveIntent=new Intent(TService.this, SaveCall.class);
                            Call c=new Call();
                            if(!outCall.isEmpty() || outCall!=null){
                                c.setPhone(outCall);
                            }else{
                                c.setPhone(inCall);
                            }
                            c.setDate(date);
                            c.setDure(String.valueOf(total_time));
                            c.setNom("UNKOWN");
                            Utilisateur user=new Utilisateur();
                            SessionManager session=new SessionManager(getApplicationContext());
                            user.setId(session.getUserDetails().get(SessionManager.KEY_ID));
                            c.setUser(user);
                            saveIntent.putExtra("call",c);
                            saveIntent.putExtra("path",audiofile.getPath());
                            saveIntent.putExtra("filename",audiofile.getName());
                            saveIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(saveIntent);

                        }
                    }
                }
            } else if (intent.getAction().equals(ACTION_OUT)) {
                Log.i("out_call","call");
                if ((bundle = intent.getExtras()) != null) {
                    outCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    Toast.makeText(context, "OUT : " + outCall, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
