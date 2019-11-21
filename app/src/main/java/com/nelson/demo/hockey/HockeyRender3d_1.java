package com.nelson.demo.hockey;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.*;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import com.nelson.demo.MatrixHelper;
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
public class HockeyRender3d_1 implements Renderer {

    private static final float[] TABLE_VERTICES = {
        // triangle fan
        0f, 0f, 0f, 1.5f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

        // Line 1
        -0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,
        0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,

        // Mallets
        0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f,
        0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f
    };
    private final int POSITION_COMPONENT_COUNT = 4;
    private final int COLOR_COMPONENT_COUNT = 3;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Context mContext;
    private int mVertexHandler;
    private int mColorHandler;
    private int mMatrixHandler;
    private FloatBuffer mVertexBuffer;
    private float mAspectRation;

    public HockeyRender3d_1(Context context) {
        mContext = context;

        mVertexBuffer = ShaderHelper.getBuffer(TABLE_VERTICES);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int program = ShaderHelper
            .compileProgram(mContext, R.raw.hockey_vertex_shader_3d_1, R.raw.hockey_fragment_shader_3d_1);
        mVertexHandler = glGetAttribLocation(program, "a_Position");
        mColorHandler = glGetAttribLocation(program, "a_Color");
        mMatrixHandler = glGetUniformLocation(program, "u_Matrix");

        glUseProgram(program);

        // 调用该函数告诉OpenGl,可以在缓冲区VertexBuffer中找到a_Position对应的数据
        glVertexAttribPointer(mVertexHandler, POSITION_COMPONENT_COUNT, GL_FLOAT, false,
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * 4, mVertexBuffer);
        glEnableVertexAttribArray(mVertexHandler);

        // 调用该函数告诉OpenGl,可以在缓冲区VertexBuffer中找到a_Position对应的数据
        mVertexBuffer.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(mColorHandler, COLOR_COMPONENT_COUNT, GL_FLOAT, false,
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * 4, mVertexBuffer);
        glEnableVertexAttribArray(mColorHandler);

        glClearColor(0, 0, 0, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        mAspectRation = width > height ? (float) width / (float) height : (float) height / (float) width;

//        if (width > height) {
//            Matrix.orthoM(projectionMatrix, 0, -mAspectRation, mAspectRation, -1f, 1f, -1f, 1f);
//        } else {
//            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -mAspectRation, mAspectRation, -1f, 1f);
//        }
        MatrixHelper.perspectiveM(projectionMatrix, 45f, (float) width / (float) height, 1f, 10f);
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0, 0, -2f);
        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        glUniformMatrix4fv(mMatrixHandler, 1, false, projectionMatrix, 0);

        // Draw the table.
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw the center dividing line.
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet.
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet.
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
