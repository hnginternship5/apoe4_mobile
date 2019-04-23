import React from "react";
import { Text } from "react-native";

export class Txt extends React.Component {
  render() {
    return (
      <Text
        {...this.props}
        style={[
          this.props.style,
          { fontFamily: "quick-light", color: "white" }
        ]}
      />
    );
  }
}

export class TxtB extends React.Component {
  render() {
    return (
      <Text
        {...this.props}
        style={[
          this.props.style,
          { fontFamily: "quick-regular", color: "white", fontSize: 20 }
        ]}
      />
    );
  }
}
