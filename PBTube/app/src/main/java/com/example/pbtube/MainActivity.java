package com.example.pbtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView vi=findViewById(R.id.vi);
        vi.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.clip);

        MediaController mediaController=new MediaController(this);
        vi.setMediaController(mediaController);
        mediaController.setAnchorView(vi);
        vi.start();
    }
}