package com.campers.ground.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.campers.ground.CustomDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.campers.ground.R;
import com.google.android.gms.maps.OnMapReadyCallback;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "EventDetailActivity";

    private CustomDialog customDialog;

    private MapView mapView;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetail);

        mapView=findViewById(R.id.mapView2);
        mapView.getMapAsync(this);

        findViewById(R.id.btn_popup).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                customDialog = new CustomDialog(EventDetailActivity.this);
                customDialog.show();

            }
        });




        getIncomingIntent();

    }

    private void getIncomingIntent(){
        Log.d(TAG,"getIncomingIntent : checking for incoming intents");
        if(getIntent().hasExtra("photoUrl")&&getIntent().hasExtra("title")&&getIntent().hasExtra("address")){
            Log.d(TAG,"getIncomingIntent : found intent extras");

            String imageUrl = getIntent().getStringExtra("photoUrl");
            String title = getIntent().getStringExtra("title");
            String address = getIntent().getStringExtra("address");
            String location_name = getIntent().getStringExtra("location_name");
            String description = getIntent().getStringExtra("description");
            String start_data =getIntent().getStringExtra("start_data");
            String end_data = getIntent().getStringExtra("end_data");

            setImage(imageUrl,title,address,location_name,description,start_data,end_data);
        }

    }

    private void setImage(String imageUrl, String title,String address,String location_name,String description,String start_data, String end_data ){
        Log.d(TAG, "setImage: 이미지, 이름 가져오기");

        TextView addressdetail = findViewById(R.id.addressdetail);
        TextView location = findViewById(R.id.location_name);
        TextView startdata = findViewById(R.id.price1);
        TextView enddata = findViewById(R.id.enddata);



        addressdetail.setText(address);
        location.setText(location_name);
        startdata.setText(start_data);
        enddata.setText(end_data);


        ImageView photoUrldetail = findViewById(R.id.photoUrldetail);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(photoUrldetail);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }
}
