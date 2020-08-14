package com.yoloday.yolotest2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yoloday.yolotest2.ListInfo;
import com.yoloday.yolotest2.R;
import com.yoloday.yolotest2.adapter.ListAdapter;





import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView = null;
    private RecyclerView recyclerView;

    private ArrayList<ListInfo> list = new ArrayList<>();

    private ListAdapter listadapter;


    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);
        final TextView title = rootView.findViewById(R.id.title);
        final TextView category = rootView.findViewById(R.id.category);
        final TextView location_name = rootView.findViewById(R.id.location_name);
        final ImageView profileImageView = rootView.findViewById(R.id.profileImageView);


        NestedScrollView bottomSheet =(NestedScrollView)rootView.findViewById(R.id.bottom_sheet);
        //BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        //behavior.setPeekHeight(100);


        mapView = (MapView)rootView.findViewById(R.id.map);
        mapView.getMapAsync(this);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        list = ListInfo.createContactsList(10);

        recyclerView.setHasFixedSize(true);

        listadapter = new ListAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listadapter);


        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("event").document("info");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            if(document.getData().get("photoUrl") != null){
                                //Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileImageView);
                            }
                            title.setText(document.getData().get("title").toString());
                            category.setText(document.getData().get("category").toString());
                            location_name.setText(document.getData().get("location_name").toString());

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //파이어베이스에서 가져온 위도/경도를 가져옵니다

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        markerOptions.title("서울");

        markerOptions.snippet("수도");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }
}
