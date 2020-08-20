package com.yoloday.yolotest2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.Query;
import com.yoloday.yolotest2.ListInfo;
import com.yoloday.yolotest2.R;
import com.yoloday.yolotest2.adapter.FragmentActionListener;
import com.yoloday.yolotest2.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private RecyclerView recyclerView;
   

    private ArrayList<ListInfo> arrayList;
    private ListAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private ChildEventListener childEventListener;


    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        NestedScrollView bottomSheet = (NestedScrollView) rootView.findViewById(R.id.bottom_sheet);
        //BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        //behavior.setPeekHeight(100);


        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.getMapAsync(this);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();

        datafromfirebase();

        adapter = new ListAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }



    //데이터를 가져옵니
    private void datafromfirebase(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    arrayList.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        ListInfo listInfo = snapshot.getValue(ListInfo.class);
                        arrayList.add(listInfo);

                    }
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("Homefragment",String.valueOf(databaseError.toException())); // 에러문 출

            }
        });


    }


    @Override
    public void onStart(){
        super.onStart();
        mapView.onStop();
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
    public void onMapReady(final GoogleMap googleMap) {

        //파이어베이스에서 가져온 위도/경도를 가져옵니다

        googleMap.setOnMarkerClickListener(this);

        LatLng latLng = new LatLng(37.56,126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        addMarkersToMap(googleMap);



    /*    LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");

        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));*/



    }




    private void addMarkersToMap(final GoogleMap googleMap) {

        DatabaseReference positionRef = FirebaseDatabase.getInstance().getReference("event");
       // final ListInfo listInfo = new ListInfo("","","","",0,0,"");
       // positionRef.push().setValue(listInfo);

        childEventListener = positionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ListInfo listInfo1 = snapshot.getValue(ListInfo.class);
                double latitude = listInfo1.getLatitude();
                double longitude = listInfo1.getLongitude();
                String location_name = listInfo1.getLocation_name();
                LatLng location = new LatLng(latitude,longitude);

                googleMap.addMarker(new MarkerOptions().position(location).title(location_name));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
