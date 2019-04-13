import React, {Component} from 'react';
import {Platform, StyleSheet,View, StatusBar,Dimensions,
Image,Text,TextInput,TouchableOpacity} from 'react-native';

var wS = Dimensions.get('window');
var dh = wS.height;
var dw = wS.width;
function hh(h){return (dh*h)/670}
function ww(w){return (dw*w)/375}

export default class App extends React.Component {
  render() {
    return (
      <View style={styles.container}>
      <StatusBar backgroundColor="#2a56c6" translucent barStyle="light-content" />
          <View style={{height:'100%',justifyContent: 'center',alignItems:'center',width:'100%' }}>
              <View style={{height:'80%', width:'80%'}}>
              <Image source={require('./assets/logo.png')} style={{height:dw*0.2, width:dw*0.2, alignSelf:'center'}} resizeMode="contain"/> 
                <Text style={{fontSize:ww(25), fontWeight:400}}>Sign Up</Text>

                        <View style={{width:'100%', marginVertical:hh(50)}}>
                          <TextInput 
                          placeholder="Email Address"
                          style={[styles.input]} />
                          <TextInput 
                          placeholder="Password"
                          style={styles.input} />
                          
                        <View style={{flexDirection:'row',justifyContent:'space-between',marginBottom:hh(30),}}>
                          <View style={{flexDirection:'row',alignItems:'center'}}>
                          <View style={{height:15,width:15, borderRadius:30, borderWidth:1,borderColor:'blue',
                          backgroundColor:'white',marginRight:10}}></View>
                            <Text>Forgot Password?</Text>
                          </View>

                            <Text>Forgot Password</Text>
                        </View>
                      </View>

                  <TouchableOpacity style={{width:'100%',paddingVertical:hh(20),marginBottom:hh(20), backgroundColor:'#3380CC',
                                  alignItems:'center', borderRadius:ww(5)}}>
                    <Text style={{fontSize:ww(17),color:'white'}}
                    >Sign in</Text>
                  </TouchableOpacity>

                <View style={{width:'100%', flexDirection:'row', justifyContent:'space-between'}}>
                                  <TouchableOpacity style={styles.btn}>
                      <Image source={require('./assets/fb.png')} style={{height:ww(30), width:ww(30)}} />
                  </TouchableOpacity>

                  <TouchableOpacity style={styles.btn}>
                     <Image source={require('./assets/google.png')} style={{height:ww(30), width:ww(30)}} />
                  </TouchableOpacity>
                </View>

              </View>
        </View>

      </View>
    );
  }
}

const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? 20 : StatusBar.currentHeight;

const styles = StyleSheet.create({
    btn:{
    height: hh(50),
    width:(0.30*dw),
    alignItems: 'center',
    justifyContent:'center',
    borderColor: '#3380CC',
    borderRadius: 5,
    borderWidth: 1.5
  },
  input:{
    backgroundColor:'white',
    height:hh(50),
    width:'100%',
    borderRadius:5,
    borderWidth:2,
    borderColor:'#3380CC',
    marginBottom:hh(20)
  },
  mainTxt:{
    fontSize:20,
  },
  container: {
    flex: 1,
  },
  statusBar:{
    height: STATUSBAR_HEIGHT
  }

});
