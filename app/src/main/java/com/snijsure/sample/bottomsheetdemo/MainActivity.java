package com.snijsure.sample.bottomsheetdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.snijsure.sample.bottomsheetdemo.ui.adapter.CuratedCollectionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.snijsure.sample.bottomsheetdemo.R.id.map_image_holder;
import static com.snijsure.sample.bottomsheetdemo.R.id.recycler_curate_images;
import static com.snijsure.sample.bottomsheetdemo.R.id.recycler_curate_images_second;
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_bottom_sheet) View mLayoutBottomSheet;
    @BindView(recycler_curate_images) RecyclerView mCuratedImagesRecycleView;
    @BindView(recycler_curate_images_second) RecyclerView mCuratedImagesRecycleView2;
    @BindView(map_image_holder) RelativeLayout mapImageHolder;
    @BindView(R.id.show_view_btn) Button mShowViewBtn;

    private BottomSheetBehavior mBottomSheetBehavior;
    public static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mShowViewBtn.setVisibility(View.VISIBLE);
                    //mLayoutBottomSheet.getLayoutParams().height = mShowViewBtn.getHeight();
                    mCuratedImagesRecycleView.setVisibility(View.GONE);
                    mCuratedImagesRecycleView2.setVisibility(View.GONE);
                    mapImageHolder.requestLayout();
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


}
