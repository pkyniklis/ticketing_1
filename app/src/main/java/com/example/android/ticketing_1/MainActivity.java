package com.example.android.ticketing_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GestureView gestureView = findViewById(R.id.gestureView);
        gestureView.getController().getSettings()
                .setMaxZoom(10f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(false)
                .setRotationEnabled(true)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);

        final ImageView backImageView = findViewById(R.id.back);

        final Intent intent = new Intent(this, GateActivity.class);

        backImageView.setOnTouchListener(new View.OnTouchListener() {

            final int DOUBLE_TAP_DURATION = 500;
            long tapTime = System.currentTimeMillis();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();

                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if (action == MotionEvent.ACTION_DOWN) {

                    if (System.currentTimeMillis() - tapTime < DOUBLE_TAP_DURATION) {
                        backImageView.setDrawingCacheEnabled(true);
                        Bitmap hotspots = Bitmap.createBitmap(backImageView.getDrawingCache());
                        backImageView.setDrawingCacheEnabled(false);

                        int touchColor = hotspots.getPixel(x, y);

                        if (touchColor == 0xff140000) {
                            intent.putExtra("Gate", "218");
                            startActivity(intent);
                        }
                        if (touchColor == 0xff280000) {
                            Toast.makeText(MainActivity.this, "Gate 219", Toast.LENGTH_SHORT).show();
                        }
                        if (touchColor == 0xff3c0000) {
                            intent.putExtra("Gate", "111");
                            startActivity(intent);
                        }
                    }
                    tapTime = System.currentTimeMillis();
                }

                return false;
            }
        });
    }
}
