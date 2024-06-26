import {
  NativeEventEmitter,
  NativeModules,
  Platform,
  type EmitterSubscription,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-power-save-mode' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const PowerSaveMode = NativeModules.PowerSaveMode
  ? NativeModules.PowerSaveMode
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

type PowerSaveModeListener = (data: { isPowerSaveMode: boolean }) => void;

export const isPowerSaveModeEnabled = async (): Promise<boolean> => {
  return new Promise((resolve, reject) => {
    PowerSaveMode.isPowerSaveModeEnabled((error: any, result: boolean) => {
      if (error) {
        reject(!!error);
      } else {
        resolve(result);
      }
    });
  });
};

export const addPowerSaveModeListener = (
  listener: PowerSaveModeListener
): EmitterSubscription => {
  const powerSaveModeEmitter = new NativeEventEmitter(PowerSaveMode);
  return powerSaveModeEmitter.addListener('PowerSaveModeChanged', listener);
};

export const removePowerSaveModeListener = (
  subscription: EmitterSubscription
) => {
  subscription.remove();
};
