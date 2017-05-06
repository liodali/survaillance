package dali.survillance.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 25/01/2017.
 */

public class GPS  implements Parcelable{

        private String id;
        private Tracker tracker;
        private String lat;
        private String lon;
        private boolean status;


    public GPS() {
    }

    public GPS(String id, Tracker tracker, String lat, String lon, boolean status) {
        this.id = id;
        this.tracker = tracker;
        this.lat = lat;
        this.lon = lon;
        this.status = status;
    }

    protected GPS(Parcel in) {
        id = in.readString();
        tracker = in.readParcelable(Tracker.class.getClassLoader());
        lat = in.readString();
        lon = in.readString();
        status = in.readByte() != 0;
    }

    public static final Creator<GPS> CREATOR = new Creator<GPS>() {
        @Override
        public GPS createFromParcel(Parcel in) {
            return new GPS(in);
        }

        @Override
        public GPS[] newArray(int size) {
            return new GPS[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(tracker, flags);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeByte((byte) (status ? 1 : 0));
    }
}
