package com.example.android.ticketing_1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

public class GateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);

        GestureView gestureView = findViewById(R.id.gate_gestureView);
        gestureView.getController().getSettings()
                .setMaxZoom(10f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(false)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);

        final ImageView backImageView = findViewById(R.id.gate_back);
        final ImageView frontImageView = findViewById(R.id.gate_front);

        backImageView.setImageResource(R.drawable.gate111_back);
        frontImageView.setImageResource(R.drawable.gate111_back);

        VectorChildFinder vector = new VectorChildFinder(this, R.drawable.gate111_front, frontImageView);
        final VectorDrawableCompat.VFullPath r16s1 = vector.findPathByName("r16s1");
        final VectorDrawableCompat.VFullPath r16s2 = vector.findPathByName("r16s2");


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
                        //Log.d("Petros", Integer.toString(touchColor));

                        if (touchColor == 0xff010000) {
                            Toast.makeText(GateActivity.this, "r16s1", Toast.LENGTH_SHORT).show();
                            toggleColor(r16s1);
                            frontImageView.invalidate();
                        }
                        if (touchColor == 0xff020000) {
                            Toast.makeText(GateActivity.this, "r16s2", Toast.LENGTH_SHORT).show();
                            toggleColor(r16s2);
                            frontImageView.invalidate();
                        }
                        if (touchColor == 0xff280000) {
                            //Toast.makeText(MainActivity.this, "Gate 219", Toast.LENGTH_SHORT).show();
                            //toggleAlpha(s2);
                            //frontImageView.invalidate();
                        }
                        if (touchColor == 0xff3c0000) {
                            //Toast.makeText(MainActivity.this, "Gate 111", Toast.LENGTH_SHORT).show();


                            //toggleAlpha(s2);
                            //frontImageView.invalidate();
                        }
                    }
                    tapTime = System.currentTimeMillis();

                }

                return false;
            }
        });

    }

    private void toggleAlpha(VectorDrawableCompat.VFullPath seat) {
        if (seat.getFillAlpha() != 128) seat.setFillAlpha(128);
        else seat.setFillAlpha(1);
    }

    private void toggleColor(VectorDrawableCompat.VFullPath seat) {
        if (seat.getFillColor() != 0xff9e9e9e) seat.setFillColor(0xff9e9e9e);
        else seat.setFillColor(0xff64dd17);
    }
}
