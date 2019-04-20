import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,Image,Text,ScrollView,
        TouchableOpacity,} from 'react-native';
import Pie from 'react-native-fab-pie';
import { FontAwesome } from '@expo/vector-icons';
var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class ScorePie extends React.Component {
   constructor(props) {
    super(props);
    const data = [8,2];
    const colors = ['132F4B','77AADD'];

    const pieData = data
      .filter(value => value > 0)
      .map((value, index) => {
        const toRet = {
          value,
          title: `title-${index}`,
          color: `#${colors[index]}`,
          key: `pie-${index}`,
        };
        return toRet;
      });

    this.state = {
      pieData,
      feeling:'great'
    };
  }

  componentDidMount() {
    this.pie.current.animate();
  }

  animate = () => {
    this.pie.current.reset().then(this.pie.current.animate);
  };

  pie = React.createRef();

  render() {
    return (
      <View style={{height:ww(100), width:ww(100)}}>
                <Pie
                ref={this.pie}
                containerStyle={{
                justifyContent:'center',
                alignItems:'center'
                }}
                pieStyle={{
                  width: 100,
                  height: 100,
                }}
                outerRadius={ww(50)}
                innerRadius={ww(40)}
                data={this.state.pieData}
                animate>
              </Pie>
              <View style={{position:'absolute',top:ww(20),left:ww(33)}}>
              <Text style={{color:'white',fontSize:ww(50),fontWeight:'bold',textAlign:'center'}}>9</Text>
              </View>

      </View>
    );
  }
}




