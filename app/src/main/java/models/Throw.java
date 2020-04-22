package models;


import com.google.firebase.firestore.GeoPoint;

public class Throw {
    private GeoPoint start;
    private GeoPoint end;

    public Throw(GeoPoint start, GeoPoint end){
        this.start = start;
        this.end = end;
    }
    public Throw(){

    }
    public GeoPoint get_start(){
        return  start;
    }
    public GeoPoint get_end(){
        return end;
    }
}
