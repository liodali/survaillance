package dali.survillance.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 22/01/2017.
 */

public class Tracker  implements Parcelable{


    private String id;
    private String Nom;
    private String prenom;
    private String age;
    private String gender;
    private Utilisateur user;
    private String code;

    public Tracker() {
    }

    public Tracker(String id, String nom, String prenom, String age, String gender, Utilisateur user, String code) {
        this.id = id;
        Nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.gender = gender;
        this.user = user;
        this.code = code;
    }

    protected Tracker(Parcel in) {
        id = in.readString();
        Nom = in.readString();
        prenom = in.readString();
        age = in.readString();
        gender = in.readString();
        user = in.readParcelable(Utilisateur.class.getClassLoader());
        code = in.readString();
    }

    public static final Creator<Tracker> CREATOR = new Creator<Tracker>() {
        @Override
        public Tracker createFromParcel(Parcel in) {
            return new Tracker(in);
        }

        @Override
        public Tracker[] newArray(int size) {
            return new Tracker[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Nom);
        dest.writeString(prenom);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeParcelable(user, flags);
        dest.writeString(code);
    }
}
