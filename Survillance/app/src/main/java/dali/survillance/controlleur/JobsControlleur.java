package dali.survillance.controlleur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import dali.survillance.model.Jobs;
import dali.survillance.other.Constant;
import dali.survillance.other.JSONparser;

/**
 * Created by Mohamed ali on 24/01/2017.
 */

public class JobsControlleur {

    public static boolean makeJobs(Jobs jobs){

        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(jobs.getTracker().getId(), "iso-8859-1")+"&"+
                    URLEncoder.encode("jobs", "iso-8859-1") + "="
                    + URLEncoder.encode(jobs.getJobs(), "iso-8859-1")+"&"+
                    URLEncoder.encode("date", "iso-8859-1") + "="
                    + URLEncoder.encode(jobs.getDate(), "iso-8859-1");


            String url= Constant.server+"makeJobs.php";

            JSONparser J=new JSONparser();
            String res=J.makeHttpRequest_Post(url,data);
            JSONObject jsObject=new JSONObject(res);
            int val=jsObject.getInt("message");
            if(val==1){
                return true;
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NullPointerException e){

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }




    public static ArrayList<Jobs> getJobs(String tracker_id){
        ArrayList<Jobs> list=new ArrayList<>();
        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(tracker_id, "iso-8859-1") ;


            String url= Constant.server+"getJobs.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("jobs");
                    JSONArray userObj=new JSONArray(val);
                    for(int i=0;i<userObj.length();i++){
                        Jobs j=new Jobs();
                        j.setId(userObj.getJSONObject(i).getString("ID"));
                        j.setJobs(userObj.getJSONObject(i).getString("JOBS_ATT"));
                        if(userObj.getJSONObject(i).getInt("STATUS")==1)
                            j.setStatus(true);
                        else
                            j.setStatus(false);
                        j.setDate(userObj.getJSONObject(i).getString("DATE_ATT"));

                        list.add(j);

                    }
                    return list;

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("errJSONExcep",e.toString());

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            return null;
        }


        return list;
    }
    public static void finishJobs(String tracker_id){
        ArrayList<Jobs> list=new ArrayList<>();
        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(tracker_id, "iso-8859-1") ;


            String url= Constant.server+"updateJobs.php";

            JSONparser J=new JSONparser();
           J.makeHttpRequest_Post(url,data);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NullPointerException e){

        }



    }


}
