package com.campers.ground;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CampersData {

    private static final String TAG = "CampersData";



    private List<ListInfo> arrayList = new ArrayList<>();

    public CampersData(List<ListInfo> arrayList) {
        this.arrayList = arrayList;

        Log.d(TAG,"arrayList"+arrayList);
    }

    public List<ListInfo> getAllCampers() {
        return arrayList;
    }

    public void setArrayList(List<ListInfo> arrayList) {
        this.arrayList = arrayList;
    }

    public List<ListInfo> getPriceFilteredCampers(List<String> price, List<ListInfo> arrayList) {
        List<ListInfo> tempList = new ArrayList<>();
        for (ListInfo campers : arrayList) {
            for (String p : price) {
                if (campers.getPrice().equalsIgnoreCase(p)) {
                    tempList.add(campers);
                }
            }
        }
        return tempList;

    }

    public List<ListInfo> getCategoryFilteredCampers(List<String> category, List<ListInfo> arrayList) {
        List<ListInfo> tempList = new ArrayList<>();
        for (ListInfo campers : arrayList) {
            for (String c : category) {
                if (campers.getPrice().equalsIgnoreCase(c)) {
                    tempList.add(campers);


                }
            }
        }
        return tempList;


    }

    public List<String> getUniquePriceKeys(){
        List<String> prices = new ArrayList<>();
        Log.d(TAG, "prices: " + prices);

        for(ListInfo campers : arrayList){
            if(!prices.contains(campers.getPrice())){
                prices.add(campers.getPrice());
            }
        }
        Collections.sort(prices);
        Log.d(TAG, "prices1: " + prices);
        return prices;
    }

    public List<String> getUniqueCategoryKeys(){
        List<String> categorys = new ArrayList<>();
        Log.d(TAG, "categorys: " + categorys);
        for(ListInfo campers : arrayList){
            if(!categorys.contains(campers.getCategory())){
                categorys.add(campers.getCategory());
            }
        }
        Collections.sort(categorys);
        Log.d(TAG, "categorys: " + categorys);
        return categorys;
    }




}
