package dali.survillance.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import dali.survillance.R;
import dali.survillance.controlleur.TrackerController;
import dali.survillance.controlleur.UserControlleur;
import dali.survillance.model.Tracker;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;

public class MainActivity extends AppCompatActivity {


    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session=new SessionManager(getApplicationContext());
        if(!session.isLoggedIn()){
            Intent i=new Intent(MainActivity.this,Connect.class);
            startActivity(i);
            finish();

        }else{
            try {
                if(session.getUserStat().get(SessionManager.KEY_STAT).equals("USER")){
                    //session.getUserDetails().get(SessionManager.KEY_ID)
                    new getUser().execute(session.getUserDetails().get(SessionManager.KEY_ID));
                }else if(session.getUserStat().get(SessionManager.KEY_STAT).equals("TRACKER")){
                    //session.getUserDetails().get(SessionManager.KEY_ID)
                    Log.i("id",session.getUserTracker().get(SessionManager.KEY_TRACK));
                    new getTracker().execute(session.getUserDetails().get(SessionManager.KEY_ID),session.getUserTracker().get(SessionManager.KEY_TRACK));
                }else {
                    Log.i("prob","None");
                    session.logoutUser();
                    finish();
                }
            }catch (NullPointerException e){
                Log.i("prob","excep");
                session.logoutUser();
                finish();
            }


        }
    }
    public  class getUser extends AsyncTask<String,String,Utilisateur>{

        @Override
        protected Utilisateur doInBackground(String... params) {
            Utilisateur user= UserControlleur.getUser(params[0]);
            return user;
        }

        @Override
        protected void onPostExecute(Utilisateur o) {
            super.onPostExecute(o);

                Intent i=new Intent(MainActivity.this,Home.class);
                i.putExtra("user",o);
                startActivity(i);
                finish();


        }
    }
        public  class getTracker extends AsyncTask<String,String,Tracker>{

        @Override
        protected Tracker doInBackground(String... params) {
            Tracker tracker= TrackerController.getTracker(params[1]);
            if (tracker!=null){
                Utilisateur user= UserControlleur.getUser(params[0]);
                tracker.setUser(user);
                return tracker;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Tracker o) {
            super.onPostExecute(o);
            if(o!=null){
                Intent i=new Intent(MainActivity.this,TrackerAct.class);
                i.putExtra("tracker",o);
                i.putExtra("user",o.getUser());
                i.putExtra("value",1);
                startActivity(i);
                finish();
            }else{
                session.logoutUser();
            }



        }
    }
}
