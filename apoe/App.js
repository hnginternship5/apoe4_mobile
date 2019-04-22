// Use this screen to load any assets

// this is the app navigotor component set in the navigation folder... do not delete this!
import AppNavigator from "./navigation/AppNavigator";
import React from "react";
import { Asset, AppLoading } from "expo";

export default class App extends React.Component {
  state = {
    isReady: false
  };

  render() {
    if (!this.state.isReady) {
      return (
        <AppLoading
          startAsync={this._cacheResourcesAsync}
          onFinish={() => this.setState({ isReady: true })}
          onError={console.warn}
        />
      );
    }

    return <AppNavigator />;
  }

  async _cacheResourcesAsync() {
    const images = [require("./assets/fb.png"), require("./assets/logo.png")];

    const cacheImages = images.map(image => {
      return Asset.fromModule(image).downloadAsync();
    });
    return Promise.all(cacheImages);
  }
}
