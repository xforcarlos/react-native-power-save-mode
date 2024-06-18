# react-native-power-save-mode

React Native module for detecting and monitoring power save mode on Android & ios devices.

## Installation

Install the package using npm or yarn:

```bash
npm install react-native-power-save-mode
```

or

```bash
yarn add react-native-power-save-mode
```

Linking
Automatic linking (React Native 0.60 and higher)
The package should link automatically after installation. If not, run:

```bash
npx react-native link react-native-power-save-mode
```

Manual linking (React Native lower than 0.60)
In android/app/build.gradle, make sure to include:

```
dependencies {
    implementation project(':react-native-power-save-mode')
    // Other dependencies
}
```

Update the settings.gradle file to include the following:

```
include ':react-native-power-save-mode'
project(':react-native-power-save-mode').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-power-save-mode/android')
```

In your MainApplication.java, register the package:

```
import com.powersavemode.PowerSaveModePackage; // Import the package

// Add `new PowerSaveModePackage()` to the list of packages
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.asList(
        new MainReactPackage(),
        new PowerSaveModePackage() // Add this line
    );
}
```

Example usage in a React component

```
import * as React from 'react';
import { useEffect, useState } from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { isPowerSaveModeEnabled, addPowerSaveModeListener } from 'react-native-power-save-mode';

export default function App() {
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

  return (
    <View style={[styles.container, { backgroundColor: powerSaveMode ? 'blue' : 'red' }]}>
      <Text>Power Save Mode: {powerSaveMode ? 'Enabled' : 'Disabled'}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
```

License
MIT License. See the LICENSE file for details.
