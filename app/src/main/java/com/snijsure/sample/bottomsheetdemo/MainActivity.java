package com.snijsure.sample.bottomsheetdemo;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.snijsure.sample.bottomsheetdemo.ui.adapter.CuratedCollectionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.snijsure.sample.bottomsheetdemo.R.id.map_image_holder;
import static com.snijsure.sample.bottomsheetdemo.R.id.recycler_curate_images;
import static com.snijsure.sample.bottomsheetdemo.R.id.recycler_curate_images_second;
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {

    @BindView(R.id.layout_bottom_sheet) View mLayoutBottomSheet;
    @BindView(recycler_curate_images) RecyclerView mCuratedImagesRecycleView;
    @BindView(recycler_curate_images_second) RecyclerView mCuratedImagesRecycleView2;
    @BindView(R.id.show_view_btn) Button mShowViewBtn;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;

    private BottomSheetBehavior mBottomSheetBehavior;
    public static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mShowViewBtn.setVisibility(View.VISIBLE);
                    //mLayoutBottomSheet.getLayoutParams().height = mShowViewBtn.getHeight();
                    mCuratedImagesRecycleView.setVisibility(View.GONE);
                    mCuratedImagesRecycleView2.setVisibility(View.GONE);
                    mBottomSheetBehavior.setPeekHeight(0);
                }
                else if(newState == BottomSheetBehavior.STATE_EXPANDED ) {
                    mShowViewBtn.setVisibility(View.INVISIBLE);
                    mCuratedImagesRecycleView.setVisibility(View.VISIBLE);
                    mCuratedImagesRecycleView2.setVisibility(View.VISIBLE);
                    //mBottomSheetBehavior.setPeekHeight(400);
                } else if ( newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mShowViewBtn.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }


        });
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        LinearLayoutManager horizontalScroller = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        mCuratedImagesRecycleView.setLayoutManager(horizontalScroller);
        String[] curatedImages = getResources().getStringArray(R.array.curated_collection_images);
        CuratedCollectionAdapter curatedCollectionAdapter = new CuratedCollectionAdapter(
                curatedImages,onCuratedItemClicked);
        mCuratedImagesRecycleView.setAdapter(curatedCollectionAdapter);


        LinearLayoutManager horizontalScroller2 = new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        mCuratedImagesRecycleView2.setLayoutManager(horizontalScroller2);
        CuratedCollectionAdapter curatedCollectionAdapter2 = new CuratedCollectionAdapter(
                curatedImages,onCuratedItemClicked);
        mCuratedImagesRecycleView2.setAdapter(curatedCollectionAdapter2);



        mLayoutBottomSheet.requestLayout();
        mShowViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //mLayoutBottomSheet.getLayoutParams().height = 1200;
                mLayoutBottomSheet.requestLayout();
            }
        });
    }

    private CuratedCollectionAdapter.OnRecyclerItemClick onCuratedItemClicked =
            new CuratedCollectionAdapter.OnRecyclerItemClick() {
                @Override
                public void onItemClick(int position) {

                }
            };

    public void showBottomSheetDialog() {
        CustomBottomSheetDialog bottomSheetDialog = CustomBottomSheetDialog.getInstance();
        bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setNumUpdates(1);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch(Exception e) {

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        mMap = map;
    }


}
