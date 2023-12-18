package com.example.myapplication.entities;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String uuid;
    private String email;
    private String gender;
    private String password;
    private String phone;
    private String address;
    private Uri avatar;
    private String birthday;
    private String role;
    private String createDate;
    private List<String> invoiceIds;

    public User() {
        this.uuid = "";
        this.email = "";
        this.gender = "";
        this.password = "";
        this.phone = "";
        this.address = "";
        this.birthday = "";
        this.role = "";
        this.avatar = new Uri.Builder().build();
        this.createDate = "";
        this.invoiceIds = new ArrayList<>();
    }

    public User(String uuid, String email, String gender, String password, String phone, String address, String birthday, String role, Uri avatar, String createDate, List<String> invoiceIds) {
        this.uuid = uuid;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.role = String.valueOf(role);
        this.avatar = avatar;
        this.createDate = createDate;
        this.invoiceIds = invoiceIds;
    }
    public User(User user){
        this(
            user.getUuid(),
            user.getName(),
            user.getGender(),
            user.getPassword(),
            user.getPhone(),
            user.getAddress(),
            user.getBirthday(),
            user.getRole(),
            user.getAvatar(),
            user.getCreateDate(),
            user.getInvoiceIds()
        );
    }
    public User(HashMap<String, Object> userMap){
        this(
            userMap.get("uuid").toString(),
            userMap.get("email").toString(),
            userMap.get("gender").toString(),
            userMap.get("password").toString(),
            userMap.get("phone").toString(),
            userMap.get("address").toString(),
            userMap.get("birthday").toString(),
            userMap.get("role").toString(),
            (Uri)userMap.get("avatar"),
            userMap.get("createDate").toString(),
            (List<String>)userMap.get("invoiceIds")
        );
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if(avatar == null){
            result.put("uuid", uuid);
            result.put("email", email);
            result.put("phone", phone);
            result.put("password", password);
            result.put("gender", gender);
            result.put("address", address);
            result.put("birthday", birthday);
            result.put("role", role);
            result.put("avatar", "");
            result.put("createDate", createDate);
            result.put("invoiceIds", invoiceIds);
        }else{
            result.put("uuid", uuid);
            result.put("email", email);
            result.put("phone", phone);
            result.put("password", password);
            result.put("gender", gender);
            result.put("address", address);
            result.put("birthday", birthday);
            result.put("role", role);
            result.put("avatar", avatar.toString());
            result.put("createDate", createDate);
            result.put("invoiceIds", invoiceIds);
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
        return email;
    }

    public void setName(String email) {
        this.email = email;
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

    public String getCreateDate() { return createDate; }

    public void setCreateDate(String createDate) { this.createDate = createDate; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(List<String> invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", avatar=" + avatar +
                ", birthday='" + birthday + '\'' +
                ", role='" + role + '\'' +
                ", createDate='" + createDate + '\'' +
                ", invoiceIds=" + invoiceIds +
                '}';
    }
}