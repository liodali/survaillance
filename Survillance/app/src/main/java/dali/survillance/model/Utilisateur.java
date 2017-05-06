package dali.survillance.model;

import android.os.Parcel;

/**
 * Created by Mohamed ali on 14/01/2017.
 */

public class Utilisateur extends Personne {


    private String email;

    private String code_admin;


    public Utilisateur() {
    }

    public Utilisateur(String id, String nom, String prenom, String age, String gendre, String email, String code_admin) {
        super(id, nom, prenom, age, gendre);
        this.email = email;
        this.code_admin = code_admin;
    }

    protected Utilisateur(Parcel in) {
        super(in);
        email = in.readString();

        code_admin = in.readString();
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };



    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getCode_admin() {
        return code_admin;
    }

    public void setCode_admin(String code_admin) {
        this.code_admin = code_admin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(email);

        dest.writeString(code_admin);
    }
}
