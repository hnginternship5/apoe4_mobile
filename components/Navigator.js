import React, { Component } from 'react';
import { Dimensions } from 'react-native';
import { createStackNavigator, createAppContainer,createDrawerNavigator} from "react-navigation";
import DOB from './DOB';
import SignIn from './SignIn'
import SideMenu from './SideMenu'
import HomePage from './HomePage'


 const AppDrawer = createDrawerNavigator (
  {
     HomePage:{screen:HomePage},
  },
  {
  contentComponent:(props) => <SideMenu {...props} />,
  drawerWidth: (Dimensions.get('window').width)*0.6,
	}
);

const RootStack =  createStackNavigator({
   SignIn:{screen:SignIn,navigationOptions: { header: null }},
   DOB:{screen:DOB ,navigationOptions: { header: null }},
   AppDrawer:{screen:AppDrawer,navigationOptions: { header: null }},
  });
const AppContainer = createAppContainer(AppDrawer);
export default AppContainer;
