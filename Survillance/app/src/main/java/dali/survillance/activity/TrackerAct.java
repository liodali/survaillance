package dali.survillance.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import dali.survillance.R;
import dali.survillance.fragment.CreateTrackerFragment;
import dali.survillance.fragment.TrackFragment;
import dali.survillance.model.Tracker;
import dali.survillance.model.Utilisateur;

public class TrackerAct extends AppCompatActivity implements TrackFragment.TrackInterface,CreateTrackerFragment.Trackerdata {

    Toolbar toolbar;
    Tracker tracker=new Tracker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        toolbar = (Toolbar) findViewById(R.id.toolbar_tracker);

        setSupportActionBar(toolbar);
        setTitle("Make Tracker");

        int value= getIntent().getIntExtra("value",0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(value==0){
            Fragment track= new CreateTrackerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, track);
            fragmentTransaction.commit();
        }else if(value==1){
            SetTracker((Tracker) getIntent().getParcelableExtra("tracker"));
            Fragment track= new TrackFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, track);
            fragmentTransaction.commit();
        }


    }



    @Override
    public void toolbarInvi() {

        toolbar.setVisibility(View.GONE);
    }

    @Override
    public Utilisateur getUser() {
        Utilisateur user= getIntent().getParcelableExtra("user");
        return user;
    }

    @Override
    public void SetTracker(Tracker c) {
        this.tracker=c;
    }

    @Override
    public Tracker GetTracker() {
        return this.tracker;
    }
}
