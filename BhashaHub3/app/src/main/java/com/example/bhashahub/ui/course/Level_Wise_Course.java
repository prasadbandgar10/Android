package com.example.bhashahub.ui.course;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhashahub.R;

public class Level_Wise_Course extends Fragment {

    private LevelWiseCourseViewModel mViewModel;

    public static Level_Wise_Course newInstance() {
        return new Level_Wise_Course();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.level__wise__course_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LevelWiseCourseViewModel.class);
        // TODO: Use the ViewModel
    }

}