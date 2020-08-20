package com.yoloday.yolotest2;

import java.util.ArrayList;

public class ListInfo {

    private String title;
    private String category;
    private String address;
    private String location_name;
    private double latitude;
    private double longitude;
    private String photoUrl;

    private ListInfo(){}


    public ListInfo(String title, String category, String address, String location_name, double latitude, double longitude, String photoUrl) {
        this.title = title;
        this.category = category;
        this.address = address;
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUrl = photoUrl;


        

        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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





    // 입력받은 숫자의 리스트생성
    public static ArrayList<ListInfo> createContactsList(int numContacts) {
        ArrayList<ListInfo> contacts = new ArrayList<ListInfo>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new ListInfo("맥도날", "콘서트","서울 서대문구 연세로 50","금호아트홀 연세",37.56,126.7,""));
        }

        return contacts;
    }
}

