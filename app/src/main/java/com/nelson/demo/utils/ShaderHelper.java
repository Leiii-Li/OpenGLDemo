package com.nelson.demo.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import androidx.annotation.RawRes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * <pre>
 *      @author  : Nelson
 *      @since   : 2019/10/18
 *      github  : https://github.com/Nelson-KK
 *      desc    :
 * </pre>
 */
public class ShaderHelper {

    public static final String TAG = ShaderHelper.class.getSimpleName();

    public static FloatBuffer getBuffer(float[] buffer) {
        FloatBuffer floatBuffer = ByteBuffer
            //分配了一块本地内存，这块内存不会被垃圾回收器管理，这里需要知道具体分配多少内存块
            .allocateDirect(buffer.length * 4)
            //告诉字节缓冲区读取的的内容序列，最重要的是保持同样的顺序
            .order(ByteOrder.nativeOrder())
            //得到一个可以放映底层字节的FloatBuffer类实例，可以避免操作单独字节的麻烦
            .asFloatBuffer();
        floatBuffer.put(buffer);
        floatBuffer.position(0);
        return floatBuffer;
    }

    /**
     * @return 创建编译一个program, 绑定vertex shader和vertex shader
     */
    public static int compileProgram(Context context, @RawRes int vertexShaderCode, @RawRes int fragmentShaderCode) {
        int vertexShader = compileVertexShader(context, vertexShaderCode);
        int fragmentShader = compileFragmentShader(context, fragmentShaderCode);
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        Log.d(TAG, "program " + program + ", info: " + GLES20.glGetShaderInfoLog(program));
        return program;
    }

    /**
     * @return 创建编译一个vertex shader
     */
    private static int compileVertexShader(Context context, @RawRes int res) {
        int id = compileShader(context, res, GLES20.GL_VERTEX_SHADER);
        Log.d(TAG, "vertex shader " + id + ", info: " + GLES20.glGetShaderInfoLog(id));
        return id;
    }

    /**
     * @return 创建编译一个fragment shader
     */
    private static int compileFragmentShader(Context context, @RawRes int res) {
        int id = compileShader(context, res, GLES20.GL_FRAGMENT_SHADER);
        Log.d(TAG, "fragment shader " + id + ", info: " + GLES20.glGetShaderInfoLog(id));
        return id;
    }

    private static int compileShader(Context context, @RawRes int res, int shaderType) {
        String code = readRaw(context, res);
        int shader = GLES20.glCreateShader(shaderType);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);
        return shader;
    }

    /**
     * 从raw文件中读string
     */
    private static String readRaw(Context context, @RawRes int res) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(res)));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    /* no-op */
                }
            }
        }
    }
}
