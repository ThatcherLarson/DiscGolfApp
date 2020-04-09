package models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DiscMap implements Parcelable {

    private String address;
    private String title;
    private String description;
    private Drawable image;
    private float longitude;
    private float latitude;
    private ArrayList<Integer> pars;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public DiscMap(String address, String title, String description, float longitude, float latitude, ArrayList<Integer> pars,Drawable image){
        this.address = address;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pars = pars;
        this.image = image;
    }
    public DiscMap(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Integer> getPars() {
        return pars;
    }

    public void setPars(ArrayList<Integer> pars) {
        this.pars = pars;
    }

    public static Creator<DiscMap> getCREATOR() {
        return CREATOR;
    }

    protected DiscMap(Parcel in){
        address = in.readString();
        longitude = in.readFloat();
        latitude = in.readFloat();
        pars = in.readArrayList(String.class.getClassLoader());

    }

    public static final Creator<DiscMap> CREATOR = new Creator<DiscMap>() {
        @Override
        public DiscMap createFromParcel(Parcel in) {
            return new DiscMap(in);
        }

        @Override
        public DiscMap[] newArray(int size) {
            return new DiscMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
        //dest.writeArrayList(pars);

    }
}
