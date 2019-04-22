import React from "react";
import Icon from "@expo/vector-icons/Ionicons";

import { createDrawerNavigator, createStackNavigator } from "react-navigation";

import sideMenu from "../screens/sidemenu";
import DashboardTabNavigator from "./MainTabNavigator";

const DashboardStackNavigator = createStackNavigator(
  {
    DashboardTabNavigator: DashboardTabNavigator
  },
  {
    defaultNavigationOptions: ({ navigation }) => {
      return {
        headerLeft: (
          <Icon
            style={{ paddingLeft: 10 }}
            onPress={() => navigation.openDrawer()}
            name="md-menu"
            size={30}
          />
        )
      };
    }
  }
);

export default createDrawerNavigator({
  Dashboard: {
    screen: DashboardStackNavigator
  }
});
