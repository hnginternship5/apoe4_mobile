import React from "react";
import { createAppContainer, createSwitchNavigator } from "react-navigation";

import MainTabNavigator from "./sideNav";
import AuthLoadingScreen from "../screens/auth/authLoad";
import authStack from "./authnav";

export default createAppContainer(
  createSwitchNavigator(
    {
      // You could add another route here for authentication.
      // Read more at https://reactnavigation.org/docs/en/auth-flow.html
      load: { screen: AuthLoadingScreen },
      auth: authStack,
      Main: MainTabNavigator
    },
    {
      initialRouteName: "load"
    }
  )
);
