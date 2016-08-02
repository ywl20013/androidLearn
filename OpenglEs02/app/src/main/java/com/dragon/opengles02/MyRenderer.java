package com.dragon.opengles02;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * This file created by dragon on 2016/8/2 14:45,
 * belong to com.dragon.opengles02 .
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    float[] triangleData = new float[]{
            0.1f,0.6f,0.0f,//上顶点
            -0.3f,0.0f,0.0f,//左
            0.3f,0.1f,0.0f//右
    };

    float[] triangleColor = new float[]{
            1.0f,0,0,0,//上->red
            0,1.0f,0,0,//左->green
            0,0,1.0f,0//右->blue
    };

    //    定义缓冲数据
    FloatBuffer triangleDataBuffer;
    FloatBuffer triangleColorBuffer;

    public MyRenderer(){
//        将数组->FloatBuffer
        triangleDataBuffer = floatBufferUtil(triangleData);
//        颜色数据->IntBuffer
        triangleColorBuffer = floatBufferUtil(triangleColor);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        // 关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        // 设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT
                , GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        // 设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }
    @Override
    public void onSurfaceChanged(GL10 gl,int width,int height){
        // 设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        // 将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 初始化单位矩阵
        gl.glLoadIdentity();
        // 计算透视视窗的宽度、高度比
        float ratio = (float) width / height;
        // 调用此方法设置透视视窗的空间大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }
    @Override
    public void onDrawFrame(GL10 gl){
        // 清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // 设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // --------------------绘制三角形---------------------
        // 重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.32f, 0.35f, -1.2f);  // ①
        // 设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
        // 设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
        // 根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        // 绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    private FloatBuffer floatBufferUtil(float[] arr){
        FloatBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length*4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }
}



