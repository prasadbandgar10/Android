package com.example.bhashahub.ui.course;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LevelWiseCourseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LevelWiseCourseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}