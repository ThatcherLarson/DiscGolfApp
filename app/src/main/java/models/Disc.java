package models;

import java.io.Serializable;

public class Disc implements Serializable {
    private String avg_distance;
    private String color;
    private String manufacturer;
    private String name;
    private String type;
    private String uid;

    public Disc() {};

    public Disc(String avg_distance, String color, String manufacturer, String name, String type, String uid) {
        this.avg_distance = avg_distance;
        this.color = color;
        this.manufacturer = manufacturer;
        this.name = name;
        this.type = type;
        this.uid = uid;
    }

    public String getAvg_distance() {
        return avg_distance;
    }

    public void setAvg_distance(String avg_distance) {
        this.avg_distance = avg_distance;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
