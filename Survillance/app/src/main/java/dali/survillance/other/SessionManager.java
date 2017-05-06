package dali.survillance.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import dali.survillance.activity.Connect;


/**
 * Created by Mohamed ali on 22/06/2016.
 */
public class SessionManager {
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SURV_Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User ID (make variable public to access from outside)
    public static final String KEY_ID = "id";

    // USER NAME (make variable public to access from outside)
    public static final String KEY_NAME = "NAME";
    public static final String KEY_TRACK = "TRACKER";
    // USER NAME (make variable public to access from outside)
    public static final String KEY_STAT = "stat";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id, String name,String stat){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing ID in pref
        editor.putString(KEY_ID, id);

        // Storing NAME in pref
        editor.putString(KEY_NAME, name);

        editor.putString(KEY_STAT, stat);

        // commit changes
        editor.commit();
    }
    public void creatTrackerSession(String id,String stat, String id_tracker){

        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing ID in pref
        editor.putString(KEY_ID, id);
        editor.putString(KEY_STAT, stat);
        // Storing NAME in pref
        editor.putString(KEY_TRACK, id_tracker);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Connect.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user ID
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user NAME
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // return user
        return user;
    }
    public HashMap<String, String> getUserStat(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user ID
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user NAME
        user.put(KEY_STAT, pref.getString(KEY_STAT, null));

        // return user
        return user;
    }
    public HashMap<String, String> getUserTracker(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user ID
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user NAME
        user.put(KEY_TRACK, pref.getString(KEY_TRACK, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
       Intent i = new Intent(_context, Connect.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
