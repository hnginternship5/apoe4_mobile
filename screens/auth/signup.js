import React from "react";
import { Button, AsyncStorage, View } from "react-native";

export default class SignUpScreen extends React.Component {
  static navigationOptions = {
    title: "SignUp"
  };

  render() {
    return (
      <View>
        <Button title="Sign in!" onPress={this._signInAsync} />
      </View>
    );
  }

  _signInAsync = async () => {
    await AsyncStorage.setItem("userToken", "abc");
    this.props.navigation.navigate("App");
  };
}
