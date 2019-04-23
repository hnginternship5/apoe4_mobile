import { View, StyleSheet, AsyncStorage } from "react-native";
import React, { Component } from "react";
import { Icon } from "react-native-elements";
import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp
} from "react-native-responsive-screen";
import { Avatar, ListItem } from "react-native-elements";
// import { Icon } from "../components/TabBarIcon";
import { Txt, TxtB } from "../components/StyledText";
export default class sideNav extends Component {
  static navigationOptions = {
    header: "Nav"
  };
  constructor(props) {
    super(props);

    this.state = {
      list: [
        {
          title: "Settings",
          icon: "settings",
          iconType: "Octicons",
          action: this.goTosettings
        },
        {
          title: "Logout",
          icon: "login",
          iconType: "entypo",
          action: this._signOutAsync
        }
      ]
    };
  }

  componentDidMount() {
    this.makeRemoteRequest();
  }

  _signOutAsync = async () => {
    await AsyncStorage.clear();
    this.props.navigation.navigate("auth");
  };

  goTosettings = () => {
    this.props.navigation.navigate("SettingsStack");
  };

  makeRemoteRequest = () => {
    const { page, seed } = this.state;
    const url = `https://randomuser.me/api/?seed=${seed}&page=${page}&results=20`;
    this.setState({ loading: true });

    fetch(url)
      .then(res => res.json())
      .then(res => {
        this.setState({
          data: page === 1 ? res.results : [...this.state.data, ...res.results],
          error: res.error || null,
          loading: false,
          refreshing: false
        });
      })
      .catch(error => {
        this.setState({ error, loading: false });
      });
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.header}>
          <Avatar
            rounded
            size="large"
            containerStyle={{ marginBottom: 10 }}
            source={{
              uri:
                "https://s3.amazonaws.com/uifaces/faces/twitter/ladylexy/128.jpg"
            }}
          />
          <TxtB>John Doe</TxtB>
          <Txt>Male, 30 years</Txt>
        </View>
        <View style={styles.main}>
          <View style={styles.bio}>
            <Txt>Height</Txt>
            <Txt>180 cm</Txt>
          </View>

          <View style={styles.bio}>
            <Txt>Weight</Txt>
            <Txt>180 kg</Txt>
          </View>
        </View>
        <View>
          {this.state.list.map((item, i) => (
            <ListItem
              onPress={item.action}
              titleStyle={styles.light}
              key={i}
              title={item.title}
              leftIcon={
                <Icon
                  name={item.icon}
                  iconStyle={styles.light}
                  type={item.iconType}
                />
              }
            />
          ))}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    justifyContent: "center"
  },
  header: {
    height: hp("25%"),
    width: "100%",
    backgroundColor: "#3380CC",
    paddingTop: 20,
    // paddingLeft: 25,
    alignItems: "center",
    flexDirection: "column"
  },
  main: {
    height: hp("10%"),
    width: "100%",
    backgroundColor: "#5897D5",
    flexDirection: "row",
    justifyContent: "space-around"
    // paddingLeft: 5,
    // paddingRight: 35,
    // paddingTop: 10
  },
  bio: {
    flexDirection: "column",
    justifyContent: "space-evenly"
  },
  light: {
    fontFamily: "quick-regular",
    color: "black",
    fontSize: 16
  }
});
