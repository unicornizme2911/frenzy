package com.example.myapplication.entities;

import java.util.HashMap;
import java.util.Map;

public class Theater {
    private String id;
    private String name;
    private String address;
    private String image;
    private String city;
    private String district;

    public Theater() {
        this.id = "";
        this.name = "";
        this.address = "";
        this.image = "";
        this.city = "";
        this.district = "";
    }

    public Theater(String id, String name, String address, String image, String city, String district) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.city = city;
        this.district = district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Theater(HashMap<String, Object> theaterMap) {
        this(
                theaterMap.get("id").toString(),
                theaterMap.get("name").toString(),
                theaterMap.get("address").toString(),
                theaterMap.get("image").toString(),
                theaterMap.get("city").toString(),
                theaterMap.get("district").toString()
        );
    }
    public Map<String, Object> toMap() {
        Map<String, Object> theaterMap = new HashMap<>();
        theaterMap.put("id", id);
        theaterMap.put("name", name);
        theaterMap.put("address", address);
        theaterMap.put("image", image);
        theaterMap.put("city", city);
        theaterMap.put("district", district);
        return theaterMap;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
