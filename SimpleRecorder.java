package com.shixi.a;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

/**
 * Created by z on 2016/6/2.
 * 使用方法,开始录音,停止录音,设置录音时间的回调,使用handler机制,不用担心子线程更新ui问题
 */
public class SimpleRecorder {
    private MediaRecorder mMediaRecorder;
    private static long mPosition;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPosition++;
            mOnRecodingListener.onRecoding(mPosition);
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private onRecodingListener mOnRecodingListener;

    /**
     * 构造方法,把播放的准备工作都做完了
     */
    public SimpleRecorder() {
//        init();

    }

    private void init() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setMaxDuration(60 * 1000 * 5);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    /**
     * 是否成功开始录音
     * 开始定时,并且计时
     *
     * @param outPath
     * @return
     */
    public void start(String outPath) {
        try {
            if (mMediaRecorder != null) {
                mMediaRecorder.reset();
            }
            init();
            mMediaRecorder.setOutputFile(outPath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
        }
    }

    public void stop() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void SetonRecodingListener(onRecodingListener listener) {
        mOnRecodingListener = listener;
    }

    public interface onRecodingListener {
        public void onRecoding(long position);
    }
}
