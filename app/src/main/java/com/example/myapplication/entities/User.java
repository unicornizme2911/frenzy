package com.example.myapplication.entities;

import android.net.Uri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String uuid;
    private String name;
    private String gender;
    private String password;
    private String phone;
    private String address;
    private Uri avatar;
    private String birthday;
    private String role;

    public User() {
        this.uuid = "";
        this.name = "";
        this.gender = "";
        this.password = "";
        this.phone = "";
        this.address = "";
        this.birthday = "";
        this.role = "";
        this.avatar = new Uri.Builder().build();
    }

    public User(String uuid, String name, String gender, String password, String phone, String address, String birthday, String role, Uri avatar) {
        this.uuid = uuid;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.role = String.valueOf(role);
        this.avatar = avatar;
    }

    public User(HashMap<String, Object> userMap){
        this(
            userMap.get("uuid").toString(),
            userMap.get("name").toString(),
            userMap.get("gender").toString(),
            userMap.get("password").toString(),
            userMap.get("phone").toString(),
            userMap.get("address").toString(),
            userMap.get("birthday").toString(),
            userMap.get("role").toString(),
            (Uri)userMap.get("avatar")
        );
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if(avatar == null){
            result.put("uuid", uuid);
            result.put("name", name);
            result.put("phone", phone);
            result.put("password", password);
            result.put("gender", gender);
            result.put("address", address);
            result.put("birthday", birthday);
            result.put("role", role);
            result.put("avatar", "");
        }else{
            result.put("uuid", uuid);
            result.put("name", name);
            result.put("phone", phone);
            result.put("password", password);
            result.put("gender", gender);
            result.put("address", address);
            result.put("birthday", birthday);
            result.put("role", role);
            result.put("avatar", avatar.toString());
        }
        return result;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Uri getAvatar() {
        return avatar;
    }

    public void setAvatar(Uri avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", avatar=" + avatar +
                ", birthday='" + birthday + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}