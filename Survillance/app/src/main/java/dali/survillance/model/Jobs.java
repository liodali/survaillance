package dali.survillance.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 24/01/2017.
 */

public class Jobs implements Parcelable{

    private String id;
    private Tracker tracker;
    private String jobs;
    private boolean status;
    private String date;

    public Jobs() {
    }

    public Jobs(String id, Tracker tracker, String jobs, boolean status, String date) {
        this.id = id;
        this.tracker = tracker;
        this.jobs = jobs;
        this.status = status;
        this.date = date;
    }

    protected Jobs(Parcel in) {
        id = in.readString();
        tracker = in.readParcelable(Tracker.class.getClassLoader());
        jobs = in.readString();
        status = in.readByte() != 0;
        date = in.readString();
    }

    public static final Creator<Jobs> CREATOR = new Creator<Jobs>() {
        @Override
        public Jobs createFromParcel(Parcel in) {
            return new Jobs(in);
        }

        @Override
        public Jobs[] newArray(int size) {
            return new Jobs[size];
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

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(tracker, flags);
        dest.writeString(jobs);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(date);
    }
}
