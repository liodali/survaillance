package dali.survillance.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 24/01/2017.
 */
interface SubPersonne extends Parcelable {
    // Other interface methods here
}
public abstract class Personne implements SubPersonne {

    private String id;
    private String nom;
    private String prenom;
    private String age;
    private String gendre;

    public Personne() {
    }

    public Personne(String id, String nom, String prenom, String age, String gendre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.gendre = gendre;
    }

    protected Personne(Parcel in) {
        id = in.readString();
        nom = in.readString();
        prenom = in.readString();
        age = in.readString();
        gendre = in.readString();

    }



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

    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(age);
        dest.writeString(gendre);

    }
}
