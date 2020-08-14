package com.yoloday.yolotest2.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yoloday.yolotest2.R;
import com.yoloday.yolotest2.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
    }

    private void init() {


        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();



    }


}
