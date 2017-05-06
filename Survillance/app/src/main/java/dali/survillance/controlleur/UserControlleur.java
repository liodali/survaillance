package dali.survillance.controlleur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import dali.survillance.other.Constant;
import dali.survillance.other.JSONparser;
import dali.survillance.model.Utilisateur;

/**
 * Created by Mohamed ali on 14/01/2017.
 */

public class UserControlleur {

    public static Utilisateur Connection(String email,String pwd){
        Utilisateur u=new Utilisateur();
        try {
            String data  = URLEncoder.encode("email", "iso-8859-1") + "=" + URLEncoder.encode(email, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("pwd", "iso-8859-1") + "=" + URLEncoder.encode(pwd, "iso-8859-1");


            String url= Constant.server+"LoginUser.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){
                    Log.i("val",jsObject.toString());
                    String val=jsObject.getString("user");
                    JSONArray userObj=new JSONArray(val);
                    u.setId(userObj.getJSONObject(0).getString("ID"));
                    u.setNom(userObj.getJSONObject(0).getString("NOM"));
                    u.setPrenom(userObj.getJSONObject(0).getString("PRENOM"));
                    u.setAge(userObj.getJSONObject(0).getString("AGE"));
                    u.setEmail(email);
                    if(userObj.getJSONObject(0).getInt("GENDRE")==1)
                        u.setGendre("Male");
                    else
                        u.setGendre("Female");
                    u.setCode_admin(userObj.getJSONObject(0).getString("CODE"));
                    return u;
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

    public static Utilisateur getUser(String id){
        Utilisateur u=new Utilisateur();
        try {
            String data  = URLEncoder.encode("id", "iso-8859-1") + "=" + URLEncoder.encode(id, "iso-8859-1");


            String url= Constant.server+"getUser.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){

                    String val=jsObject.getString("user");
                    JSONArray userObj=new JSONArray(val);
                    u.setId(userObj.getJSONObject(0).getString("ID"));
                    u.setNom(userObj.getJSONObject(0).getString("NOM"));
                    u.setPrenom(userObj.getJSONObject(0).getString("PRENOM"));
                    u.setAge(userObj.getJSONObject(0).getString("AGE"));
                    u.setEmail(userObj.getJSONObject(0).getString("EMAIL"));
                    if(userObj.getJSONObject(0).getInt("GENDRE")==1)
                        u.setGendre("Male");
                    else
                        u.setGendre("Female");
                    u.setCode_admin(userObj.getJSONObject(0).getString("CODE"));
                    return u;
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


    public static boolean Inscription(Utilisateur u,String pwd){
        try {
            String data  = URLEncoder.encode("email", "iso-8859-1") + "=" + URLEncoder.encode(u.getEmail(), "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("pwd", "iso-8859-1") + "=" + URLEncoder.encode(pwd, "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("nom", "iso-8859-1") + "=" + URLEncoder.encode(u.getNom(), "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("prenom", "iso-8859-1") + "=" + URLEncoder.encode(u.getPrenom(), "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("age", "iso-8859-1") + "=" + URLEncoder.encode(u.getAge(), "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("gender", "iso-8859-1") + "=" + URLEncoder.encode(u.getGendre(), "iso-8859-1")+
                    "&"+
                    URLEncoder.encode("code", "iso-8859-1") + "=" + URLEncoder.encode(u.getCode_admin(), "iso-8859-1");


            String url= Constant.server+"inscription.php";

            JSONparser J=new JSONparser();
            String resURL=J.makeHttpRequest_Post(url,data);

            try {

                JSONObject jsObject=new JSONObject(resURL);
                int result=jsObject.getInt("message");
                if(result==1){

                    return true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("errJSONExcep",e.toString());
                Log.i("errJSONExcep",resURL);
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return false;
    }


}
