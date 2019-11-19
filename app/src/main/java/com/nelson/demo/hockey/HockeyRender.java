package com.nelson.demo.hockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import com.nelson.demo.R;
import com.nelson.demo.utils.ShaderHelper;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/11/19
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class HockeyRender implements Renderer {

    private static final float[] TABLE_VERTICES = {
        // triangle 1
        0f, 0f,
        9f, 14f,
        0f, 14f,

        // triangle 2
        0f, 0f,
        9f, 0f,
        9f, 14f,

        // line 1
        0f, 7f,
        9f, 7f,

        // mallets
        4.5f, 2f,
        4.5f, 12f
    };

    private Context mContext;
    private int mVertexHandler;
    private int mColorHandler;
    private FloatBuffer mVertexBuffer;

    public HockeyRender(Context context) {
        mContext = context;

        mVertexBuffer = ShaderHelper.getBuffer(TABLE_VERTICES);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int program = ShaderHelper.compileProgram(mContext, R.raw.hockey_vertex_shader, R.raw.hockey_fragment_shader);
        mVertexHandler = glGetAttribLocation(program, "a_Position");
        mColorHandler = glGetUniformLocation(program, "u_Color");
        glUseProgram(program);

        // 调用该函数告诉OpenGl,可以在缓冲区VertexBuffer中找到a_Position对应的数据
        glVertexAttribPointer(mVertexHandler, 4, GL_FLOAT, false, 0, mVertexBuffer);

        glEnableVertexAttribArray(mVertexHandler);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glUniform4f(mColorHandler, 1, 1, 1, 1);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
