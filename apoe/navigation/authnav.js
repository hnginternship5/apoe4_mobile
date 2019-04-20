import React from "react";

import { createStackNavigator } from "react-navigation";

import loginScreen from "../screens/auth/login";
import SignUpScreen from "../screens/auth/signup";

export default createStackNavigator({
  Login: { screen: loginScreen },
  Join: { screen: SignUpScreen }
});
