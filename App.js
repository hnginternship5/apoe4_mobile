import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,
        TouchableOpacity,} from 'react-native';
import AppContainer from './components/Navigator'

export default class App extends React.Component {
  render() {
    return (
      <View style={styles.container}>
      <StatusBar translucent backgroundColor="#606060" barStyle="dark-content" />
      <AppContainer />
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
  },

});
