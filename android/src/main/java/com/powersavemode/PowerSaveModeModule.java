package com.powersavemode;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.PowerManager;
import android.content.Intent; 
import android.content.IntentFilter;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = PowerSaveModeModule.NAME)
public class PowerSaveModeModule extends ReactContextBaseJavaModule {
  public static final String NAME = "PowerSaveMode";
  private final ReactApplicationContext reactContext;
  private BroadcastReceiver powerSaveModeReceiver;

  public PowerSaveModeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    initPowerSaveModeReceiver();
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }
  @ReactMethod
  public void isPowerSaveModeEnabled(Callback callback) {
      PowerManager powerManager = (PowerManager) reactContext.getSystemService(Context.POWER_SERVICE);
      if (powerManager != null) {
          callback.invoke(null, powerManager.isPowerSaveMode());
      } else {
          callback.invoke("PowerManager not available", null);
      }
  }

  private void initPowerSaveModeReceiver() {
      powerSaveModeReceiver = new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
              if (PowerManager.ACTION_POWER_SAVE_MODE_CHANGED.equals(intent.getAction())) {
                  PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                  boolean isPowerSaveMode = powerManager.isPowerSaveMode();
                  WritableMap params = new WritableNativeMap();
                  params.putBoolean("isPowerSaveMode", isPowerSaveMode);
                  sendEvent("PowerSaveModeChanged", params);
              }
          }
      };

      IntentFilter filter = new IntentFilter(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
      reactContext.registerReceiver(powerSaveModeReceiver, filter);
  }

  @ReactMethod
  public void addListener(String eventName) {}

  @ReactMethod
  public void removeListeners(Integer count) {}

  private void sendEvent(String eventName, @NonNull WritableMap params) {
      reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, params);
  }

}
