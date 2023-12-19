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
    private String fullname;
    private String createDate;
    private List<String> invoiceIds;

    public User() {
        this.uuid = "";
        this.email = "";
        this.password = "";
        this.phone = "";
        this.address = "";
        this.fullname = "";
        this.avatar = new Uri.Builder().build();
        this.createDate = "";
        this.invoiceIds = new ArrayList<>();
    }

    public User(String uuid, String email, String password, String phone, String address, String gender, String birthday, String fullname, Uri avatar, String createDate, List<String> invoiceIds) {
        this.uuid = uuid;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.fullname = String.valueOf(fullname);
        this.avatar = avatar;
        this.createDate = createDate;
        this.invoiceIds = invoiceIds;
    }
    public User(String uuid, String email, String password, String phone, String address, String fullname, String createDate, List<String> invoiceIds) {
        this.uuid = uuid;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.fullname = String.valueOf(fullname);
        this.createDate = createDate;
        this.invoiceIds = invoiceIds;
    }
    public User(User user){
        this(
            user.getUuid(),
            user.getName(),
            user.getPassword(),
            user.getPhone(),
            user.getAddress(),
            user.getGender(),
            user.getBirthday(),
            user.getFullname(),
            user.getAvatar(),
            user.getCreateDate(),
            user.getInvoiceIds()
        );
    }
    public User(HashMap<String, Object> userMap){
        if(userMap.get("avatar").toString().equals("")) {
            this.uuid = userMap.get("uuid").toString();
            this.email = userMap.get("email").toString();
            this.password = userMap.get("password").toString();
            this.phone = userMap.get("phone").toString();
            this.address = userMap.get("address").toString();
            this.gender = userMap.get("gender").toString();
            this.birthday = userMap.get("birthday").toString();
            this.fullname = userMap.get("fullname").toString();
            this.avatar = new Uri.Builder().build();
            this.createDate = userMap.get("createDate").toString();
            this.invoiceIds = (List<String>) userMap.get("invoiceIds");
        } else{
            this.uuid = userMap.get("uuid").toString();
            this.email = userMap.get("email").toString();
            this.password = userMap.get("password").toString();
            this.phone = userMap.get("phone").toString();
            this.address = userMap.get("address").toString();
            this.gender = userMap.get("gender").toString();
            this.birthday = userMap.get("birthday").toString();
            this.fullname = userMap.get("fullname").toString();
            this.avatar = Uri.parse(userMap.get("avatar").toString());
            this.createDate = userMap.get("createDate").toString();
            this.invoiceIds = (List<String>) userMap.get("invoiceIds");
        }
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if(avatar == null){
            result.put("uuid", uuid);
            result.put("email", email);
            result.put("phone", phone);
            result.put("password", password);
            result.put("address", address);
            result.put("gender", gender);
            result.put("birthday", birthday);
            result.put("fullname", fullname);
            result.put("avatar", "");
            result.put("createDate", createDate);
            result.put("invoiceIds", invoiceIds);
        }else{
            result.put("uuid", uuid);
            result.put("email", email);
            result.put("phone", phone);
            result.put("password", password);
            result.put("address", address);
            result.put("gender", gender);
            result.put("birthday", birthday);
            result.put("fullname", fullname);
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", avatar=" + avatar +
                ", fullname='" + fullname + '\'' +
                ", createDate='" + createDate + '\'' +
                ", invoiceIds=" + invoiceIds +
                '}';
    }
}