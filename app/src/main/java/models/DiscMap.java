package models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class DiscMap implements Parcelable {

    private String address;
    private String title;
    private String description;
    private Drawable image;
    private String id;
    private double longitude;
    private double latitude;
    private ArrayList<Integer> pars;
    private ArrayList<Integer> yards;
    //USERS
    private double milesAway;
    private boolean favorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
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

    public double getMilesAway(){
        return milesAway;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }

    public boolean getFavorite(){
        return favorite;
    }
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
    public void setMilesAway(double currentLongitude,double currentLatitude){
        milesAway = distance(currentLatitude,latitude,currentLongitude,longitude,0.0,0.0)*(0.000621371);
    }

    public double calculateMilesAway(double currentLongitude,double currentLatitude){
        return (double)Math.sqrt(Math.pow(longitude-currentLongitude,2.0)+Math.pow(latitude-currentLatitude,2.0));
    }

    public DiscMap(String address, String title, String description, float longitude, float latitude, ArrayList<Integer> pars, ArrayList<Integer> yards,Drawable image){
        this.address = address;
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pars = pars;
        this.yards = yards;
        this.image = image;
    }
    public DiscMap(String title, String description, float longitude, float latitude, ArrayList<Integer> pars, ArrayList<Integer> yards,Drawable image){
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pars = pars;
        this.yards = yards;
        this.image = image;
    }
    public DiscMap(String id, String title, String description, GeoPoint geoPoint, ArrayList<Integer> pars, ArrayList<Integer> yards){
        this.id = id;
        this.title = title;
        this.description = description;
        longitude = geoPoint.getLongitude();
        latitude = geoPoint.getLatitude();
        this.pars = pars;
        this.yards = yards;
    }
    public DiscMap(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
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

    public Integer getNumPars() {
        return pars.size();
    }

    public ArrayList<Integer> getYards() {
        return yards;
    }

    public void setYards(ArrayList<Integer> yards) {
        this.yards = yards;
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
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        //dest.writeArrayList(pars);

    }
}
