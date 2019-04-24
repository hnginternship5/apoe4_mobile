import React from "react";
import { Button, View, AsyncStorage, StyleSheet } from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";
import { Input, Image } from "react-native-elements";

export default class SignInScreen extends React.Component {
  static navigationOptions = {
    header: null
  };

  render() {
    return (
      <View style={styles.container}>
        <Button title="Sign in!" onPress={this._signInAsync} />
      </View>
    );
  }

  _signInAsync = async () => {
    await AsyncStorage.setItem("userToken", "abc");
    this.props.navigation.navigate("Main");
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10
  }
});
// More code like OtherScreen omitted for brevity
