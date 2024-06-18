import { useState, useEffect } from 'react';
import {
  isPowerSaveModeEnabled,
  addPowerSaveModeListener,
} from 'react-native-power-save-mode';

export const usePowerSaveMode = (): { powerSaveMode: boolean } => {
  const [powerSaveMode, setPowerSaveMode] = useState(false);

  useEffect(() => {
    const checkPowerSaveMode = async () => {
      const isEnabled = await isPowerSaveModeEnabled();

      setPowerSaveMode(isEnabled);
    };

    checkPowerSaveMode();

    const subscription = addPowerSaveModeListener((data) => {
      setPowerSaveMode(data?.isPowerSaveMode);
    });

    return () => {
      subscription.remove();
    };
  }, []);

  return { powerSaveMode };
};
