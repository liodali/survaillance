package dali.survillance.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dali.survillance.R;
import dali.survillance.controlleur.CallsController;
import dali.survillance.fragment.CallFragment;
import dali.survillance.model.Call;
import dali.survillance.other.Upload;

public class SaveCall extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView nom,phone,date,duration;
    private Button save,cancel;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_call);



        mProgress=new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_save_call);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Storage Call in Our database");


        final Call c=getIntent().getParcelableExtra("call");
        final String filename=getIntent().getStringExtra("filename");
        final String path=getIntent().getStringExtra("path");



        nom=(TextView)findViewById(R.id.id_save_call_nom);
        phone=(TextView)findViewById(R.id.id_save_call_phone);
        date=(TextView)findViewById(R.id.id_save_call_date);
        duration=(TextView)findViewById(R.id.id_save_call_duration);

        save=(Button)findViewById(R.id.id_save_call_save);
        cancel=(Button)findViewById(R.id.id_save_call_cancel);
        nom.setText(c.getNom());
        phone.setText(c.getPhone());
        date.setText(c.getDate());
        duration.setText(c.getDure());
        Log.i("file",filename);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("id", "id:"+c.getUser().getId());
                new StorageCall().execute(path,c,filename);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
         Upload.doFileUpload(audiofile.getAbsolutePath());
            boolean res= CallsController.StorageCall(date,String.valueOf(total_time),file_name+".mp3",id,"UNKOWN",inCall);
            if(res){
                return true;
                Toast.makeText(context, "storage of call finish with success ", Toast.LENGTH_LONG).show();
            }else{
                return  false;
                Toast.makeText(context, "storage of call finish with failure ", Toast.LENGTH_LONG).show();
            }
        */

    }
    public class StorageCall extends AsyncTask<Object,String,Boolean>{
        Call mcall;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }
        @Override
        protected Boolean doInBackground(Object... params) {
            if(Upload.doFileUpload((String) params[0])) {

                mcall = (Call) params[1];
                Log.i("id", "id:" + mcall.getUser().getId());
                boolean res = CallsController.StorageCall(mcall.getDate(), mcall.getDure(), (String) params[2], mcall.getUser().getId(), mcall.getNom(), mcall.getPhone());
                if (res) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgress.dismiss();
            if(aBoolean){
                Toast.makeText(SaveCall.this, "storage of call finish with success ", Toast.LENGTH_LONG).show();
                if(CallFragment.stop){
                    CallFragment.update_list=true;
                    CallFragment.call=mcall;

                }

            }else{

                Toast.makeText(SaveCall.this, "storage of call finish with failure ", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}
