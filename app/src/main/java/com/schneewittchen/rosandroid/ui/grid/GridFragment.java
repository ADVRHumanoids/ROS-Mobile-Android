package com.schneewittchen.rosandroid.ui.grid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.ui.main.MainViewModel;


public class GridFragment extends Fragment {

    private GridViewModel mViewModel;

    public static GridFragment newInstance() {
        return new GridFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(GridViewModel.class);
        // TODO: Use the ViewModel
    }

}
