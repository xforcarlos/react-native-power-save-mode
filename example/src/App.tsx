import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { usePowerSaveMode } from './powerSave';

export default function App() {
  const { powerSaveMode } = usePowerSaveMode();
  return (
    <View
      style={[
        styles.container,
        { backgroundColor: !powerSaveMode ? 'blue' : 'red' },
      ]}
    >
      <Text>Result: {`${powerSaveMode}`}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
