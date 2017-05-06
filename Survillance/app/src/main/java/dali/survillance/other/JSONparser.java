package dali.survillance.other;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by Mohamed ali on 14/01/2017.
 */

public class JSONparser {
    static String json = "";

    // constructor
    public JSONparser() {

    }


    public String makeHttpRequest(String url){

        try {
            URL theurl = new URL(url);
            Log.i("url_parse",url);
            BufferedReader buff=new BufferedReader(new InputStreamReader(theurl.openConnection().getInputStream() ,"iso-8859-1"));
            json=buff.readLine();
            Log.i("json","j"+json);
            buff.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
    public String makeHttpRequest_Post(String url,String data ){

        try {
            URL  theurl = new URL(url);

            URLConnection conn = theurl.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader buff=new BufferedReader(new InputStreamReader(conn.getInputStream() ,"utf-8"));
            //Log.i("buff","b:"+buff.readLine().toString());
            json=buff.readLine();


            buff.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
    public String makeHttpRequest_PostSpecial(String url,String data ){

        try {
            URL  theurl = new URL(url);

            URLConnection conn = theurl.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader buff=new BufferedReader(new InputStreamReader(conn.getInputStream() ,"utf-8"));
            //Log.i("buff","b:"+buff.readLine().toString());
            json=buff.readLine();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = buff.readLine()) != null) {
                sb.append(line);
            }
            json=sb.toString();
            Log.i("json","j"+sb);
            buff.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
