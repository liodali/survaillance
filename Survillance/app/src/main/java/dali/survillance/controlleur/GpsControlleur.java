package dali.survillance.controlleur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import dali.survillance.model.GPS;
import dali.survillance.model.Tracker;
import dali.survillance.other.Constant;
import dali.survillance.other.JSONparser;

/**
 * Created by Mohamed ali on 25/01/2017.
 */

public class GpsControlleur {


    public static void sendPosition(GPS gps){

        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(gps.getTracker().getId(), "iso-8859-1")+"&"+
                    URLEncoder.encode("lon", "iso-8859-1") + "="
                    + URLEncoder.encode(gps.getLon(), "iso-8859-1")+"&"+
                    URLEncoder.encode("lat", "iso-8859-1") + "="
                    + URLEncoder.encode(gps.getLat(), "iso-8859-1");


            String url= Constant.server+"makeGPS.php";

            JSONparser J=new JSONparser();
            J.makeHttpRequest_Post(url,data);
            Log.i("gps","gps");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NullPointerException e){

        }



    }
    public static ArrayList<GPS> TrackPosition(String id){
        ArrayList<GPS> list =new ArrayList<>();
        try {
            String data  = URLEncoder.encode("id", "UTF-8") + "="
                    + URLEncoder.encode(id, "UTF-8") ;


            String url= Constant.server+"Trackinggps.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_PostSpecial(url,data);
            Log.i("val","v:"+resURL.toString());
            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("gps");
                    JSONArray TrackerObj=new JSONArray(val);
                    for (int i=0;i<TrackerObj.length();i++){
                       GPS gps =new GPS();
                        gps.setId(TrackerObj.getJSONObject(i).getString("ID"));
                        gps.setLon(TrackerObj.getJSONObject(i).getString("LON"));
                        gps.setLat(TrackerObj.getJSONObject(i).getString("LAT"));
                        Tracker tracker=new Tracker();
                        tracker.setNom(TrackerObj.getJSONObject(i).getString("NOM"));
                        tracker.setPrenom(TrackerObj.getJSONObject(i).getString("PRENOM"));
                        tracker.setId(TrackerObj.getJSONObject(i).getString("ID_TRACKER"));
                        gps.setTracker(tracker);
                        list.add(gps);
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
    public static void TrackerRead(String gps_id){

        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "="
                    + URLEncoder.encode(gps_id, "iso-8859-1") ;


            String url= Constant.server+"updateGPS.php";

            JSONparser J=new JSONparser();
            J.makeHttpRequest_Post(url,data);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NullPointerException e){

        }



    }

}
