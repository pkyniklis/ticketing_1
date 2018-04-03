package com.example.android.test_shapes_1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        GestureView gestureView = findViewById(R.id.gestureView);

        gestureView.getController().getSettings()
                .setMaxZoom(4f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);

        final ImageView backImageView = findViewById(R.id.back);
        final ImageView frontImageView = findViewById(R.id.front);
        //backImageView.setImageResource(R.drawable.back);

        VectorChildFinder vector = new VectorChildFinder(this, R.drawable.front, frontImageView);
        final VectorDrawableCompat.VFullPath s1 = vector.findPathByName("s1");
        final VectorDrawableCompat.VFullPath s2 = vector.findPathByName("s2");

        //s1.setFillColor(Color.RED);
        //s1.setFillAlpha(128);


        backImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();

                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if (action == MotionEvent.ACTION_DOWN) {

                    backImageView.setDrawingCacheEnabled(true);
                    Bitmap hotspots = Bitmap.createBitmap(backImageView.getDrawingCache());
                    backImageView.setDrawingCacheEnabled(false);

                    int touchColor = hotspots.getPixel(x, y);
                    Log.d("Petros", Integer.toString(touchColor));

                    //s1.setFillColor(Color.RED);

                    if (touchColor == 0xffbe0000) {
                        toggleAlpha(s1);
                        frontImageView.invalidate();
                    }
                    if (touchColor == 0xff00be00) {
                        toggleAlpha(s2);
                        frontImageView.invalidate();
                    }

                }

                return false;
            }
        });

    }

    private void toggleAlpha(VectorDrawableCompat.VFullPath seat) {
        if (seat.getFillAlpha() != 128) seat.setFillAlpha(128);
        else seat.setFillAlpha(1);
    }
}