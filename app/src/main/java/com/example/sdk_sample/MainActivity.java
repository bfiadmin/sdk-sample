package com.example.sdk_sample;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.storyplayer.consumer.ShowActivity;
import io.storyplayer.consumer.ShowEventListener;
import io.storyplayer.consumer.StoryPlayerView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private PictureInPictureParams.Builder pipParams;
    private StoryPlayerView storyPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storyPlayerView = findViewById(R.id.storyPlayerView);
        ShowEventListener showEventListener = new ShowEventListener();
        storyPlayerView.addShowEventListener(showEventListener);
        storyPlayerView.setShowId("73228940291189");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pipParams = new PictureInPictureParams.Builder();
        }

    }

    public void openShowActiviy(View view){
        Intent showIntent =  new Intent(this, ShowActivity.class);
        startActivity(showIntent);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        // called when user touch on home button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "onUserLeaveHint: was not in pip");
            pipMode();
        } else {
            Log.d(TAG, "onUserLeaveHint: already in pip");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        storyPlayerView.handleFileChooser(requestCode, resultCode, intent);
    }


    private void pipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // setup height and width of PIP window
            enterPictureInPictureMode(new PictureInPictureParams.Builder().setAspectRatio(new Rational(9, 16)).build());
        } else {
            Log.d(TAG, "pipMode: not supported");
        }
    }

}