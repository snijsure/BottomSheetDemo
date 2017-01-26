package com.snijsure.sample.bottomsheetdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomBottomSheetDialog extends BottomSheetDialogFragment {
    public static CustomBottomSheetDialog getInstance() {
        return new CustomBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);
    }
}
