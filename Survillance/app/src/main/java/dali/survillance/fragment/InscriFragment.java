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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import dali.survillance.other.Constant;
import dali.survillance.other.SessionIdentifierGenerator;
import dali.survillance.R;
import dali.survillance.activity.Home;
import dali.survillance.controlleur.UserControlleur;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;

@SuppressLint("ValidFragment")
public class InscriFragment extends Fragment {


    private RadioGroup radioGroup;

    private RadioButton female,male;
    private EditText nom,prenom,email,password,age;
    private Button inscri;
    boolean checkmale=true;
    private CoordinatorLayout layout;
    private ProgressDialog mProgress;
    public InscriFragment() {
        // Required empty public constructor
    }


    public InscriFragment(CoordinatorLayout layout, ProgressDialog mProgress) {
        this.layout=layout;
        this.mProgress=mProgress;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inscri, container, false);

        nom=(EditText)view.findViewById(R.id.id_inscri_input_nom);
        prenom=(EditText)view.findViewById(R.id.id_inscri_input_prenom);
        age=(EditText)view.findViewById(R.id.id_inscri_input_age);
        email=(EditText)view.findViewById(R.id.id_inscri_input_email);
        password=(EditText)view.findViewById(R.id.id_inscri_input_password);

        radioGroup=(RadioGroup)view.findViewById(R.id.id_inscri_rg_gender);
        female=(RadioButton)view.findViewById(R.id.id_inscri_radio_female);
        male=(RadioButton)view.findViewById(R.id.id_inscri_radio_male);

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
        inscri=(Button)view.findViewById(R.id.id_inscri_bt);
        inscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _nom=nom.getText().toString();
                String _prenom=prenom.getText().toString();
                String _email=email.getText().toString();
                String _pwd=password.getText().toString();
                String _age=age.getText().toString();
                if(_nom.isEmpty() || _prenom.isEmpty() || _email.isEmpty()|| _pwd.isEmpty()|| _age.isEmpty()){
                    Constant.dialog("Invalid Data",layout);
                }else {
                    if(Integer.parseInt(_age)>100){
                        Constant.dialog("Invalid Data",layout);
                    }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
                        Constant.dialog("Invalid Data",layout);
                    }else {
                        Utilisateur u=new Utilisateur();
                        u.setEmail(_email);
                        u.setAge(_age);
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if(selectedId==male.getId())
                            u.setGendre("1");
                        else
                             u.setGendre("2");
                        u.setNom(_nom);
                        u.setPrenom(_prenom);
                        u.setCode_admin(SessionIdentifierGenerator.nextSessionId());
                        new Inscri().execute(u,_pwd);
                    }
                }
            }
        });
        return view;
    }

    public class Inscri extends AsyncTask<Object,String,Utilisateur> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }
        @Override
        protected Utilisateur doInBackground(Object... params) {
            Utilisateur u=(Utilisateur)params[0];
            String mPassword=String.valueOf(params[1]);
            boolean res= UserControlleur.Inscription(u,mPassword);
            if(res){
                return  u;
            }else
                return null;
        }


        @Override
        protected void onPostExecute(Utilisateur utilisateur) {
            super.onPostExecute(utilisateur);
            mProgress.dismiss();
            if(utilisateur==null){
                Constant.dialog("Error In Signup",layout);
            }else{
                SessionManager session=new SessionManager(getContext());
                session.createLoginSession((utilisateur.getId()),(utilisateur.getNom()),"USER");
                Intent i=new Intent(getContext(), Home.class);
                i.putExtra("user",utilisateur);
                startActivity(i);
                getActivity().finish();
            }
        }
    }


}
