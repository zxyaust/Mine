package com.shixi.a;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * 使用方法示例:
 * 1.开始播放:
 * if (!mMediaPlayer.start(path))
 * Toast.makeText(getApplicationContext(), "资源路径可能错误", Toast.LENGTH_SHORT).show();
 * 2.停止播放:
 * mMediaPlayer.stop();
 * Created by z on 2016/6/1.
 */
public class SimpleMediaPlayer {
    private MediaPlayer mPlayer;

    public SimpleMediaPlayer() {
        if (mPlayer == null)
            mPlayer = new MediaPlayer();
    }

    /**
     * 重新开始播放
     *
     * @param path
     * @return 是否播放成功
     */
    public boolean start(String path) {
        if (setSource(path)) {
            if (start()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        int currentPosition = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        Log.d("当前位置", currentPosition + "," + duration + "");
        if (mPlayer != null && currentPosition < duration) {
            //如果没有放完的话就开始播放,否则没效果
            mPlayer.start();

        }
    }

    /**
     * 从头开始播放
     *
     * @return 是否正常开始播放
     */
    private boolean start() {
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.start();
            }
        });
        return true;
    }

    /**
     * 结束播放
     */
    public void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.reset();
        }

    }

    /**
     * 释放资源
     */
    public void release() {
        if (mPlayer != null)
            mPlayer.release();
    }

    /**
     * 如果正在播放则会停止,然后再设置资源路径
     *
     * @param path
     * @return
     */
    private boolean setSource(String path) {
        if (mPlayer == null)
            mPlayer = new MediaPlayer();
        if (mPlayer.isPlaying()) {
            stop();
        }
        mPlayer.reset();
        try {
            if (path == null) {
                Log.d("设置错误", "资源路径为null");
                return false;
            }
            mPlayer.setDataSource(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
