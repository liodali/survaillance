package dali.survillance.controlleur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import dali.survillance.model.Call;
import dali.survillance.other.Constant;
import dali.survillance.other.JSONparser;

/**
 * Created by Mohamed ali on 19/01/2017.
 */

public class CallsController {



    public static ArrayList<Call> GetCalls(String id){
        ArrayList<Call> Clist=new ArrayList<>();
        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "=" + URLEncoder.encode(id, "iso-8859-1");


            String url= Constant.server+"getCallsUser.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                Log.i("res","res:"+resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("calls");
                    JSONArray userObj=new JSONArray(val);
                    for(int i=0;i<userObj.length();i++){
                        Call c=new Call();
                        c.setId(userObj.getJSONObject(i).getString("ID"));
                        c.setNom(userObj.getJSONObject(i).getString("NOM"));
                        c.setDate(userObj.getJSONObject(i).getString("DATES"));
                        c.setPhone(userObj.getJSONObject(i).getString("PHONE"));
                        c.setDure(userObj.getJSONObject(i).getString("TEMPS"));

                        Clist.add(c);
                    }

                    return Clist;
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

    public static boolean StorageCall(String date,String temps,String filename,String user_id,String nom,String phone){

        try {
            String data  = URLEncoder.encode("user_id", "iso-8859-1") + "=" + URLEncoder.encode(user_id, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("nom", "iso-8859-1") + "=" + URLEncoder.encode(nom, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("phone", "iso-8859-1") + "=" + URLEncoder.encode(phone, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("dates", "iso-8859-1") + "=" + URLEncoder.encode(date, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("temps", "iso-8859-1") + "=" + URLEncoder.encode(temps, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("filename", "iso-8859-1") + "=" + URLEncoder.encode(filename, "iso-8859-1");


            String url= Constant.server+"insertCalls.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_PostSpecial(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){



                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("errJSONExcep",e.toString());

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }


        return false;
    }
    public static String GetLinkAudioCalls(String id){

        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "=" + URLEncoder.encode(id, "iso-8859-1");


            String url= Constant.server+"afficheAudio.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);
            Log.i("res","res:"+resURL);
            try {

                JSONObject jsObject=new JSONObject(resURL);

                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("file");

                    return val;
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
}
