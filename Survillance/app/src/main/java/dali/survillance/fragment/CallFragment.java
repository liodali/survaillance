package dali.survillance.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import dali.survillance.R;
import dali.survillance.adapter.CallAdapter;
import dali.survillance.model.Call;
import dali.survillance.model.Utilisateur;
import dali.survillance.other.SessionManager;
import dali.survillance.service.TService;

import static dali.survillance.controlleur.CallsController.GetCalls;


public class CallFragment extends Fragment {


    public static boolean  update_list=false;
    public static boolean  stop=false;
    public static Call call;

    RecyclerView recyclerView;
    Button make_call_empty,make_call;
    LinearLayout empty_calls;
    ArrayList<Call> listCall=new ArrayList<>();
    private CallAdapter adapter;
    private ProgressDialog mProgress;
    private Utilisateur u=new Utilisateur();
    public CallFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SessionManager sessionManager=new SessionManager(getContext());
        String id=sessionManager.getUserDetails().get(sessionManager.KEY_ID);
        u.setId(id);
        View view =inflater.inflate(R.layout.fragment_call, container, false);
        mProgress=new ProgressDialog(getContext());
        empty_calls=(LinearLayout)view.findViewById(R.id.id_ll_empty_calls);
        recyclerView=(RecyclerView)view.findViewById(R.id.id_calls_fragment_recycler);
        make_call=(Button)view.findViewById(R.id.id_calls_fragment_bt_make_call);
        make_call_empty=(Button)view.findViewById(R.id.id_calls_fragment_empty_bt_make);

        empty_calls.setVisibility(View.GONE);

        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().stopService(new Intent(getContext(), TService.class));
                Intent myIntent = new Intent(getContext(), TService.class);
                myIntent.putExtra("user",u.getId());
                getContext().startService(myIntent);
                dialog();
            }
        });

        make_call_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().stopService(new Intent(getContext(), TService.class));
                Intent myIntent = new Intent(getContext(), TService.class);
                myIntent.putExtra("user",u.getId());
                getContext().startService(myIntent);
                dialog();
            }
        });
        Log.i("res","res:"+u.getId());
        new Getcalls().execute(u.getId());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resume","resume");
        if(update_list){
           listCall.add(0,call);
            adapter.notifyDataSetChanged();
            update_list=false;
            stop=false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("stop","stop");
        stop=true;
    }

    public class Getcalls extends AsyncTask<String,String,ArrayList<Call>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected ArrayList<Call> doInBackground(String... params) {
            return GetCalls(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Call> calls) {
            super.onPostExecute(calls);
            mProgress.dismiss();
            if(calls==null || calls.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                empty_calls.setVisibility(View.VISIBLE);
            }else{
                make_call.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                empty_calls.setVisibility(View.GONE);
                adapter=new CallAdapter(getContext(),calls);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

            }
        }
    }
    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Get Number");

// Set up the input
        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone = input.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(getContext(),"You don't have permission",Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
