package com.nelson.demo.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/11/19
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class CustomSurfaceView extends GLSurfaceView {

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
    }
}
