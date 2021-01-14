package com.campers.ground;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CampersData {

    private List<ListInfo> arrayList = new ArrayList<>();

    public CampersData(List<ListInfo> arrayList) {
        this.arrayList = arrayList;
    }

    public List<ListInfo> getAllCampers() {
        return arrayList;
    }

    public void setArrayList(List<ListInfo> arrayList) {
        this.arrayList = arrayList;
    }

    public List<String> getUniquePriceKeys() {
        List<String> price = new ArrayList<>();
        for (ListInfo campers : arrayList) {

            if(!price.contains(campers.getPrice()+"")){
                price.add(campers.getPrice()+"");
            }

        }

        Collections.sort(price);
        return price;
    }



}
