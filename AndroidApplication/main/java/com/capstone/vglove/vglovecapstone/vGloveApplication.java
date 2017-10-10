package com.capstone.vglove.vglovecapstone;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.res.Configuration;

/**
 * Created by Nathan on 5/21/2017.
 */

public class vGloveApplication extends Application {

    public static BluetoothDevice mBTDevice;
    private static vGloveApplication singleton;

    public static vGloveApplication getInstance() {
        return singleton;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        this.mBTDevice = null;
        singleton = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    public vGloveApplication(){
        this.mBTDevice = null;
    }

    public BluetoothDevice getGlobDevice() {

        return mBTDevice;
    }

    public void setGlobDevice(BluetoothDevice newdev) {

        this.mBTDevice = newdev;
    }
}
