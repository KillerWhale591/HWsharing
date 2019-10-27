package com.example.sse.customlistview_sse;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        VideoView mVideoView = (VideoView) findViewById(R.id.videoView);
        MediaController mController = new MediaController(this);
        String video = "android.resource://" + getPackageName() + "/" + R.raw.khan;
        mController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mController);
        mVideoView.setVideoURI(Uri.parse(video));
        mVideoView.requestFocus();
        mVideoView.start();
    }
}
