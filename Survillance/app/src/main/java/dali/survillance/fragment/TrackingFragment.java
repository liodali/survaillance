package dali.survillance.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import dali.survillance.R;
import dali.survillance.adapter.TrackersAdapter;
import dali.survillance.controlleur.TrackerController;
import dali.survillance.model.Tracker;
import dali.survillance.other.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackingFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayout ll;
    TrackersAdapter adapter;
    private ProgressDialog mProgress;
    public TrackingFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tracking, container, false);
        ll=(LinearLayout)view.findViewById(R.id.id_tracking_empty);
        recyclerView=(RecyclerView)view.findViewById(R.id.id_tracking_recycler);

        new getTracker().execute();
        return view;
    }


    public class getTracker extends AsyncTask<String,String,ArrayList<Tracker>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.VISIBLE);
            mProgress=new ProgressDialog(getContext());
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected ArrayList<Tracker> doInBackground(String... params) {
            SessionManager sessionManager=new SessionManager(getContext());
            String id=sessionManager.getUserDetails().get(sessionManager.KEY_ID);
            return TrackerController.getTrackers(id);
        }

        @Override
        protected void onPostExecute(ArrayList<Tracker> trackers) {
            super.onPostExecute(trackers);
            mProgress.dismiss();
            if(trackers.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
            }else{

                ll.setVisibility(View.GONE);
                adapter=new TrackersAdapter(trackers,getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
            }
        }
    }



}
