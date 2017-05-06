package dali.survillance.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 19/01/2017.
 */

public class Call implements Parcelable{

    private String id;
    private String nom;
    private String phone;
    private String date;
    private String dure;
    private Utilisateur user;

    public Call() {
    }

    public Call(String id, String nom, String phone, String date, String dure, Utilisateur user) {
        this.id = id;
        this.nom = nom;
        this.phone = phone;
        this.date = date;
        this.dure = dure;
        this.user = user;
    }

    protected Call(Parcel in) {
        id = in.readString();
        nom = in.readString();
        phone = in.readString();
        date = in.readString();
        dure = in.readString();
        user = in.readParcelable(Utilisateur.class.getClassLoader());
    }

    public static final Creator<Call> CREATOR = new Creator<Call>() {
        @Override
        public Call createFromParcel(Parcel in) {
            return new Call(in);
        }

        @Override
        public Call[] newArray(int size) {
            return new Call[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nom);
        dest.writeString(phone);
        dest.writeString(date);
        dest.writeString(dure);
        dest.writeParcelable(user, flags);
    }
}
