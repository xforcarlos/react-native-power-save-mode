package com.powersavemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class PowerSaveModeReceiver extends BroadcastReceiver {
    private final ReactApplicationContext reactContext;

    public PowerSaveModeReceiver(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            boolean isPowerSaveMode = pm.isPowerSaveMode();
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("PowerSaveModeChanged", isPowerSaveMode);
        }
    }
}
