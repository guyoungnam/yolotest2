package com.campers.ground;

import java.util.ArrayList;

public class ListInfo  {

    private String price;
    private String category;
    private String address;
    private String location_name;
    private double latitude;
    private double longitude;
    private String photoUrl;
    private String description;
    private String end_data;
    private String start_data;


    private ListInfo(){}


    public ListInfo(String price, String category, String address, String location_name, double latitude, double longitude, String photoUrl,String description,String end_data, String start_data ) {
        this.price = price;
        this.category = category;
        this.address = address;
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUrl = photoUrl;
        this.description = description;
        this.end_data = end_data;
        this.start_data = start_data;


    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_data() {
        return end_data;
    }

    public void setEnd_data(String end_data) {
        this.end_data = end_data;
    }

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
    }

    // 입력받은 숫자의 리스트생성
    public static ArrayList<ListInfo> createContactsList(int numContacts) {
        ArrayList<ListInfo> contacts = new ArrayList<ListInfo>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new ListInfo("1234", "콘서트","서울 서대문구 연세로 50","금호아트홀 연세",37.56,126.7,"https://firebasestorage.googleapis.com/v0/b/yoloday-4adf8.appspot.com/o/2020_1_00001.jpg?alt=media&token=16cccddc-b9f7-4bfd-8ed1-e8fc6baebb71","1","2","3"));
        }

        return contacts;
    }
}

