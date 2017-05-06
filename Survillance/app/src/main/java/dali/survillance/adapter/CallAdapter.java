package dali.survillance.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dali.survillance.R;
import dali.survillance.controlleur.CallsController;
import dali.survillance.model.Call;
import dali.survillance.other.Constant;

/**
 * Created by Mohamed ali on 19/01/2017.
 */

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.MyViewHolder> {

    private ArrayList<Call> list_call=new ArrayList<>();
    private Context mContext;


    private ProgressDialog mProgress;
    public CallAdapter(Context context,ArrayList<Call> list_call) {
        this.mContext=context;
        this.list_call = list_call;

    }

    @Override
    public CallAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CallAdapter.MyViewHolder holder, final int position) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String Today = sdf.format(Calendar.getInstance().getTime());
        try {
            Date mdate=sdf.parse(list_call.get(position).getDate());
            String call_date=sdf.format(mdate);
            if(call_date.equals(Today)){
                if(position==0 ){
                    holder.ll.setVisibility(View.VISIBLE);
                    holder.date.setText("Today");
                }
            }else{
                if(position>0) {
                    mdate = sdf.parse(list_call.get(position - 1).getDate());
                    String call_date1 = sdf.format(mdate);
                    if (!call_date.equals(call_date1)) {
                        holder.ll.setVisibility(View.VISIBLE);
                        holder.date.setText(call_date);
                    } else {
                        holder.ll.setVisibility(View.GONE);
                        holder.date.setText(call_date);
                    }
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.nom.setText(list_call.get(position).getNom());
            holder.phone.setText(list_call.get(position).getPhone());

            holder.hear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new lickAudio().execute(list_call.get(position).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_call.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nom,phone,date;
        ImageButton hear;
        LinearLayout ll;


        public MyViewHolder(View itemView) {
            super(itemView);
            nom=(TextView)itemView.findViewById(R.id.id_row_call_nom);
            phone=(TextView)itemView.findViewById(R.id.id_row_call_phone);
            date=(TextView)itemView.findViewById(R.id.id_row_call_date);
            hear=(ImageButton) itemView.findViewById(R.id.id_row_call_bt_hear);
            ll =(LinearLayout)itemView.findViewById(R.id.id_row_call_ll_date);

        }
    }
    public class lickAudio extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(mContext);
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return CallsController.GetLinkAudioCalls(params[0]);
        }

        @Override
        protected void onPostExecute(String link) {
            super.onPostExecute(link);
            mProgress.dismiss();
            if(link==null){
                Toast.makeText(mContext,"Impossible to get this audio",Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(Constant.server+link), "audio/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        }

    }


}
