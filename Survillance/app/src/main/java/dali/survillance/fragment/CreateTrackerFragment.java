package dali.survillance.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import dali.survillance.R;
import dali.survillance.activity.TrackerAct;
import dali.survillance.controlleur.TrackerController;
import dali.survillance.model.Tracker;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionIdentifierGenerator;
import dali.survillance.other.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTrackerFragment extends Fragment {


    EditText Name,prenom,age;
    private RadioGroup radioGroup;

    private RadioButton female,male;
    Button create;
    private ProgressDialog mProgress;
    boolean checkmale=true;
    public CreateTrackerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_tracker, container, false);

        Name=(EditText)view.findViewById(R.id.id_create_tracker_name);
        prenom=(EditText)view.findViewById(R.id.id_create_tracker_prenom);
        age=(EditText)view.findViewById(R.id.id_create_tracker_age);


        radioGroup=(RadioGroup)view.findViewById(R.id.id_create_tracker_rg_gender);
        female=(RadioButton)view.findViewById(R.id.id_create_tracker_radio_female);
        male=(RadioButton)view.findViewById(R.id.id_create_tracker_radio_male);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.id_inscri_radio_male){
                    checkmale=true;
                }else{
                    checkmale=false;
                }
            }
        });
        create=(Button)view.findViewById(R.id.id_create_tracker_bt_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName=Name.getText().toString();

                String _prenom=prenom.getText().toString();

                String _age=age.getText().toString();
                if(mName.isEmpty() || _prenom.isEmpty() || _age.isEmpty()){
                    //Constant.dialog("Invalid Data",layout);
                    dialogshow("erreur","Invalid Data");
                }else {
                    if(Integer.parseInt(_age)>100){
                        //Constant.dialog("Invalid Data",layout);
                        dialogshow("erreur","Invalid Data");
                    }else {
                        Tracker t=new Tracker();
                        SessionManager sessionManager=new SessionManager(getContext());
                        String userid=sessionManager.getUserDetails().get(SessionManager.KEY_ID);
                        Utilisateur u=new Utilisateur();
                        u.setId(userid);
                        t.setUser(u);
                        t.setNom(mName);
                        t.setAge(_age);
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if(selectedId==male.getId())
                            t.setGender("1");
                        else if(selectedId==female.getId())
                            t.setGender("2");

                        t.setPrenom(_prenom);
                        t.setCode(SessionIdentifierGenerator.CodeTracker());
                        new CreateTracker().execute(t);
                    }
                }
            }
        });



        return view;
    }
    private void dialogshow(String title,String msg){
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    private class CreateTracker extends AsyncTask<Object,String,Tracker>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(getContext());
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Tracker doInBackground(Object... params) {
            Tracker tracker= (Tracker) params[0];
            String id=TrackerController.createTracker(tracker);
                if(id!=null){
                    tracker.setId(id);
                    return tracker;
                }


            return null;
        }

        @Override
        protected void onPostExecute(Tracker res) {
            super.onPostExecute(res);
            mProgress.dismiss();
            if(res!=null){
                SessionManager sessionManager=new SessionManager(getContext());
                sessionManager.creatTrackerSession(res.getUser().getId(),"TRACKER",res.getId());
                TrackerAct act=(TrackerAct)getActivity();
                act.SetTracker(res);
                Fragment track= new TrackFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, track);
                fragmentTransaction.commit();
            }else{
                dialogshow("Erreur","Problem to create your tracker!");
            }
        }
    }
    public interface Trackerdata{
        public void SetTracker(Tracker c);
        public Tracker GetTracker();
    }

}
