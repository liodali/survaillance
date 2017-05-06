package dali.survillance.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dali.survillance.activity.TrackerAct;
import dali.survillance.other.Constant;
import dali.survillance.R;
import dali.survillance.activity.Home;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;

import static dali.survillance.controlleur.UserControlleur.Connection;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {

   private EditText email,password;
    private Button login,tracker;
    private CoordinatorLayout layout;
    private ProgressDialog mProgress;
    public LoginFragment() {
        // Required empty public constructor

    }
    public LoginFragment(CoordinatorLayout layout,ProgressDialog mProgress) {

        this.layout=layout;
        this.mProgress=mProgress;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email=(EditText)view.findViewById(R.id.id_login_input_email);
        password=(EditText)view.findViewById(R.id.id_login_input_password);
        login=(Button) view.findViewById(R.id.id_login_bt_login);
        tracker=(Button) view.findViewById(R.id.id_login_bt_login_tracker);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email=email.getText().toString();
                String _pwd=password.getText().toString();

                if(_email.isEmpty() || _pwd.isEmpty()){
                    Constant.dialog("Invalid Data",layout);
                }else{
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
                        Constant.dialog("Invalid Email",layout);
                    }else{
                        new login(1).execute(_email,_pwd);
                    }
                }


            }
        });
        tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email=email.getText().toString();
                String _pwd=password.getText().toString();

                if(_email.isEmpty() || _pwd.isEmpty()){
                    Constant.dialog("Invalid Data",layout);
                }else{
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
                        Constant.dialog("Invalid Email",layout);
                    }else{
                        new login(2).execute(_email,_pwd);
                    }
                }
            }
        });

        return  view ;
    }
    public class login extends AsyncTask<String,String,Utilisateur>{
        int click=0;
        public  login(int click){
            this.click=click;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Utilisateur doInBackground(String... params) {
            return Connection(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(Utilisateur utilisateur) {
            super.onPostExecute(utilisateur);
            mProgress.dismiss();
            if(utilisateur==null){
                Constant.dialog("user doesn't exist",layout);
            }else{

                SessionManager session=new SessionManager(getContext());

                if(click==1){
                    session.createLoginSession((utilisateur.getId()),(utilisateur.getNom()),"USER");
                    Intent i=new Intent(getContext(), Home.class);
                    i.putExtra("user",utilisateur);
                    startActivity(i);
                    getActivity().finish();
                }else if(click==2){
                    session.createLoginSession((utilisateur.getId()),(utilisateur.getNom()),"TRACKER");
                    Intent i=new Intent(getContext(), TrackerAct.class);
                    i.putExtra("user",utilisateur);
                    i.putExtra("value",0);
                    startActivity(i);
                    getActivity().finish();
                }

            }
        }
    }

}
