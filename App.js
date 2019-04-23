// this is the app navigotor component set in the navigation folder... do not delete this!
import AppNavigator from "./navigation/AppNavigator";
import React from "react";
import { Platform, StatusBar, StyleSheet, View } from "react-native";
import { AppLoading, Asset, Font, Icon } from "expo";

export default class App extends React.Component {
  state = {
    isLoadingComplete: false
  };

  render() {
    if (!this.state.isLoadingComplete && !this.props.skipLoadingScreen) {
      return (
        <AppLoading
          startAsync={this._loadResourcesAsync}
          onError={this._handleLoadingError}
          onFinish={this._handleFinishLoading}
        />
      );
    } else {
      return (
        <View style={styles.container}>
          {Platform.OS === "ios" && <StatusBar barStyle="default" />}
          <AppNavigator />
        </View>
      );
    }
  }

  _loadResourcesAsync = async () => {
    return Promise.all([
      Asset.loadAsync([
        require("./assets/appicon.png"),
        require("./assets/profile.png"),
        require("./assets/google.png"),
        require("./assets/fb.png")
      ]),
      Font.loadAsync({
        // This is the font that we are using for our side menu
        ...Icon.Ionicons.font,
        "quick-bold": require("./assets/fonts/Quicksand-Bold.ttf"),
        "quick-light": require("./assets/fonts/Quicksand-Light.ttf"),
        "quick-regular": require("./assets/fonts/Quicksand-Regular.ttf")
      })
    ]);
  };

  _handleLoadingError = error => {
    // In this case, you might want to report the error to your error
    // reporting service, for example Sentry
    console.warn(error);
  };

  _handleFinishLoading = () => {
    this.setState({ isLoadingComplete: true });
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff"
  }
});
