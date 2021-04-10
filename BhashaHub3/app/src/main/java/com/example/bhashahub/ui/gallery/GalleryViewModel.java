package com.example.bhashahub.ui.gallery;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class GalleryViewModel extends ViewModel {
    ImageButton prev, next;
    ImageView pic;
    int currentImage = 0;


    public GalleryViewModel() {

    }
    public void prev(View v){

        String idX = "pic" + currentImage;
        //int x = this.getResources().getIdentifier(idX, "id", getPackageName());
        //pic.findViewById(x);
        pic.setAlpha(0f);
        currentImage = (5 + currentImage - 1) % 5;
        String idY = "pic" + currentImage;
        //int y = this.getResources().getIdentifier(idY, "id", getPackageName());
      //  pic.findViewById(y);
        pic.setAlpha(1f);
    }

    private String getPackageName() {
        return getPackageName();
    }

    public void next(View v){
        String idX = "pic" + currentImage;
        //int x = this.getResources().getIdentifier(idX, "id", getPackageName());
        //pic.findViewById(x);
        pic.setAlpha(0f);
        currentImage = (currentImage + 1) % 5;
        String idY = "pic" + currentImage;
        //int y = this.getResources().getIdentifier(idY, "id", getPackageName());
        //pic.findViewById(y);
        pic.setAlpha(1f);
    }

}