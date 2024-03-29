package com.nelson.demo;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.nelson.demo.hockey.HockeyRender1;
import com.nelson.demo.hockey.HockeyRender2;
import com.nelson.demo.hockey.HockeyRender3;
import com.nelson.demo.hockey.HockeyRender3d_1;
import com.nelson.demo.opengl.CustomSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CustomSurfaceView mSurfaceView;
    private Renderer mHockeyRender;

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
        mHockeyRender = new HockeyRender3d_1(this);
        mSurfaceView.setRenderer(mHockeyRender);
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
