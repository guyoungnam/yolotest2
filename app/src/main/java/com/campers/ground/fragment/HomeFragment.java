package com.campers.ground.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.campers.ground.CampersData;
import com.campers.ground.SharedViewModel;
import com.campers.ground.activity.EventDetailActivity;
import com.campers.ground.activity.FilterActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;
import com.campers.ground.ListInfo;
import com.campers.ground.MyItem;
import com.campers.ground.R;
import com.campers.ground.adapter.HomeAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback, AAH_FabulousFragment.Callbacks,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, SlidingPaneLayout.PanelSlideListener, AAH_FabulousFragment.AnimationListener {

    private static final String TAG = "HomeFragment";

    private MapView mapView;
    private RecyclerView recyclerView;


    private ArrayList<ListInfo> arrayList;
    private HomeAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private ChildEventListener childEventListener;

    private FragmentActivity mContext;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private Marker currentMarker = null;


    private ClusterManager<MyItem> clusterManager;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;

    private LatLng previousLocation,currentLocation;

    private Button mBtnOpenPartly;
    private Button mBtnClose;

    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private ImageButton filterImage;

    private FilterActivity filterActivity;

    private FloatingActionButton fab2;


    private MyFabFragment dialogFrag;


    private SharedViewModel sharedViewModel;
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>(

    );

    private CampersData cData;


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


       filterImage =rootView.findViewById(R.id.filterImage);
       filterImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), FilterActivity.class);
               startActivity(intent);
           }
       });


       fab2 = (FloatingActionButton) rootView.findViewById(R.id.fab2);

       dialogFrag = MyFabFragment.newInstance();
       dialogFrag.setParentFab(fab2);
       fab2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialogFrag.show(getFragmentManager(), dialogFrag.getTag());

               DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

               Bundle bundle = new Bundle();
               bundle.putString("db", String.valueOf(db));


           }
       });



        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOverScrollMode(recyclerView.OVER_SCROLL_NEVER);

        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.getMapAsync(this);

        arrayList = new ArrayList<>();

        datafromfirebase();

        adapter = new HomeAdapter(arrayList, getContext(),getActivity());
        recyclerView.setAdapter(adapter);



        return rootView;
    }


    //데이터를 가져옵니다
    private void datafromfirebase(){

        Log.d(TAG, "datafromfirebase : call");

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

                    Log.d(TAG, "adapter : call" +adapter);
                    Log.d(TAG, "arrayList : call" +arrayList);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("Homefragment",String.valueOf(databaseError.toException())); // 에러문 출

            }
        });

    }

    @Override
    public void onResult(Object result) {

        Log.d("k9res", "onResult: " + result.toString());

        if(result.toString().equals("swiped_down")){

        }else {
            if(result !=null){
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if(applied_filters.size() !=0){
                    //List<ListInfo> filterdList = cData.getAllCampers();

                    for(Map.Entry<String, List<String>> entry : applied_filters.entrySet()){
                        Log.d("k9res", "entry.key: " + entry.getKey());
                        switch (entry.getKey()){
                            case "price":
                              //  filterdList = cData.getPriceFilteredCampers(entry.getValue(), filterdList);
                                break;
                            case "category":
                              //  filterdList = cData.getCategoryFilteredCampers(entry.getValue(), filterdList);
                                break;
                        }
                    }
                 //   Log.d("k9res","new size:"+ filterdList.size());

                }
            }
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }

    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    public CampersData getcData(){
        return cData;
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
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if(mMap == null){
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();
        if(mGoogleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        }
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

        Log.d(TAG, "onMapReady:");

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMarkerClickListener(this);
        LatLng default_location = new LatLng(37.56, 126.97);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(default_location,18));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        //파이어베이스에서 가져온 위도/경도를 가져옵니다

        //setDefaultLocation();
        //GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요합니다



      mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        //왜 마커 클러스터링이 안되지?
        clusterManager = new ClusterManager<>(getContext(),googleMap);

        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);


        addMarkersToMap(googleMap);


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())

                 .addConnectionCallbacks(this)
                 .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                 .addApi(LocationServices.API)
                 .build();
        mGoogleApiClient.connect();
    }


        //GPS를 찾지 못하는 장소에 있을 경우 지도의 초기 위치가 필요합니
        private void setDefaultLocation() {

            //if(currentMarker !=null) currentMarker.remove();

            LatLng default_location = new LatLng(37.56, 126.97);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(default_location);
            markerOptions.title("위치정보 가져올 수 없음");
            markerOptions.snippet("위치 퍼미션과 GPS 활성 여부 확인하세요");
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentMarker = mMap.addMarker(markerOptions);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(default_location, 15);
            mMap.moveCamera(cameraUpdate);

        }



    private void addMarkersToMap(final GoogleMap googleMap) {

        Log.d(TAG, "addMarkersToMap: call");

        DatabaseReference positionRef = FirebaseDatabase.getInstance().getReference("event");

        childEventListener = positionRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ListInfo listInfo1 = snapshot.getValue(ListInfo.class);
                double latitude = listInfo1.getLatitude();
                double longitude = listInfo1.getLongitude();
                String price = listInfo1.getPrice();
                LatLng location = new LatLng(latitude,longitude);

                googleMap.addMarker(new MarkerOptions().position(location).title(price));


                Log.d(TAG, "addMarkersToMap:"+location+price);



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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,locationCallback, Looper.myLooper());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d(TAG, "onLocationChanged: call");


        if(mCurrLocationMarker !=null){
            mCurrLocationMarker.remove();
        }

        //현재 위치 표시
  /*      LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("현재위치");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));*/


        

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){

                new AlertDialog.Builder(getActivity())
                        .setTitle("위치 인증이 필요합니다")
                        .setMessage("이 앱은 위치 인증 필요합니")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            }else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResults){

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        if(mGoogleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }else {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onPanelSlide(@NonNull View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(@NonNull View panel) {

    }

    @Override
    public void onPanelClosed(@NonNull View panel) {

    }


    @Override
    public void onOpenAnimationStart() {

    }

    @Override
    public void onOpenAnimationEnd() {

    }

    @Override
    public void onCloseAnimationStart() {

    }

    @Override
    public void onCloseAnimationEnd() {

    }


}
