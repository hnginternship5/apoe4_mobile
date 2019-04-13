import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,
Image,Text,ImageBackground,TouchableOpacity} from 'react-native';
import { LinearGradient } from 'expo';

var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class App extends React.Component {
  render() {
    return (
      <View style={styles.container}>
      <StatusBar backgroundColor="transparent" translucent barStyle="light-content" />
      <ImageBackground 
        resizeMode="cover"
        source={require('./assets/bgImg.jpg')}
        style={{width:'100%',height: '100%',}}>
        <LinearGradient
          colors={['rgba(15,22,26,0.1)','rgba(0,3,3,0.9)']}
          start={[0,0]}
          style={{height:'100%',justifyContent: 'flex-end',alignItems:'center' }}>
        <View style={{height:'60%', width:'80%'}}>
          <Text style={styles.mainTxt}>Live life,</Text>
          <Text style={styles.mainTxt}>Embrace Joy</Text>

          <View style={{width:'100%', marginVertical:hh(40)}}>
              <Text style={{fontSize:ww(17),color:'white'}}
              >Embrace joy and get help to remember all you need to with people you love.</Text>
          </View>

          <TouchableOpacity style={{width:'100%',paddingVertical:hh(20),marginBottom:hh(20), backgroundColor:'#2a56c6',alignItems:'center', borderRadius:ww(30)}}>
            <Text style={{fontSize:ww(17),color:'white'}}
            > Register</Text>
          </TouchableOpacity>

        <TouchableOpacity style={{width:'100%',paddingVertical:hh(20),borderWidth:1,borderColor:'white',
           alignItems:'center', borderRadius:ww(30)}}>
            <Text style={{fontSize:ww(17),color:'white'}}
            > Already have an account, Login</Text>
          </TouchableOpacity>
        </View>
        </LinearGradient>
      </ImageBackground>
      </View>
    );
  }
}

const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? 20 : StatusBar.currentHeight;

const styles = StyleSheet.create({
  mainTxt:{
    fontSize:28,
    color:'#fff',
    fontWeight:'bold',
  },
  btn:{
    height: hh(50),
    width:(0.35*dw),
    alignItems: 'center',
    justifyContent:'center',
    borderColor: '#ed3851',
    borderRadius: 5,
    borderWidth: 1.5
  },
  topText:{
    fontSize: ww(17),
    color:'#474747'
  },
  container: {
    flex: 1,
  },
  statusBar:{
    height: STATUSBAR_HEIGHT
  }

});
