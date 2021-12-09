package com.moi.gov.gh.convic_19.models;

public class Users {
    private String id;
    private String name;
    private String email;
    private String dateofbirth;
    private String address;
    private String phone;
    private String hosname;
    private String imageurl;
    private String role;
    private String seacrh;
    private String date;

    public Users() {

    }

    public Users(String id, String name, String email, String dateofbirth, String address, String phone, String hosname, String imageurl, String role, String seacrh, String date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateofbirth = dateofbirth;
        this.address = address;
        this.phone = phone;
        this.hosname = hosname;
        this.imageurl = imageurl;
        this.role = role;
        this.seacrh = seacrh;
        this.date = date;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSeacrh() {
        return seacrh;
    }

    public void setSeacrh(String seacrh) {
        this.seacrh = seacrh;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", role='" + role + '\'' +
                ", seacrh='" + seacrh + '\'' +
                '}';
    }
}
