package com.nelson.demo;

import android.opengl.GLSurfaceView;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.nelson.demo.hockey.HockeyRender;
import com.nelson.demo.opengl.CustomSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CustomSurfaceView mSurfaceView;
    private HockeyRender mHockeyRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mSurfaceView = findViewById(R.id.surface_view);

        init();
    }

    private void init() {
        mHockeyRender = new HockeyRender(this);
        mSurfaceView.setRenderer(mHockeyRender);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
