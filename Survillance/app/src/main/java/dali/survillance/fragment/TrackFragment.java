package dali.survillance.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dali.survillance.R;
import dali.survillance.model.Tracker;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;
import dali.survillance.service.jobService;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackFragment extends Fragment {


    private TextView name,admin;
    private Button logout;

    public TrackFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        name=(TextView)view.findViewById(R.id.id_frag_track_name_tracker);
        admin=(TextView)view.findViewById(R.id.id_frag_track_name_tracker_par);
        try{
            ((TrackInterface) getContext()).toolbarInvi();
            Utilisateur user=((TrackInterface) getContext()).getUser();
            Tracker tracker=((CreateTrackerFragment.Trackerdata) getContext()).GetTracker();
            name.setText(tracker.getNom());
            admin.setText(user.getNom());
        }catch (ClassCastException cce){

        }
        logout=(Button)view.findViewById(R.id.id_frag_track_bt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager=new SessionManager(getContext());
                sessionManager.logoutUser();
                getActivity().finish();
            }
        });
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent(getActivity(), jobService.class);

        getActivity().startService(intent);
    }

    public interface TrackInterface{
        public void toolbarInvi();
        public Utilisateur getUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(), jobService.class));
    }
}
