package dali.survillance.controlleur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import dali.survillance.model.Tracker;
import dali.survillance.other.Constant;
import dali.survillance.other.JSONparser;

/**
 * Created by Mohamed ali on 22/01/2017.
 */

public class TrackerController {




    public static String createTracker(Tracker c){

        try {
            String data  = URLEncoder.encode("userid", "iso-8859-1") + "=" + URLEncoder.encode(c.getUser().getId(), "iso-8859-1")
                    + "&"+
                    URLEncoder.encode("nom", "iso-8859-1") + "=" + URLEncoder.encode(c.getNom(), "iso-8859-1")
                    + "&"+
                    URLEncoder.encode("prenom", "iso-8859-1") + "=" + URLEncoder.encode(c.getPrenom(), "iso-8859-1")
                    + "&"+
                    URLEncoder.encode("age", "iso-8859-1") + "=" + URLEncoder.encode(c.getAge(), "iso-8859-1")
                    + "&"+
                    URLEncoder.encode("gender", "iso-8859-1") + "=" + URLEncoder.encode(c.getGender(), "iso-8859-1")
                    + "&"+
                    URLEncoder.encode("code", "iso-8859-1") + "=" + URLEncoder.encode(c.getCode(), "iso-8859-1") ;


            String url= Constant.server+"makeTracker.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    String id=jsObject.getString("id");
                    Log.i("id_tracker",id);
                    return id;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("errJSONExcep",e.toString());

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return null;
    }
    public static Tracker getTracker(String id){

        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(id, "iso-8859-1") ;


            String url= Constant.server+"GetTracker.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("tracker");
                    JSONArray TrackerObj=new JSONArray(val);
                    Tracker mTracker=new Tracker();
                    mTracker.setId(TrackerObj.getJSONObject(0).getString("ID"));
                    mTracker.setNom(TrackerObj.getJSONObject(0).getString("NOM"));
                    mTracker.setPrenom(TrackerObj.getJSONObject(0).getString("PRENOM"));
                    mTracker.setAge(TrackerObj.getJSONObject(0).getString("AGE"));
                    if(TrackerObj.getJSONObject(0).getInt("GENDRE")==1)
                        mTracker.setGender("Male");
                    else
                        mTracker.setGender("Female");
                    mTracker.setCode(TrackerObj.getJSONObject(0).getString("CODE_TRACKER"));
                    return mTracker;
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


        return null;
    }
    public static ArrayList<Tracker> getTrackers(String id){
            ArrayList<Tracker> list=new ArrayList<>();
        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(id, "iso-8859-1") ;


            String url= Constant.server+"GetTrackers.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("tracker");
                    JSONArray TrackerObj=new JSONArray(val);
                    for (int i=0;i<TrackerObj.length();i++){
                        Tracker mTracker=new Tracker();
                        mTracker.setId(TrackerObj.getJSONObject(i).getString("ID"));
                        mTracker.setNom(TrackerObj.getJSONObject(i).getString("NOM"));
                        mTracker.setPrenom(TrackerObj.getJSONObject(i).getString("PRENOM"));
                        mTracker.setAge(TrackerObj.getJSONObject(i).getString("AGE"));
                        if(TrackerObj.getJSONObject(i).getInt("GENDRE")==1)
                            mTracker.setGender("Male");
                        else
                            mTracker.setGender("Female");
                        list.add(mTracker);
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
            return list;
        }


        return list;
    }

}
