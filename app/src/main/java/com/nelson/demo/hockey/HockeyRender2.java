package com.nelson.demo.hockey;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import com.nelson.demo.R;
import com.nelson.demo.utils.ShaderHelper;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/11/19
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class HockeyRender2 implements Renderer {

    private static final float[] TABLE_VERTICES = {
        // triangle 1
        -0.5f, -0.5f,
        0.5f, 0.5f,
        -0.5f, 0.5f,

        // triangle 2
        -0.5f, -0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f,

        // line 1
        -0.5f, 0f,
        0.5f, 0f,

        // mallets
        0f, -0.25f,
        0f, 0.25f
    };

    private Context mContext;
    private int mVertexHandler;
    private int mColorHandler;
    private FloatBuffer mVertexBuffer;

    public HockeyRender2(Context context) {
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
        glVertexAttribPointer(mVertexHandler, 2, GL_FLOAT, false, 0, mVertexBuffer);

        glEnableVertexAttribArray(mVertexHandler);

        glClearColor(0, 0, 0, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        // 绘制球桌：绘制方式是 TRIANGLES 所以需要6个顶点，从下标为0开始
        glUniform4f(mColorHandler, 1, 1, 1, 1);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        // 绘制分割线
        glUniform4f(mColorHandler, 1, 0, 0, 1);
        glDrawArrays(GL_LINES, 6, 2);

        // 绘制 两个木槌
        glUniform4f(mColorHandler, 0, 0, 1, 1);
        glDrawArrays(GL_POINTS, 8, 1);

        glUniform4f(mColorHandler, 1, 0, 0, 1);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
