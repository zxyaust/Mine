package com.z.thepretender.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by z on 2016/6/1.
 * 使用方法:
 * 你可以选择在Activity的onCreate或者onStar方法中调用register方法,
 * 然后再onStop或者onDestroy方法中调用unRegister方法即可
 * 注意:需要权限
 * <uses-permission android:name="android.permission.WAKE_LOCK"/>
 */
public class DistanceWakeListener implements SensorEventListener {
    private SensorManager mSensorManager;//传感器管理对象
    private PowerManager.WakeLock mWakeLock;//电源锁

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] its = event.values;
        //Log.d(TAG,"its array:"+its+"sensor type :"+event.sensor.getType()+" proximity type:"+Sensor.TYPE_PROXIMITY);
        if (its == null)
            return;
        System.out.println("its[0]:" + its[0]);
        //经过测试，当手贴近距离感应器的时候its[0]返回值为0.0，当手离开时返回1.0
        if (its[0] == 0.0) {// 贴近手机
            Log.d("DistanceWakeListener", "close face");
            lockAcquire();
        } else {// 远离手机
            Log.d("DistanceWakeListener", "away from face");
            lockRelease();
        }

    }

    private void lockRelease() {
        if (mWakeLock.isHeld()) {
            return;
        } else {
            mWakeLock.setReferenceCounted(false);//是否计数,如果true,则每次acquire都需要一个release,否则一个release可以释放掉所有的acquire
            mWakeLock.release(); // 释放设备电源锁
        }
    }

    private void lockAcquire() {
        if (mWakeLock.isHeld()) {//如果申请了就返回true,如果释放了就返回false
            return;
        } else {
            mWakeLock.acquire();// 申请设备电源锁
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * @param context
     */
    public void register(Context context) {

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //获取系统服务POWER_SERVICE，返回一个PowerManager对象
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        mWakeLock = powerManager.newWakeLock(32, "MyPower");//第一个参数为电源锁级别，第二个是日志tag
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),// 距离感应器
                SensorManager.SENSOR_DELAY_GAME);//注册传感器，第一个参数为距离监听器，第二个是传感器类型，第三个是延迟类型
    }

    public void unRegister() {
        if (mSensorManager != null) {
            mWakeLock.release();//释放电源锁，如果不释放finish这个acitivity后仍然会有自动锁屏的效果，不信可以试一试
            mSensorManager.unregisterListener(this);//注销传感器监听
        }
    }
}
